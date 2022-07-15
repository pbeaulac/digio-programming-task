package com.digio.services.interfaces;

import java.util.List;

import com.digio.models.NetworkRequest;

public interface NetworkLogParseService {

	public List<NetworkRequest> parseNetworkLog(String filePath);
	public void setLogService(LogService logServiceImpl);
	public void setNetworkRequestFactoryService(NetworkRequestFactoryService networkRequestFactoryService);
	
}
