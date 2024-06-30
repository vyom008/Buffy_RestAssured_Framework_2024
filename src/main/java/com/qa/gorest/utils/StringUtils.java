package com.qa.gorest.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		String randomEmail = "Buffy" + System.currentTimeMillis() + "@api.com";
		return randomEmail;
	}
}
