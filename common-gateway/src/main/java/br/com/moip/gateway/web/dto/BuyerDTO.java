package br.com.moip.gateway.web.dto;

public class BuyerDTO {

	private String name;
	private String cpf;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	@Override
	public String toString() {
		return "ClientDTO [name=" + name + ", cpf=" + cpf + "]";
	}
}
