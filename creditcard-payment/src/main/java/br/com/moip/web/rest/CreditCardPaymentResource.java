package br.com.moip.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.moip.gateway.domain.Payment;
import br.com.moip.gateway.web.dto.PaymentDTO;
import br.com.moip.gateway.web.dto.StatusDTO;
import br.com.moip.gateway.web.rest.AbstractPaymentResource;
import br.com.moip.gateway.web.util.HeaderUtil;
import br.com.moip.service.CreditCardPaymentService;

@RestController
@RequestMapping("/api")
public class CreditCardPaymentResource extends AbstractPaymentResource {
	
	private final Logger log = LoggerFactory.getLogger(CreditCardPaymentResourceTest.class);
	private static final String PAYMENT = "payment";
	
	@Inject
	private CreditCardPaymentService creditCardPaymentService;
	
	
	
	@PostMapping("/creditcard/payment")
    @Timed
    public ResponseEntity<StatusDTO> paymentWithCreditCard(@Valid @RequestBody PaymentDTO payment, @RequestHeader("CLIENT-ID") String clientId) throws URISyntaxException {
        log.debug("REST request to keep payment : {}", payment);
        if (payment.getBuyer() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(PAYMENT, "validation", "Para acao de pagamentoo o cliente obrigatorio")).body(null);
        }
        if( !StringUtils.isNumeric(clientId)) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(PAYMENT, "validation", "Client-id obrigatorio")).body(null);
        }
        if (payment.getCreditCard() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(PAYMENT, "validation", "Para acao de pagamentoo o cartao obrigatorio")).body(null);
        }
        
        Payment result = creditCardPaymentService.saveTransactionCard(clientId, payment);
        
        return ResponseEntity.created(new URI("/boleto/payment"))
            .headers(HeaderUtil.createEntityCreationAlert("payment", result.getTransactionId()))
            .body(result.buildPaymentStatus());
    }
	
	@GetMapping("/creditcard/status/{transactionId}")
    @Timed
    public ResponseEntity<StatusDTO> checkingPaymentStatus(@PathVariable String transactionId, @RequestHeader("CLIENT-ID") String clientId) throws URISyntaxException {
        log.debug("REST request to get status : {}", transactionId);
        if (transactionId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(PAYMENT, "validation", "Para obter status necesario transactionid")).body(null);
        }
        if( !StringUtils.isNumeric(clientId)) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(PAYMENT, "validation", "Client-id obrigatorio")).body(null);
        }
         
        Payment payment = creditCardPaymentService.findByTransactionId(transactionId);
        if(payment != null) {
	        StatusDTO status = payment.buildPaymentStatus();
	        return new ResponseEntity<>(status, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
