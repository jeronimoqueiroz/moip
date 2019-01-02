package br.com.moip.repository.search;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.moip.gateway.domain.Client;


public interface ClientSearchRepository  extends ElasticsearchRepository<Client, Long> {
}

 