package com.jwtk.znlover.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtk.znlover.domain.JwtkMessage;


public interface JwtkMessageRepository extends JpaRepository<JwtkMessage, Long> {
	List<JwtkMessage> findAllByOrderByDateDesc();
}
