package com.digio.services.implementations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;

import com.digio.models.NetworkRequest;
import com.digio.services.interfaces.NetworkRequestAnalysisService;

public class NetworkRequestAnalysisServiceImpl implements NetworkRequestAnalysisService {

	@Override
	public Integer getUniqueIPAddresses(List<NetworkRequest> requests) {
		
		Objects.requireNonNull(requests);
		
		return generateIPFrequencyTable(requests).size();
	}

	@Override
	public List<String> getMostVisitedURLs(List<NetworkRequest> requests, Integer bound) {
		
		Objects.requireNonNull(requests);
		Objects.requireNonNull(bound);
		
		List<Map.Entry<String,Long>> sortedTable = generateURLsFrequencyTable(requests).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(bound).collect(Collectors.toList());
		
		return buildOutputList(sortedTable);
	}

	@Override
	public List<String> getMostActiveIPs(List<NetworkRequest> requests, Integer bound) {

		Objects.requireNonNull(requests);
		Objects.requireNonNull(bound);
		
		List<Map.Entry<String,Long>> sortedTable = generateIPFrequencyTable(requests).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(bound).collect(Collectors.toList());
		
		return buildOutputList(sortedTable);

	}
	
	private Map<String, Long> generateIPFrequencyTable(List<NetworkRequest> requests) {
		return requests.stream().collect(Collectors.groupingBy(NetworkRequest::getIp, Collectors.counting()));
	}
	
	private Map<String, Long> generateURLsFrequencyTable(List<NetworkRequest> requests) {
		return requests.stream().collect(Collectors.groupingBy(NetworkRequest::getUrl, Collectors.counting()));
	}
	
	private List<String> buildOutputList(List<Map.Entry<String,Long>> sortedTable) {
		
		List<String> outputList = new ArrayList<>();
		
		for (Map.Entry<String, Long> entry : sortedTable) {
			outputList.add(entry.getKey());
		}
		
		return outputList;
	}

}
