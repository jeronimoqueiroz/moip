package br.com.moip.builder;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.moip.gateway.config.MoipProperties;
import br.com.moip.gateway.domain.Boleto;
import br.com.moip.gateway.domain.PaymentStatus;
import br.com.moip.gateway.web.dto.PaymentDTO;

@Component
public class BoletoBuilder extends AbstractBarcodeBuilder{
	
	@Inject
    private MoipProperties moipProperties;
	
	

	public String geraCampo1(PaymentDTO payment) {
		
		String campo = Integer.toString(moipProperties.getAccount().getNumeroDoBanco()) 
				+ Integer.toString(moipProperties.getAccount().getMoeda())
				+ moipProperties.getAccount().getNossoNumero().substring(0, 5);
		campo = campo + geraDigitoVerificador(campo);
		campo = completaComZeros(campo, 10);
		campo = divideComPonto(campo);
		return campo;
	}

	public String geraCampo2(PaymentDTO payment) {
		String campo = moipProperties.getAccount().getNossoNumero().substring(moipProperties.getAccount().getNossoNumero().length() - 6)
				+ completaComZeros(Integer.toString(moipProperties.getAccount().getAgencia()), 4);
		campo = campo + geraDigitoVerificador(campo);
		campo = completaComZeros(campo, 10);
		campo = divideComPonto(campo);
		return campo;
	}

	public String geraCampo3(PaymentDTO payment) {
		String campo = completaComZeros(Integer.toString(moipProperties.getAccount().getContaCorrente()), 8)
				+ completaComZeros(moipProperties.getAccount().getCarteira(), 2);
		campo = campo + geraDigitoVerificador(campo);
		campo = completaComZeros(campo, 10);
		campo = divideComPonto(campo);
		return campo;
	}

	public String geraCampo4(PaymentDTO payment) {
		return geraFatorDeVencimento() + converteValor(payment.getValue());
	}

	public int geraDigitoControlador(PaymentDTO payment) {
		String campo = completaComZeros(Integer.toString(moipProperties.getAccount().getNumeroDoBanco()), 3)
				+ Integer.toString(moipProperties.getAccount().getMoeda())
				+ Integer.toString(geraFatorDeVencimento()) + converteValor(payment.getValue())
				+ completaComZeros(moipProperties.getAccount().getNossoNumero(), 11)
				+ completaComZeros(Integer.toString(moipProperties.getAccount().getAgencia()), 4)
				+ completaComZeros(Integer.toString(moipProperties.getAccount().getContaCorrente()), 8)
				+ completaComZeros(moipProperties.getAccount().getCarteira(), 2);
		int soma = 0;
		int[] multArray = { 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6,
				5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
		for (int i = campo.length() - 1; i >= 0; i--) {
			int mult = Integer.parseInt("" + campo.charAt(i)) * multArray[i];
			soma += mult;
		}
		int dv = 11 - (soma % 11);
		if (dv == 1 || dv == 0 || dv == 10) {
			dv = 1;
		}
		return dv;
	}

	public String geraLinhaDigitavel(PaymentDTO payment) {
		return geraCampo1(payment) + " " + geraCampo2(payment) + " " + geraCampo3(payment) + " "
				+ geraDigitoControlador(payment) + " " + geraCampo4(payment);
	}

	@Override
	public Boleto buildFieldsDocument(PaymentDTO payment) {
		
		Boleto boleto = new Boleto();
		
		String barcode =  completaComZeros(Integer.toString(moipProperties.getAccount().getNumeroDoBanco()), 3) + Integer.toString(moipProperties.getAccount().getMoeda())
				+ Integer.toString(geraDigitoControlador(payment))
				+ Integer.toString(geraFatorDeVencimento()) + converteValor(payment.getValue())
				+ completaComZeros(moipProperties.getAccount().getNossoNumero(), 11)
				+ completaComZeros(Integer.toString(moipProperties.getAccount().getAgencia()), 4)
				+ completaComZeros(Integer.toString(moipProperties.getAccount().getContaCorrente()), 8)
				+ completaComZeros(moipProperties.getAccount().getCarteira(), 2);
		
		boleto.setBarcode(barcode);
		boleto.setValue(payment.getValue());
		boleto.setDateExpire(generateDtVencimento());
		boleto.setStatus(PaymentStatus.PENDING);
		
		
		return boleto;
		
	}


}
