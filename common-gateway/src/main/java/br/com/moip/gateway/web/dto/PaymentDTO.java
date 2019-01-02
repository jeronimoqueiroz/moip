package br.com.moip.gateway.web.dto;

public class PaymentDTO {

	private BuyerDTO buyer;
	private double value;
	private CreditCardDTO creditCard;
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public BuyerDTO getBuyer() {
		return buyer;
	}
	public void setBuyer(BuyerDTO buyer) {
		this.buyer = buyer;
	}
	@Override
	public String toString() {
		return "PaymentDTO [buyer=" + buyer + ", value=" + value + "]";
	}
	public CreditCardDTO getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCardDTO creditCard) {
		this.creditCard = creditCard;
	}
	
	
}
