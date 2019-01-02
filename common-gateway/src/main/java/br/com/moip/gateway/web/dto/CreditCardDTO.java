package br.com.moip.gateway.web.dto;

import java.util.Date;

public class CreditCardDTO {
	
	private String holderName;
	private String cardNumber;
	private Date expirationDate;
	private String cvv;
	private String creditCardToken;
	
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getCreditCardToken() {
		return creditCardToken;
	}
	public void setCreditCardToken(String creditCardToken) {
		this.creditCardToken = creditCardToken;
	}
	

}
