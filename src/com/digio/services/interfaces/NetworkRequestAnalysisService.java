package com.digio.services.interfaces;

import java.util.List;

import com.digio.models.NetworkRequest;

public interface NetworkRequestAnalysisService {
	
	public Integer getUniqueIPAddresses(List<NetworkRequest> requests);
	public List<String> getMostVisitedURLs(List<NetworkRequest> requests, Integer bound);
	public List<String> getMostActiveIPs(List<NetworkRequest> requests, Integer bound);

}
