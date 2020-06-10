package com.github.ka4ok85.wca.pod;

import static org.junit.Assert.*;

import org.junit.Test;

public class PodTest {

	@Test
	public void testGetOAuthEndpoint() {
		String oAuthEndpoint = Pod.getOAuthEndpoint("eu-1");
		assertEquals(oAuthEndpoint, "https://api-campaign-eu-1.goacoustic.com/oauth/token");
	}

	@Test(expected = RuntimeException.class)
	public void testGetOAuthEndpointThrowsException() {
		String oAuthEndpoint = Pod.getOAuthEndpoint("eu1");
		assertEquals(oAuthEndpoint, "https://api-campaign-eu-1.goacoustic.com/oauth/token");
	}

	@Test
	public void testXMLAPIEndpoint() {
		String xMLAPIEndpoint = Pod.getXMLAPIEndpoint("us-4");
		assertEquals(xMLAPIEndpoint, "https://api-campaign-us-4.goacoustic.com/XMLAPI");
	}

	@Test(expected = RuntimeException.class)
	public void testXMLAPIEndpointThrowsException() {
		String xMLAPIEndpoint = Pod.getXMLAPIEndpoint("456");
		assertEquals(xMLAPIEndpoint, "https://api-campaign-us-4.goacoustic.com.com/XMLAPI");
	}

	@Test
	public void testSFTPHostName() {
		String sFTPHostName = Pod.getSFTPHostName("us-3");
		assertEquals(sFTPHostName, "transfer-campaign-us-3.goacoustic.com");
	}

	@Test(expected = RuntimeException.class)
	public void testSFTPHostNameThrowsException() {
		String sFTPHostName = Pod.getSFTPHostName("us-8");
		assertEquals(sFTPHostName, "transfer-campaign-us-3.goacoustic.com");
	}

}
