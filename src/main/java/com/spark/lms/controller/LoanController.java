package com.spark.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spark.lms.model.Loan;
import com.spark.lms.model.Category;
import com.spark.lms.service.LoanService;
import com.spark.lms.service.CategoryService;

@Controller
@RequestMapping(value = "/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute(name = "categories")
    public List<Category> categories() {
        return categoryService.getAllBySort();
    }

    @RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
    public String showLoansPage(Model model) {
        model.addAttribute("loans", loanService.getAll());
        return "/loan/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addLoanPage(Model model) {
        model.addAttribute("loan", new Loan());
        return "/loan/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editLoanPage(@PathVariable(name = "id") Long id,  Model model) {
        Loan loan = loanService.get(id);
        if( loan != null ) {
            model.addAttribute("loan", loan);
            return "/loan/form";
        } else {
            return "redirect:/loan/add";
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveLoan(@Valid Loan loan, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            return "/loan/form";
        }

        if( loan.getId() == null ) {
            if( loanService.getByTag(loan.getTag()) != null ) {
                bindingResult.rejectValue("tag", "tag", "Tag already exists");
                return "/loan/form";
            } else {
                loanService.addNew(loan);
                redirectAttributes.addFlashAttribute("successMsg", "'" + loan.getTitle() + "' is added as a new Loan.");
                return "redirect:/loan/add";
            }
        } else {
            Loan updatedLoan = LoanService.save(loan);
            redirectAttributes.addFlashAttribute("successMsg", "Changes for '" + loan.getTitle() + "' are saved successfully. ");
            return "redirect:/loan/edit/"+updatedLoan.getId();
        }
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeLoan(@PathVariable(name = "id") Long id, Model model) {
        Loan loan = loanService.get( id );
        if( loan != null ) {
            if( loanService.hasUsage(loan) ) {
                model.addAttribute("loanInUse", true);
                return showLoansPage(model);
            } else {
                loanService.delete(id);
            }
        }
        return "redirect:/loan/list";
    }
}