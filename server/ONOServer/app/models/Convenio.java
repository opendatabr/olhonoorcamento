package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Index;

import services.ConvenioService;

@Entity
public class Convenio {

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
	private String modalidade;
	@Column
	private String situacao;
	
	@Column
	@Index(name = "codOrgaoSuperior")
	private int codOrgaoSuperior;
	@Column
	@Index(name = "nomeOrgaoSuperior")
	private String nomeOrgaoSuperior;
	
	@Column
	@Index(name = "codOrgaoConcedente")
	private int codOrgaoConcedente;
	@Column
	@Index(name = "nomeOrgaoConcedente")
	private String nomeOrgaoConcedente;
		
	@Column
	private Date dataInicioVigencia;
	@Column
	private Date dataFimVigencia;
	@Column
	private Date dataAssinatura;
	
	@Column
	private float valorGlobal;
	@Column
	private float valorRepasse;
	@Column
	private float valorContrapartida;
	
	@Column
	private float valorEmpenhado;
	
	@Column(columnDefinition="TEXT")
	private String objeto;
	
	@Column
	private String justificativa;
	
	@Column
	private int qtdAditivos;
	@Column
	private int qtdProrrogacao;
	
	@Column
	private String nomeProponente;
	@Column
	private String codProponente;
	@Column
	private String cargoProponente;
	@Column
	private String enderecoProponente;
	@Column
	private String bairroProponente;
	@Column
	private String cepProponente;
	
	@Column
	private String qualificacaoProponente;
	
	@JsonIgnore
	@Column
	private Date createdAt;
	
	@JsonIgnore
	@Column
	private Date updatedAt;

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

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public void setAnoProposta(int anoProposta) {
		this.anoProposta = anoProposta;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getCodOrgaoSuperior() {
		return codOrgaoSuperior;
	}

	public void setCodOrgaoSuperior(int codOrgaoSuperior) {
		this.codOrgaoSuperior = codOrgaoSuperior;
	}

	public String getNomeOrgaoSuperior() {
		return nomeOrgaoSuperior;
	}

	public void setNomeOrgaoSuperior(String nomeOrgaoSuperior) {
		this.nomeOrgaoSuperior = nomeOrgaoSuperior;
	}

	public int getCodOrgaoConcedente() {
		return codOrgaoConcedente;
	}

	public void setCodOrgaoConcedente(int codOrgaoConcedente) {
		this.codOrgaoConcedente = codOrgaoConcedente;
	}

	public String getNomeOrgaoConcedente() {
		return nomeOrgaoConcedente;
	}

	public void setNomeOrgaoConcedente(String nomeOrgaoConcedente) {
		this.nomeOrgaoConcedente = nomeOrgaoConcedente;
	}

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Date getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(Date dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

	public Date getDataAssinatura() {
		return dataAssinatura;
	}

	public void setDataAssinatura(Date dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
	}

	public float getValorGlobal() {
		return valorGlobal;
	}

	public void setValorGlobal(float valorGlobal) {
		this.valorGlobal = valorGlobal;
	}

	public float getValorRepasse() {
		return valorRepasse;
	}

	public void setValorRepasse(float valorRepasse) {
		this.valorRepasse = valorRepasse;
	}

	public float getValorContrapartida() {
		return valorContrapartida;
	}

	public void setValorContrapartida(float valorContrapartida) {
		this.valorContrapartida = valorContrapartida;
	}

	public float getValorEmpenhado() {
		return valorEmpenhado;
	}

	public void setValorEmpenhado(float valorEmpenhado) {
		this.valorEmpenhado = valorEmpenhado;
	}

	public int getQtdAditivos() {
		return qtdAditivos;
	}

	public void setQtdAditivos(int qtdAditivos) {
		this.qtdAditivos = qtdAditivos;
	}

	public int getQtdProrrogacao() {
		return qtdProrrogacao;
	}

	public void setQtdProrrogacao(int qtdProrrogacao) {
		this.qtdProrrogacao = qtdProrrogacao;
	}

	public String getQualificacaoProponente() {
		return qualificacaoProponente;
	}

	public void setQualificacaoProponente(String qualificacaoProponente) {
		this.qualificacaoProponente = qualificacaoProponente;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getNomeProponente() {
		return nomeProponente;
	}

	public void setNomeProponente(String nomeProponente) {
		this.nomeProponente = nomeProponente;
	}

	public String getCodProponente() {
		return codProponente;
	}

	public void setCodProponente(String codProponente) {
		this.codProponente = codProponente;
	}

	public String getCargoProponente() {
		return cargoProponente;
	}

	public void setCargoProponente(String cargoProponente) {
		this.cargoProponente = cargoProponente;
	}

	public String getEnderecoProponente() {
		return enderecoProponente;
	}

	public void setEnderecoProponente(String enderecoProponente) {
		this.enderecoProponente = enderecoProponente;
	}

	public String getBairroProponente() {
		return bairroProponente;
	}

	public void setBairroProponente(String bairroProponente) {
		this.bairroProponente = bairroProponente;
	}

	public String getCepProponente() {
		return cepProponente;
	}

	public void setCepProponente(String cepProponente) {
		this.cepProponente = cepProponente;
	}

	@PrePersist
	private void setCreatedAt() {
		this.createdAt = new Date();
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@PreUpdate
	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}
	
	@Transient
	public List<PlanoAplicacao> getPagamentos(){
		List<PlanoAplicacao> pagamentos = ConvenioService.getPlanosAplicacao(idConvenio);
		return pagamentos;
	}
	
}
