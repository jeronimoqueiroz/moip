package br.com.moip.gateway.web.util;

import java.util.UUID;

public class TransactionBuilder {
	
	
	public static String generateUid() {
		return UUID.randomUUID().toString();
	}

}
