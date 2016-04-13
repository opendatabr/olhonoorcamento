package models.util;

import javax.persistence.Column;

public class EnderecoPagamento {

	private String cep;
	private String endereco;
	private String cidade;
	private String estado;
	private double total;
	
	double latitude;
	double longitude;
	
	public EnderecoPagamento(){
		
	}

	public EnderecoPagamento(String cep, String endereco, String cidade,
			String estado, double total) {
		super();
		this.cep = cep;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.total = total;
	}

	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getEnderecoAjustado(){
		String texto = endereco.toLowerCase();
		texto = texto.replace(getCidade().toLowerCase(), "");
		texto = texto.replace(getEstado().toLowerCase(), "");
		texto = texto.replace(getCep(), "");
		texto = texto.replace("/", " ");
		texto = texto.replace(",", " ");
		texto = texto.replace("-", " ");
		texto = texto.replace(".", " ");
		texto = texto.replace("  ", " ");
		texto = texto.trim();
		texto += ", " + getCidade() + ", " + getEstado() + ", Brasil";
		return texto;
	}
	
	@Override
	public String toString(){
		return cep + "," + endereco + "," +cidade + "," +estado + "," +total;
	}
	
}
