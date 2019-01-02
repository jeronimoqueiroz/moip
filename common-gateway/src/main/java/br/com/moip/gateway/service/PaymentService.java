package br.com.moip.gateway.service;

import java.util.Date;

import javax.inject.Inject;

import br.com.moip.gateway.domain.Payment;
import br.com.moip.gateway.domain.PaymentStatus;
import br.com.moip.gateway.web.util.TransactionBuilder;
import br.com.moip.repository.search.PaymentSearchRepository;


public class PaymentService {
	
	@Inject
	protected PaymentSearchRepository paymentSearchRepository;
	
	
	protected Payment save(Payment payment) {
		
		payment.setDateTransaction(new Date());
		payment.setTransactionId(TransactionBuilder.generateUid());
		payment.setStatus(PaymentStatus.PENDING);
		return paymentSearchRepository.save(payment);
	}
	
	public Payment findByTransactionId(String transactionId) {
		return paymentSearchRepository.findByTransactionId(transactionId);
	}

}
