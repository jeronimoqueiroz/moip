package br.com.moip.repository.search;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.moip.gateway.domain.Payment;


public interface PaymentSearchRepository  extends ElasticsearchRepository<Payment, Long> {
	
	public  Payment findByTransactionId(String transactionId);
	
	
}

  