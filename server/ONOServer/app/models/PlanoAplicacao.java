package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class PlanoAplicacao {
	
	//ANO_PROPOSTA;NR_PROPOSTA;TX_SITUACAOPROPOSTA;TX_MODALIDADE;ANO_CONVENIO;
	//NR_CONVENIO;CD_ORGAO_CONCEDENTE;NM_ORGAO_CONCEDENTE;CD_IDENT_PROPONENTE;NM_IDENT_PROPONENTE;
	//TP_IDENT_PROPONENTE;TX_ESFERA_ADM_PROPONENTE;NM_MUNICIPIO_PROPONENTE;UF_PROPONENTE;TX_REGIAO_PROPONENTE;
	//TP_DESPESA;TX_NATUREZAAQUISICAO;TX_DESCRICAODESPESA;CD_NATUREZADESPESA;NM_NATUREZADESPESA;
	//QD_NATUREZADESPESA;VL_UNITARIO;VL_TOTAL;TX_UNIDADEFORNECIMENTO;TX_ENDERECO;
	//TX_MUNICIPIO;CD_MUNICIPIO;UF_DESPESA;NR_CEP;TX_SITUACAODESPESA;
	//ID_CONVENIO;ID_PROPOSTA
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	@Index(name = "numConvenio")
	private int numConvenio;
	@Column
	@Index(name = "idConvenio")
	private int idConvenio;
	@Column
	private int anoConvenio;
	@Column
	@Index(name = "numProposta")
	private int numProposta;
	@Column
	@Index(name = "idProposta")
	private int idProposta;
	@Column
	private int anoProposta;
	
	@Column
	private String tipoDespesa; //Bem, Serviço, Obra, Tributo, Outros ou Despesa Administrativa
	@Column
	private String descricaoDespesa;
	@Column
	private String codNaturezaDespesa;
	@Column
	private String nomeNaturezaDespesa;
	
	@Column
	private float qtdItems;
	@Column
	private float valorUnitario;
	@Column
	private float valorTotal;
	@Column
	private String unidadeFornecimento;
	
	@Column
	private String endereco;
	@Column
	private String cidade;
	@Column
	private String uf;
	@Column
	private String cep;
	@Column
	private String situacao;
	
	@Column
	private double latitude;
	@Column
	private double longitude;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNumConvenio() {
		return numConvenio;
	}
	public void setNumConvenio(int numConvenio) {
		this.numConvenio = numConvenio;
	}
	public int getIdConvenio() {
		return idConvenio;
	}
	public void setIdConvenio(int idConvenio) {
		this.idConvenio = idConvenio;
	}
	public int getAnoConvenio() {
		return anoConvenio;
	}
	public void setAnoConvenio(int anoConvenio) {
		this.anoConvenio = anoConvenio;
	}
	public int getNumProposta() {
		return numProposta;
	}
	public void setNumProposta(int numProposta) {
		this.numProposta = numProposta;
	}
	public int getIdProposta() {
		return idProposta;
	}
	public void setIdProposta(int idProposta) {
		this.idProposta = idProposta;
	}
	public int getAnoProposta() {
		return anoProposta;
	}
	public void setAnoProposta(int anoProposta) {
		this.anoProposta = anoProposta;
	}
	public String getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	public String getDescricaoDespesa() {
		return descricaoDespesa;
	}
	public void setDescricaoDespesa(String descricaoDespesa) {
		this.descricaoDespesa = descricaoDespesa;
	}
	public String getCodNaturezaDespesa() {
		return codNaturezaDespesa;
	}
	public void setCodNaturezaDespesa(String codNaturezaDespesa) {
		this.codNaturezaDespesa = codNaturezaDespesa;
	}
	public String getNomeNaturezaDespesa() {
		return nomeNaturezaDespesa;
	}
	public void setNomeNaturezaDespesa(String nomeNaturezaDespesa) {
		this.nomeNaturezaDespesa = nomeNaturezaDespesa;
	}
	public float getQtdItems() {
		return qtdItems;
	}
	public void setQtdItems(float qtdItems) {
		this.qtdItems = qtdItems;
	}
	public float getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(float valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getUnidadeFornecimento() {
		return unidadeFornecimento;
	}
	public void setUnidadeFornecimento(String unidadeFornecimento) {
		this.unidadeFornecimento = unidadeFornecimento;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
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
}
