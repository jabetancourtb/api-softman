package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softman.entity.LogHistory;
import com.softman.repository.LogJpaRepository;
import com.softman.service.LogService;


@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogJpaRepository logJpaRepository;

	
	@Override
	public void setHistory(LogHistory logHistory) throws Exception {
		logJpaRepository.save(logHistory);		
	}

}
