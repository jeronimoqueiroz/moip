package br.com.moip.repository.search;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.moip.gateway.domain.Boleto;


public interface BoletoSearchRepository  extends ElasticsearchRepository<Boleto, Long> {
	
	
	
}

  