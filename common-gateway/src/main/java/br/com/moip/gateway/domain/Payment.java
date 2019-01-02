package br.com.moip.gateway.domain;

import java.util.Date;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import br.com.moip.gateway.web.dto.StatusDTO;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "payment")
public class Payment {
	
	private Long id;
	private String transactionId;
	private Date dateTransaction; 
	private double amount;
	private Boleto boleto;
	private PaymentType type;
	private PaymentStatus status;
	private String clientId;
	
	private String creditCardToken;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getDateTransaction() {
		return dateTransaction;
	}
	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Boleto getBoleto() {
		return boleto;
	}
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
	public PaymentType getType() {
		return type;
	}
	public void setType(PaymentType type) {
		this.type = type;
	}
	public String getCreditCardToken() {
		return creditCardToken;
	}
	public void setCreditCardToken(String creditCardToken) {
		this.creditCardToken = creditCardToken;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	
	public StatusDTO buildPaymentStatus() {
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setTransactionId(this.transactionId);
		statusDTO.setStatus(this.status);
		return statusDTO;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	

}
