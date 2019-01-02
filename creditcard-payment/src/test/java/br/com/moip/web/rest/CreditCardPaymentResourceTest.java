package br.com.moip.web.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import br.com.moip.Main;
import br.com.moip.gateway.web.dto.BuyerDTO;
import br.com.moip.gateway.web.dto.CreditCardDTO;
import br.com.moip.gateway.web.dto.PaymentDTO;
import br.com.moip.web.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class CreditCardPaymentResourceTest {
	
	private static final String DEFAULT_CPF = "81133299987";
	private static final String DEFAULT_NAME = "JUCA DA SILVA SAURO";
	private static final String DEFAULT_HOLDER_NAME = "JUCA DA SILVA SAURO";
	private static final String DEFAULT_CARD_NUMBER = "8787878989891212121212122";
	private static final String DEFAULT_CARD_TOKEN = "AAQAQ8787UUUUU891212121LLLLIIIIAAAANNNN";
	private static final String DEFAULT_CVV = "321";
	private static final String DEFAULT_CLIENT_ID = "10";
	private static final String INVALID_TRANSACTION_ID = "AAQAQ8787UUUUU891212121LLLLIIIIAAAANNNN";

	private PaymentDTO payment;

	private MockMvc restCreditCardPaymentMockMvc;

	@Autowired
	private CreditCardPaymentResource creditCardPaymentResource;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;
	
	
	private static final String API_CREDITCARD_PAYMENT = "/api/creditcard/payment";
	private static final String API_CREDITCARD_STATUS = "/api/creditcard/status/";
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.restCreditCardPaymentMockMvc = MockMvcBuilders.standaloneSetup(creditCardPaymentResource)
				.setMessageConverters(jacksonMessageConverter).build();

		CreditCardDTO creditcard = new CreditCardDTO();
		creditcard.setCardNumber(DEFAULT_CARD_NUMBER);
		creditcard.setHolderName(DEFAULT_HOLDER_NAME);
		creditcard.setCreditCardToken(DEFAULT_CARD_TOKEN);
		creditcard.setCvv(DEFAULT_CVV);

		BuyerDTO buyer = new BuyerDTO();
		buyer.setCpf(DEFAULT_CPF);
		buyer.setName(DEFAULT_NAME);

		payment = new PaymentDTO();
		payment.setValue(200d);
		payment.setBuyer(buyer);
		payment.setCreditCard(creditcard);
	}
	
	@Test
	@Transactional
	public void createCreditCardPaymentSimple() throws Exception {

		restCreditCardPaymentMockMvc.perform(post(API_CREDITCARD_PAYMENT).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.header("CLIENT-ID", DEFAULT_CLIENT_ID).content(TestUtil.convertObjectToJsonBytes(payment)))
				.andExpect(status().isCreated());

	}
	
	@Test
	@Transactional
	public void tryCreateCreditCardPaymentWithoutClientId() throws Exception {

		restCreditCardPaymentMockMvc.perform(post(API_CREDITCARD_PAYMENT).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(payment)))
				.andExpect(status().is4xxClientError());

	}
	
	@Test
	@Transactional
	public void tryCreateCreditCardPaymentWithoutBuyer() throws Exception {

		payment.setBuyer(null);
		
		restCreditCardPaymentMockMvc.perform(post(API_CREDITCARD_PAYMENT).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.header("CLIENT-ID", DEFAULT_CLIENT_ID).content(TestUtil.convertObjectToJsonBytes(payment)))
				.andExpect(status().is4xxClientError());

	}
	
	
	@Test
	@Transactional
	public void createCreditCardPaymentCheckingStatus() throws Exception {

		MvcResult resultPayment  = restCreditCardPaymentMockMvc.perform(post(API_CREDITCARD_PAYMENT).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.header("CLIENT-ID", DEFAULT_CLIENT_ID).content(TestUtil.convertObjectToJsonBytes(payment)))
				.andExpect(status().isCreated())
				.andReturn();
				
		
		JSONObject boletoJson = new JSONObject(resultPayment.getResponse().getContentAsString());
		String transactionIdPayment = (String) boletoJson.get("transactionId");
		
		
		MvcResult resultStatusTransaction  = restCreditCardPaymentMockMvc.perform(get(API_CREDITCARD_STATUS+transactionIdPayment).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.header("CLIENT-ID", DEFAULT_CLIENT_ID))
				.andExpect(status().isOk())
				.andReturn();
		
		JSONObject paymentJson = new JSONObject(resultStatusTransaction.getResponse().getContentAsString());
		String transactionIdStatus = (String) paymentJson.get("transactionId");
		
		assertEquals(transactionIdPayment, transactionIdStatus);
	
	}
	
	@Test
	public void checkingPaymentWithInvalidTransactionId() throws Exception {

		restCreditCardPaymentMockMvc.perform(get(API_CREDITCARD_STATUS+INVALID_TRANSACTION_ID).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.header("CLIENT-ID", DEFAULT_CLIENT_ID))
				.andExpect(status().isNotFound());
				
		
		
	
	}
	
	
	@Test
	@Transactional
	public void tryCreateCreditCardPaymentWithoutCreditToken() throws Exception {

		payment.setCreditCard(null);
		
		restCreditCardPaymentMockMvc.perform(post(API_CREDITCARD_PAYMENT).contentType(TestUtil.APPLICATION_JSON_UTF8)
				.header("CLIENT-ID", DEFAULT_CLIENT_ID).content(TestUtil.convertObjectToJsonBytes(payment)))
				.andExpect(status().is4xxClientError());

	}

}
