package com.digio.services.implementations;

import com.digio.models.NetworkRequest;
import com.digio.services.interfaces.NetworkRequestFactoryService;

public class NetworkRequestFactoryServiceImpl implements NetworkRequestFactoryService {

	@Override
	public NetworkRequest create(String logEntry) {
				
		//String regex = "/\"[^\"]*\"|\\[[^\\][]*]|[^\\s\\][]+/g";

		String[] splitEntry = logEntry.split("\"");
		
		NetworkRequest nr = new NetworkRequest();
		nr.setIp(splitEntry[0].split(" ")[0]);
		nr.setUrl(splitEntry[1].split(" ")[1]);
		
		return nr;
	}

}
