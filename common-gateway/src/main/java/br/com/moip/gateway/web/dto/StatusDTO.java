package br.com.moip.gateway.web.dto;

import br.com.moip.gateway.domain.PaymentStatus;

public class StatusDTO {
	
	private String transactionId;
	private PaymentStatus status;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	
	

}
