package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spark.lms.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public List<Member> findAllByOrderByFirstNameAscMiddleNameAscLastNameAsc();
	public Long countByType(String type);
}
