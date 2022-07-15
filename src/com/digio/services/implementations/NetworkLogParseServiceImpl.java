package com.digio.services.implementations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.digio.models.NetworkRequest;
import com.digio.services.interfaces.LogService;
import com.digio.services.interfaces.NetworkLogParseService;
import com.digio.services.interfaces.NetworkRequestFactoryService;

public class NetworkLogParseServiceImpl implements NetworkLogParseService {
	
	private LogService logService;
	private NetworkRequestFactoryService networkRequestFactoryService;
	
	@Override
	public List<NetworkRequest> parseNetworkLog(String filePath) {
		
		Objects.requireNonNull(filePath);
		
		List<NetworkRequest> requests = new ArrayList<NetworkRequest>();
		
		try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr)) {
			
			String entry = null;
						
			while ((entry = br.readLine()) != null) {
				NetworkRequest request = networkRequestFactoryService.create(entry);
				requests.add(request);
			}
			
		} catch (FileNotFoundException e) {
			logService.error("Cannot find file at provided path: " + filePath);
		} catch (IOException e) {
			logService.error(e.getLocalizedMessage());
		}
		
		return requests;
	}
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public void setNetworkRequestFactoryService(NetworkRequestFactoryService networkRequestFactoryService) {
		this.networkRequestFactoryService = networkRequestFactoryService;
	}

}
