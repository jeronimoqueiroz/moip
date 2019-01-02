package br.com.moip.service;

import org.springframework.stereotype.Service;

import br.com.moip.gateway.domain.Payment;
import br.com.moip.gateway.domain.PaymentType;
import br.com.moip.gateway.service.PaymentService;
import br.com.moip.gateway.web.dto.PaymentDTO;

@Service
public class CreditCardPaymentService extends PaymentService{
	
	
	public Payment saveTransactionCard(String clientId, PaymentDTO dto) {
		
	
		Payment payment = new Payment();
		payment.setAmount(dto.getValue());
		payment.setType(PaymentType.CREDIT_CARD);
		payment.setCreditCardToken(dto.getCreditCard().getCreditCardToken());
		payment.setClientId(clientId);
		
		this.save(payment);
		
		return payment;
		
		
	}

}
