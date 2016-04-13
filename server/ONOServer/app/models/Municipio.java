package models;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.Index;

class MunicipioKey implements Serializable{
	private static final long serialVersionUID = 8781958145090745747L;
	int codEstado;
	int codMunicipio;
	int codDistrito;
}

@Entity
@IdClass(MunicipioKey.class)
public class Municipio {
	//NO_REGIAO;FK_COD_ESTADO;SIGLA;PK_COD_MUNICIPIO;NO_MUNICIPIO;PK_COD_DISTRITO;NO_DISTRITO
	
	@Id
	int codEstado;
	@Id
	int codMunicipio;
	@Id
	int codDistrito;
	
	@Column(nullable = false)
	@Index(name = "regiao")
	String regiao;
	
	@Column(nullable = false)
	@Index(name = "siglaEstado")
	String siglaEstado;
	
	@Column(nullable = false)
	String nomeMunicipio;
	
	@Column(nullable = false)
	String nomeDistrito;
	
	@Column
	double latitude;
	
	@Column
	double longitude;
	
	@Column
	boolean capital;
	
	private static HashMap<String, String> nomeEstado;
	
	//Monta o conversor do nome do estado automaticamente
	{
		nomeEstado = new HashMap<String, String>();
		nomeEstado.put("AC","Acre");
		nomeEstado.put("AL","Alagoas");
		nomeEstado.put("AP","Amapá");
		nomeEstado.put("AM","Amazonas");
		nomeEstado.put("BA","Bahia");
		nomeEstado.put("CE","Ceará");
		nomeEstado.put("DF","Distrito Federal");
		nomeEstado.put("ES","Espírito Santo");
		nomeEstado.put("GO","Goiás");
		nomeEstado.put("MA","Maranhão");
		nomeEstado.put("MT","Mato Grosso");
		nomeEstado.put("MS","Mato Grosso do Sul");
		nomeEstado.put("MG","Minas Gerais");
		nomeEstado.put("PA","Pará");
		nomeEstado.put("PB","Paraíba");
		nomeEstado.put("PR","Paraná");
		nomeEstado.put("PE","Pernambuco");
		nomeEstado.put("PI","Piauí");
		nomeEstado.put("RJ","Rio de Janeiro");
		nomeEstado.put("RN","Rio Grande do Norte");
		nomeEstado.put("RS","Rio Grande do Sul");
		nomeEstado.put("RO","Rondônia");
		nomeEstado.put("RR","Roraima");
		nomeEstado.put("SC","Santa Catarina");
		nomeEstado.put("SP","São Paulo");
		nomeEstado.put("SE","Sergipe");
		nomeEstado.put("TO","Tocantins");
	}
	
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public int getCodEstado() {
		return codEstado;
	}
	public void setCodEstado(int codEstado) {
		this.codEstado = codEstado;
	}
	public String getSiglaEstado() {
		return siglaEstado;
	}
	public String getNomeEstado(){
		return nomeEstado.get(siglaEstado.toUpperCase());
	}
	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}
	public int getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(int codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getNomeMunicipio() {
		return nomeMunicipio;
	}
	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}
	public int getCodDistrito() {
		return codDistrito;
	}
	public void setCodDistrito(int codDistrito) {
		this.codDistrito = codDistrito;
	}
	public String getNomeDistrito() {
		return nomeDistrito;
	}
	public void setNomeDistrito(String nomeDistrito) {
		this.nomeDistrito = nomeDistrito;
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
	public boolean isCapital() {
		return capital;
	}
	public void setCapital(boolean capital) {
		this.capital = capital;
	}
	@Override
	public String toString(){
		return nomeDistrito + " - " + nomeMunicipio + " - " + siglaEstado + " - " + regiao; 
	}
	
}
