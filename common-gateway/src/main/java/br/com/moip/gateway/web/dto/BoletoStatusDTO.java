package br.com.moip.gateway.web.dto;

public class BoletoStatusDTO extends StatusDTO{
	
	private String barcode;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
