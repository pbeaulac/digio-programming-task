package com.digio.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digio.models.NetworkRequest;
import com.digio.services.implementations.NetworkLogParseServiceImpl;
import com.digio.services.interfaces.LogService;
import com.digio.services.interfaces.NetworkRequestFactoryService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class NetworkLogParseServiceTest {
	
	@Mock private LogService logService;
	@Mock private NetworkRequestFactoryService networkRequestFactoryService;
	
	private NetworkLogParseServiceImpl networkLogParseService;
	
	@BeforeEach
	public void setUp() throws Exception {
		networkLogParseService = new NetworkLogParseServiceImpl();
		
		networkLogParseService.setLogService(logService);
		networkLogParseService.setNetworkRequestFactoryService(networkRequestFactoryService);
	}
	
	/* parseNetworkLog(String) */
	
	@Test
	public void givenANullFilePath_whenParseNetworkLog_thenFailWithNullPointer() {
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			networkLogParseService.parseNetworkLog(null);
		});
		
	}
	
	@Test
	public void givenAFilePath_andThereIsNoSuchFile_whenParseNetworkLog_thenErrorIsLogged() {
		
		String filePath = "/no/such/location/";
		
		networkLogParseService.parseNetworkLog(filePath);
		
		verify(logService, times(1)).error(any());
		
	}
	
	@Test
	public void givenAValidFilePath_andThefileContainsMultipleEntries_whenParseNetworkLog_thenNetworkRequestsAreReturned() {
		
		String filePath = "./files/test-file-1.log";
		
		List<NetworkRequest> output = networkLogParseService.parseNetworkLog(filePath);

		verify(networkRequestFactoryService, times(4)).create(any());
		assertEquals(output.size(), 4);
	}
	
	@Test
	public void givenAValidFilePath_andTheFileIsEmpty_whenParseNetworkLog_thenEmptyListIsReturned() {
		
		String filePath = "./files/test-file-2.log";

		List<NetworkRequest> output = networkLogParseService.parseNetworkLog(filePath);
		verify(networkRequestFactoryService, never()).create(any());
		assertEquals(output.size(), 0);

	}
	
	@Test
	public void givenAValidFilePath_andTheFileContainsASingleEntry_whenParseNetworkLog_thenEmptyListIsReturned() {
		
		String filePath = "./files/test-file-3.log";

		List<NetworkRequest> output = networkLogParseService.parseNetworkLog(filePath);
		verify(networkRequestFactoryService, times(1)).create(any());
		assertEquals(output.size(), 1);

	}

}
