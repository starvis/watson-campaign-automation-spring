package com.github.ka4ok85.wca.pod;

import java.util.Arrays;
import java.util.List;

public class Pod {
	/* deprecated URLs by end 2020

	private static String ACCESS_URL = "https://apiPOD.silverpop.com/oauth/token";
	private static String XML_API_URL = "https://apiPOD.silverpop.com/XMLAPI";
	private static String SFTP_URL = "transferPOD.silverpop.com";

	private static List<Integer> podList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

	*/

	private static String BASE = "https://api-campaign-POD.goacoustic.com";
	private static String ACCESS_URL = BASE + "/oauth/token";
	private static String XML_API_URL = BASE + "/XMLAPI";
	private static String SFTP_URL = "transfer-campaign-POD.goacoustic.com";

	private static List<String> podList = Arrays.asList("us-1", "us-2", "us-3", "us-4", "us-5", "eu-1", "ap-2", "ca-1", "us-6", "ap-1");

	public static String getOAuthEndpoint(String podIdentifier) {
		if (false == isValidPodIdentifier(podIdentifier)) {
			throw new RuntimeException("Unsupported Pod");
		}

		return ACCESS_URL.replaceAll("POD", String.valueOf(podIdentifier));
	}

	public static String getXMLAPIEndpoint(String podIdentifier) {
		if (false == isValidPodIdentifier(podIdentifier)) {
			throw new RuntimeException("Unsupported Pod");
		}

		return XML_API_URL.replaceAll("POD", String.valueOf(podIdentifier));
	}

	public static String getSFTPHostName(String podIdentifier) {
		if (false == isValidPodIdentifier(podIdentifier)) {
			throw new RuntimeException("Unsupported Pod");
		}

		return SFTP_URL.replaceAll("POD", String.valueOf(podIdentifier));
	}

	private static boolean isValidPodIdentifier(String podIdentifier) {
		return podList.contains(podIdentifier);
	}
}
