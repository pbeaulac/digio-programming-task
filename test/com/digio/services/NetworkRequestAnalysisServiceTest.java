package com.digio.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digio.models.NetworkRequest;
import com.digio.services.implementations.NetworkRequestAnalysisServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class NetworkRequestAnalysisServiceTest {

	private NetworkRequestAnalysisServiceImpl networkRequestAnalsysService;
	
	@BeforeEach
	public void setUp() {
		networkRequestAnalsysService = new NetworkRequestAnalysisServiceImpl();
	}
	
	/* getUniqueIPAddresses(List<NetworkRequest>) */
	
	@Test
	public void givenNull_whenGetUniqueIPAddresses_thenFailWithNullPointerException() {
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getUniqueIPAddresses(null);
		});
		
	}
	
	@Test
	public void givenAnEmptyListOfRequests_whenGetUniqueIPAddresses_thenReturnZero() {
		
		List<NetworkRequest> requests = new ArrayList<>();

		int uniqueIps = networkRequestAnalsysService.getUniqueIPAddresses(requests);

		assertEquals(uniqueIps, 0);

	}
	
	@Test
	public void givenAListOfRequests_andEachRequestIPIsUnique_whenGetUniqueIPAddresses_thenReturnSizeOfInputList() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		int uniqueIps = networkRequestAnalsysService.getUniqueIPAddresses(requests);
		
		assertEquals(uniqueIps, requests.size());

	}
	
	@Test
	public void givenAListOfRequests_andTheListContainsADuplicateIP_whenGetUniqueIPAddresses_thenReturnSizeOfInputListMinusOne() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.60","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.60","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.60","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		int uniqueIps = networkRequestAnalsysService.getUniqueIPAddresses(requests);
		
		assertEquals(uniqueIps, 1);
		
	}
	
	@Test
	public void givenAListOfRequests_andAllIPsAreDuplicates_whenGetUniqueIPAddresses_thenReturnOne() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.61","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		int uniqueIps = networkRequestAnalsysService.getUniqueIPAddresses(requests);
		
		assertEquals(uniqueIps, requests.size()-1);
		
	}
	
	/* getMostVisitedURLs(List<NetworkRequest>, Integer) */
	
	@Test
	public void givenNullInputs_whenGgetMostVisitedURLs_thenFailWithNullPointerException() {
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getMostVisitedURLs(new ArrayList<>(), null);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getMostVisitedURLs(null, 3);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getMostVisitedURLs(null, null);
		});
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfZero_whenGetMostVisitedURLs_thenReturnAnEmptyList() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 0;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.61","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		List<String> output = networkRequestAnalsysService.getMostVisitedURLs(requests, bound);
		
		assertEquals(output.size(), 0);
		
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_whenGetMostVisitedURLs_thenReturnListContainingTheThreeMostCommonURLs() {

		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 3;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/bar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/bar/");
		NetworkRequest nr5 = new NetworkRequest("168.41.191.64","/blog/category/community/bar/");

		NetworkRequest nr6 = new NetworkRequest("168.41.191.65","/blog/category/community/foobar/");
		NetworkRequest nr7 = new NetworkRequest("168.41.191.66","/blog/category/community/foobar/");
		NetworkRequest nr8 = new NetworkRequest("168.41.191.67","/blog/category/community/foobar/");

		NetworkRequest nr9 = new NetworkRequest("168.41.191.68","/blog/category/community/baz/");
		NetworkRequest nr10 = new NetworkRequest("168.41.191.69","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		requests.add(nr5);
		requests.add(nr6);
		requests.add(nr7);
		requests.add(nr8);
		requests.add(nr9);
		requests.add(nr10);
		
		List<String> output = networkRequestAnalsysService.getMostVisitedURLs(requests, bound);
		
		assertEquals(output.size(), 3);
		assertEquals(output.contains("/blog/category/community/bar/"), true);
		assertEquals(output.contains("/blog/category/community/foobar/"), true);
		assertEquals(output.contains("/blog/category/community/baz/"), true);
		assertEquals(output.contains("/blog/category/community/foo/"), false);

	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_andAllRequestUrlsAreUnique_whenGetMostVisitedURLs_thenReturnListOfSizeThree() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 3;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		List<String> output = networkRequestAnalsysService.getMostVisitedURLs(requests, bound);
		
		assertEquals(output.size(), 3);
		
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_andListContainsASingleDuplicateUrl_whenGetMostVisitedURLs_thenReturnListOfSizeThreeContainingDuplicate() {
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 3;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		NetworkRequest nr5 = new NetworkRequest("168.41.191.64","/blog/category/community/foo/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		requests.add(nr5);

		
		List<String> output = networkRequestAnalsysService.getMostVisitedURLs(requests, bound);
		
		assertEquals(output.size(), 3);
		assertEquals(output.contains("/blog/category/community/foo/"), true);
		
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_andTheBoundIsGreaterThanDistinctUrls_whenGetMostVisitedURLs_thenReturnListContainingAllDistinctURLs() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 5;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		NetworkRequest nr5 = new NetworkRequest("168.41.191.64","/blog/category/community/foo/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		requests.add(nr5);

		
		List<String> output = networkRequestAnalsysService.getMostVisitedURLs(requests, bound);
		
		assertEquals(output.size(), 4);
		assertEquals(output.contains("/blog/category/community/foo/"), true);
		assertEquals(output.contains("/blog/category/community/bar/"), true);
		assertEquals(output.contains("/blog/category/community/foobar/"), true);
		assertEquals(output.contains("/blog/category/community/baz/"), true);
		
	}
	
	/* getMostActiveIPs(List<NetworkRequest>, Integer) */
	
	@Test
	public void givenNullInputs_getMostActiveIPs_thenFailWithNullPointerException() {
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getMostActiveIPs(new ArrayList<>(), null);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getMostActiveIPs(null, 3);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkRequestAnalsysService.getMostActiveIPs(null, null);
		});
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfZero_whenGetMostActiveIPs_thenReturnAnEmptyList() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 0;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		List<String> output = networkRequestAnalsysService.getMostActiveIPs(requests, bound);
		
		assertEquals(output.size(), 0);
		
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_whenGetMostActiveIPs_thenReturnListContainingTheThreeMostCommonIPs() {

		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 3;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo1/");
		
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar1/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.61","/blog/category/community/bar2/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.61","/blog/category/community/bar3/");
		NetworkRequest nr5 = new NetworkRequest("168.41.191.61","/blog/category/community/bar4/");

		NetworkRequest nr6 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar1/");
		NetworkRequest nr7 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar2/");
		NetworkRequest nr8 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar3/");

		NetworkRequest nr9 = new NetworkRequest("168.41.191.63","/blog/category/community/baz1/");
		NetworkRequest nr10 = new NetworkRequest("168.41.191.63","/blog/category/community/baz2/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		requests.add(nr5);
		requests.add(nr6);
		requests.add(nr7);
		requests.add(nr8);
		requests.add(nr9);
		requests.add(nr10);
		
		List<String> output = networkRequestAnalsysService.getMostActiveIPs(requests, bound);
		
		assertEquals(output.size(), 3);
		assertEquals(output.contains("168.41.191.61"), true);
		assertEquals(output.contains("168.41.191.62"), true);
		assertEquals(output.contains("168.41.191.63"), true);
		assertEquals(output.contains("168.41.191.60"), false);

	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_andAllRequestIPsAreUnique_whenGetMostActiveIPs_thenReturnListOfSizeThree() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 3;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.62","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		
		List<String> output = networkRequestAnalsysService.getMostActiveIPs(requests, bound);
		
		assertEquals(output.size(), 3);
		
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_andListContainsASingleDuplicateIP_whenGetMostActiveIPs_thenReturnListOfSizeThreeContainingDuplicate() {
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 3;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo1/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.60","/blog/category/community/bar1/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.61","/blog/category/community/foobar1/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.62","/blog/category/community/baz1/");
		NetworkRequest nr5 = new NetworkRequest("168.41.191.63","/blog/category/community/foo2/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		requests.add(nr5);

		
		List<String> output = networkRequestAnalsysService.getMostActiveIPs(requests, bound);
		
		assertEquals(output.size(), 3);
		assertEquals(output.contains("168.41.191.60"), true);
		
	}
	
	@Test
	public void givenAListOfRequests_andABoundOfThree_andTheBoundIsGreaterThanDistinctIPs_whenGetMostActvieIPs_thenReturnListContainingAllDistinctIPs() {
		
		List<NetworkRequest> requests = new ArrayList<>();
		int bound = 5;
		
		NetworkRequest nr1 = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		NetworkRequest nr2 = new NetworkRequest("168.41.191.61","/blog/category/community/bar/");
		NetworkRequest nr3 = new NetworkRequest("168.41.191.63","/blog/category/community/foobar/");
		NetworkRequest nr4 = new NetworkRequest("168.41.191.63","/blog/category/community/baz/");
		NetworkRequest nr5 = new NetworkRequest("168.41.191.64","/blog/category/community/foo/");
		
		requests.add(nr1);
		requests.add(nr2);
		requests.add(nr3);
		requests.add(nr4);
		requests.add(nr5);
		
		List<String> output = networkRequestAnalsysService.getMostActiveIPs(requests, bound);
		
		assertEquals(output.size(), 4);
		assertEquals(output.contains("168.41.191.60"), true);
		assertEquals(output.contains("168.41.191.61"), true);
		assertEquals(output.contains("168.41.191.63"), true);
		assertEquals(output.contains("168.41.191.64"), true);
		
	}
	
}
