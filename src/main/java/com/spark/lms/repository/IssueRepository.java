package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spark.lms.model.Issue;
import com.spark.lms.model.Member;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	public List<Issue> findByReturned(Integer returned);
	public Long countByMemberAndReturned(Member member, Integer returned);
}
