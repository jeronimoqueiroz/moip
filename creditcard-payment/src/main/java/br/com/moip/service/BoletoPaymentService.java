package br.com.moip.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.moip.builder.BoletoBuilder;
import br.com.moip.gateway.domain.Boleto;
import br.com.moip.gateway.domain.Payment;
import br.com.moip.gateway.domain.PaymentType;
import br.com.moip.gateway.service.PaymentService;
import br.com.moip.gateway.web.dto.PaymentDTO;

@Service
public class BoletoPaymentService extends PaymentService{
	
	@Inject
	private BoletoBuilder boletoBuilder;
	
	public Boleto generateBoletoHash(PaymentDTO dto) {
		
		Boleto boleto = boletoBuilder.buildFieldsDocument(dto);
	
		Payment payment = new Payment();
		payment.setAmount(dto.getValue());
		payment.setType(PaymentType.BOLETO);
		payment.setBoleto(boleto);
		
		this.save(payment);
		
		return boleto;
		
		
	}

}
