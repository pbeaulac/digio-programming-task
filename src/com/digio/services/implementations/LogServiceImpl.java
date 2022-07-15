package com.digio.services.implementations;

import org.apache.log4j.Logger;

import com.digio.services.interfaces.LogService;

public class LogServiceImpl implements LogService {
	
	private static final Logger LOGGER = Logger.getLogger(LogServiceImpl.class);

	@Override
	public void info(String message) {
		LOGGER.info(message);
	}

	@Override
	public void error(String message) {
		LOGGER.error(message);
	}
	
}
