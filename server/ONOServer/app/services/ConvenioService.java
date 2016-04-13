package services;

import java.util.ArrayList;
import java.util.List;
import play.db.jpa.JPA;

import models.PlanoAplicacao;
import models.util.EnderecoPagamento;

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
		String query = "SELECT cep, endereco, cidade, uf, sum(valorTotal) as total FROM PlanoAplicacao " + 
				"WHERE numConvenio > 0 AND cidade = :cidade AND uf = :estado " +
				"GROUP BY cep " + 
				"ORDER BY 5 DESC";
		
		List<Object> resultList = JPA.em().createNativeQuery(query)
				.setParameter("cidade", cidade)
				.setParameter("estado", estado)
				.getResultList();
		
		List<EnderecoPagamento> valores = new ArrayList<EnderecoPagamento>();
		EnderecoPagamento valor;
		
		for (Object result : resultList) {
		    Object[] items = (Object[]) result;
		    valor = new EnderecoPagamento((String)items[0], (String)items[1], (String)items[2], 
		    		(String)items[3], (Double)items[4]);
		    if (valor.getTotal() > 0){
		    	valores.add(valor);
		    }
		}
		
		return valores;
	}

	public static List<EnderecoPagamento> buscaEnderecosSemLocalizacao(String estado, String cidade) {
		String query = "SELECT cep, endereco, cidade, uf, sum(valorTotal) as total FROM PlanoAplicacao " + 
				"WHERE numConvenio > 0 AND cidade = :cidade AND uf = :estado " +
				" AND latitude = 0 " + 
				" GROUP BY cep " + 
				"ORDER BY 5 DESC";
		
		List<Object> resultList = JPA.em().createNativeQuery(query)
				.setParameter("cidade", cidade)
				.setParameter("estado", estado)
				.getResultList();
		
		List<EnderecoPagamento> valores = new ArrayList<EnderecoPagamento>();
		EnderecoPagamento valor;
		
		for (Object result : resultList) {
		    Object[] items = (Object[]) result;
		    valor = new EnderecoPagamento((String)items[0], (String)items[1], (String)items[2], 
		    		(String)items[3], (Double)items[4]);
		    if (valor.getTotal() > 0){
		    	valores.add(valor);
		    }
		}
		
		return valores;
	}
	
	public static List<EnderecoPagamento> buscaEnderecos(String estado) {
		String query = "SELECT cep, endereco, cidade, uf, sum(valorTotal) as total FROM PlanoAplicacao " + 
				"WHERE numConvenio > 0 AND uf = :estado " +
				"GROUP BY cep " + 
				"ORDER BY 5 DESC";
		
		List<Object> resultList = JPA.em().createNativeQuery(query)
				.setParameter("estado", estado)
				.getResultList();
		
		List<EnderecoPagamento> valores = new ArrayList<EnderecoPagamento>();
		EnderecoPagamento valor;
		
		for (Object result : resultList) {
		    Object[] items = (Object[]) result;
		    valor = new EnderecoPagamento((String)items[0], (String)items[1], (String)items[2], 
		    		(String)items[3], (Double)items[4]);
		    if (valor.getTotal() > 0){
		    	valores.add(valor);
		    }
		}
		
		return valores;
	}
	
	public static List<EnderecoPagamento> buscaEnderecosSemLocalizacao(String estado) {
		String query = "SELECT cep, endereco, cidade, uf, sum(valorTotal) as total FROM PlanoAplicacao " + 
				"WHERE numConvenio > 0 AND uf = :estado " +
				" AND latitude = 0 " + 
				"GROUP BY cep " + 
				"ORDER BY 5 DESC";
		
		List<Object> resultList = JPA.em().createNativeQuery(query)
				.setParameter("estado", estado)
				.getResultList();
		
		List<EnderecoPagamento> valores = new ArrayList<EnderecoPagamento>();
		EnderecoPagamento valor;
		
		for (Object result : resultList) {
		    Object[] items = (Object[]) result;
		    valor = new EnderecoPagamento((String)items[0], (String)items[1], (String)items[2], 
		    		(String)items[3], (Double)items[4]);
		    if (valor.getTotal() > 0){
		    	valores.add(valor);
		    }
		}
		
		return valores;
	}
	
	public static List<EnderecoPagamento> buscaEnderecos() {
		String query = "SELECT cep, endereco, cidade, uf, sum(valorTotal) as total FROM PlanoAplicacao " + 
				"WHERE numConvenio > 0 " +
				"GROUP BY cep " + 
				"ORDER BY 5 DESC";
		
		List<Object> resultList = JPA.em().createNativeQuery(query)
				.getResultList();
		
		List<EnderecoPagamento> valores = new ArrayList<EnderecoPagamento>();
		EnderecoPagamento valor;
		
		for (Object result : resultList) {
		    Object[] items = (Object[]) result;
		    valor = new EnderecoPagamento((String)items[0], (String)items[1], (String)items[2], 
		    		(String)items[3], (Double)items[4]);
		    if (valor.getTotal() > 0){
		    	valores.add(valor);
		    }
		}
		
		return valores;
	}
	
	public static List<EnderecoPagamento> buscaEnderecosSemLocalizacao() {
		String query = "SELECT cep, endereco, cidade, uf, sum(valorTotal) as total FROM PlanoAplicacao " + 
				"WHERE numConvenio > 0 " +
				" AND latitude = 0 " + 
				"GROUP BY cep " + 
				"ORDER BY 5 DESC";
		
		List<Object> resultList = JPA.em().createNativeQuery(query)
				.getResultList();
		
		List<EnderecoPagamento> valores = new ArrayList<EnderecoPagamento>();
		EnderecoPagamento valor;
		
		for (Object result : resultList) {
		    Object[] items = (Object[]) result;
		    valor = new EnderecoPagamento((String)items[0], (String)items[1], (String)items[2], 
		    		(String)items[3], (Double)items[4]);
		    if (valor.getTotal() > 0){
		    	valores.add(valor);
		    }
		}
		return valores;
	}

}
