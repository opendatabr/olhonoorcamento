package services;

import java.util.ArrayList;
import java.util.List;
import play.db.jpa.JPA;

import models.EnderecoPagamento;
import models.PlanoAplicacao;

public class ConvenioService {

	public static List<PlanoAplicacao> buscaAplicacoes(String estado,
			String cidade) {
		
		String query = "FROM PlanoAplicacao WHERE cidade = :cidade AND uf = :estado AND numConvenio > 0";
		List<PlanoAplicacao> results = JPA.em().createQuery(query)
				.setParameter("cidade", cidade)
				.setParameter("estado", estado)
				.getResultList();
		
		return results;
	}

	public static List<EnderecoPagamento> buscaEnderecos(String estado, String cidade) {
		String query = "FROM EnderecoPagamento " + 
				"WHERE cidade = :cidade AND estado = :estado " +
				"ORDER BY total DESC";
		
		List<EnderecoPagamento> resultList = JPA.em().createQuery(query)
				.setParameter("cidade", cidade)
				.setParameter("estado", estado)
				.getResultList();
		
		return resultList;
	}

	public static List<EnderecoPagamento> buscaEnderecosSemLocalizacao(String estado, String cidade) {
		String query = "FROM EnderecoPagamento " + 
				"WHERE cidade = :cidade AND estado = :estado " +
				"AND latitude = 0 " +
				"ORDER BY total DESC";
		
		List<EnderecoPagamento> resultList = JPA.em().createQuery(query)
				.setParameter("cidade", cidade)
				.setParameter("estado", estado)
				.getResultList();
		
		return resultList;
	}
	
	public static List<EnderecoPagamento> buscaEnderecos(String estado) {
		String query = "FROM EnderecoPagamento " + 
				"WHERE estado = :estado " +
				"ORDER BY total DESC";
		
		List<EnderecoPagamento> resultList = JPA.em().createQuery(query)
				.setParameter("estado", estado)
				.getResultList();
		
		return resultList;
	}
	
	public static List<EnderecoPagamento> buscaEnderecosSemLocalizacao(String estado) {
		String query = "FROM EnderecoPagamento " + 
				"WHERE estado = :estado " +
				"AND latitude = 0 " +
				"AND total > 0 " +
				"ORDER BY total DESC";
		
		List<EnderecoPagamento> resultList = JPA.em().createQuery(query)
				.setParameter("estado", estado)
				.getResultList();
		
		return resultList;
	}
	
	public static List<EnderecoPagamento> buscaEnderecos() {
		String query = "FROM EnderecoPagamento " + 
				"ORDER BY total DESC";
		
		List<EnderecoPagamento> resultList = JPA.em().createQuery(query)
				.getResultList();
		
		return resultList;
	}
	
	public static List<EnderecoPagamento> buscaEnderecosSemLocalizacao() {
		String query = "FROM EnderecoPagamento " + 
				"AND latitude = 0 " +
				"ORDER BY total DESC";
		
		List<EnderecoPagamento> resultList = JPA.em().createNativeQuery(query)
				.getResultList();
		
		return resultList;
	}

	public static int updateLocalizacaoEndereco(String cep, double lat, double lng) {
		String query = "UPDATE PlanoAplicacao set latitude =:lat, longitude =:lng " +
				"WHERE cep =:cep";
		
		try {
			int updated = JPA.em().createNativeQuery(query)
				.setParameter("lat", lat)
				.setParameter("lng", lng)
				.setParameter("cep", cep)
				.executeUpdate();
			return updated;
		} catch (Exception e){
			return 0;
		}
		
	}

	public static int consolidaCepsEstado(String estado) {
		String query = "INSERT INTO EnderecoPagamento " +
			"(SELECT cep, cidade, endereco, uf, latitude, longitude, sum(valorTotal) " + 
			"FROM planoaplicacao " + 
			"WHERE numConvenio > 0 and uf=:estado " +
			"GROUP BY cep) " +
			"ON DUPLICATE KEY UPDATE cep = cep;";
		
		try {
			int updated = JPA.em().createNativeQuery(query)
				.setParameter("estado", estado)
				.executeUpdate();
			return updated;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}

}
