package com.softman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softman.entity.LogHistory;


public interface LogJpaRepository extends JpaRepository<LogHistory, Long> {

}
