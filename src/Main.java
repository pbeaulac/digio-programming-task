import com.digio.services.interfaces.LogService;
import com.digio.services.interfaces.NetworkLogParseService;
import com.digio.services.interfaces.NetworkRequestAnalysisService;
import com.digio.services.interfaces.NetworkRequestFactoryService;

import java.util.List;

import com.digio.models.NetworkRequest;
import com.digio.services.implementations.LogServiceImpl;
import com.digio.services.implementations.NetworkLogParseServiceImpl;
import com.digio.services.implementations.NetworkRequestAnalysisServiceImpl;
import com.digio.services.implementations.NetworkRequestFactoryServiceImpl;

public class Main {
	
	public static void main(String[] args) {
		String path = System.getProperty("filepath");
		
		LogService logService = new LogServiceImpl();
		NetworkRequestFactoryService networkRequestFactoryService = new NetworkRequestFactoryServiceImpl();
		NetworkLogParseService networkLogParseService = new NetworkLogParseServiceImpl();
		networkLogParseService.setLogService(logService);
		networkLogParseService.setNetworkRequestFactoryService(networkRequestFactoryService);
		NetworkRequestAnalysisService networkRequestAnalysisService = new NetworkRequestAnalysisServiceImpl();
		
		List<NetworkRequest> nrs = networkLogParseService.parseNetworkLog(path);
		
		Integer uniqueIds = networkRequestAnalysisService.getUniqueIPAddresses(nrs);
		List<String> mostActiveIps = networkRequestAnalysisService.getMostActiveIPs(nrs, 3);
		List<String> mostVisitedUrls = networkRequestAnalysisService.getMostVisitedURLs(nrs, 3);
		
		logService.info("Number of unique IPs: " + Integer.toString(uniqueIds));
		logService.info("Three most active IPs: " + mostActiveIps.toString());
		logService.info("Three most visited URLs: " + mostVisitedUrls.toString());
	}
}

