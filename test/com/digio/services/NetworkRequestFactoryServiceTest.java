package com.digio.services;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digio.models.NetworkRequest;
import com.digio.services.implementations.NetworkRequestFactoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class NetworkRequestFactoryServiceTest {
	
	private NetworkRequestFactoryServiceImpl networkRequestFactoryService;
	
	@BeforeEach
	public void setUp() {
		networkRequestFactoryService = new NetworkRequestFactoryServiceImpl();
	}
	
	/* create(String) */
	
	@Test
	public void givenAnEntry_whenCreate_thenReturnANetworkRequest() {
		
		NetworkRequest control = new NetworkRequest("168.41.191.60","/blog/category/community/foo/");
		
		String entry = "168.41.191.60 - - [09/Jul/2018:10:11:30 +0200] \"GET /blog/category/community/foo/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"\n";
		
		NetworkRequest output = networkRequestFactoryService.create(entry);
		
		assertEquals(output.getIp(), control.getIp());
		assertEquals(output.getUrl(), control.getUrl());

	}

}
