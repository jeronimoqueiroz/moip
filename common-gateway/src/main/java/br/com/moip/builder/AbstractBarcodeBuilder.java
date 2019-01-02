package br.com.moip.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import br.com.moip.gateway.domain.Boleto;
import br.com.moip.gateway.web.dto.PaymentDTO;

public abstract class AbstractBarcodeBuilder {
	
	protected static final String DATA_BASE = "07/10/1997";
	
	public int geraDigitoVerificador(String valorCampo) {
		int fator = 2;
		int soma = 0;
		for (int i = valorCampo.length() - 1; i >= 0; i--) {
			int mult = Integer.parseInt("" + valorCampo.charAt(i)) * fator;
			mult = mult > 9 ? mult - 9 : mult;
			soma += mult;
			fator = fator == 2 ? 1 : 2;
		}
		return 10 - (soma % 10);
	}

	public Date geraData(String data) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy").parse(data);
	}

	public int geraFatorDeVencimento() {
		Date dataDeVencimento = generateDtVencimento();
		Date dataBase = null;
		try {
			dataBase = geraData(DATA_BASE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diferenca = dataDeVencimento.getTime() - dataBase.getTime();
		return (int) Math.ceil((double) diferenca / (double) (1000 * 60 * 60 * 24));
	}
	
	
	protected Date generateDtVencimento() {
		Instant currentDate = Instant.now();
		
		Instant incrementedDays = currentDate.plus(3, ChronoUnit.DAYS);
		
	    return Date.from(incrementedDays);
	}

	
	public String completaComZeros(String str, int tamanho) {
		if (str.length() < tamanho) {
			String zeros = "";
			for (int i = 0; i < tamanho - str.length(); i++) {
				zeros += "0";
			}
			return zeros + str;
		}
		return str;
	}

	public String divideComPonto(String campo) {
		return campo.substring(0, 5) + "." + campo.substring(5, campo.length());
	}
	
	public String converteValor(double valor) {
		String str = Double.toString(valor);
		int indiceSeparadorDecimal = -1;
		String ret = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '.' && str.charAt(i) != ',') {
				ret += str.charAt(i);
			} else {
				indiceSeparadorDecimal = i;
			}
		}
		if (indiceSeparadorDecimal != str.length() - 3) {
			if (indiceSeparadorDecimal > str.length() - 3) {
				for (int i = 0; i < 2 - (str.length() - indiceSeparadorDecimal - 1); i++) {
					ret += "0";
				}
			}
		}
		return completaComZeros(ret, 10);
	}
	
	public abstract Boleto buildFieldsDocument(PaymentDTO payment);

}
