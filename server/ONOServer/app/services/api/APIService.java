package services.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Convenio;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class APIService {
	
	@Transactional
	public static List<HashMap<String,Object>> selectPlanoAplicacaoPorEstado(String uf){
		String query = "SELECT id, endereco, nomeNaturezaDespesa "
				+ "FROM PlanoAplicacao "
				+ "WHERE uf = :uf "
				+ "ORDER BY anoProposta, id DESC";
    	List<Object> lo = JPA.em().createNativeQuery(query)
    			.setParameter("uf", uf)
    			.setMaxResults(250) // limitado por que retorna uma porrada de dados
    			.getResultList();
    	
    	List<HashMap<String,Object>> listaResultado = new ArrayList<HashMap<String,Object>>();
    	HashMap<String, Object> map = null;
    	
    	for (Object o : lo) {
    		Object[] itens = (Object[]) o;
    		map = new HashMap<String, Object>();
			 
			map.put("id", ((BigInteger)itens[0]).longValue());
			map.put("endereco", (String)itens[1]);
			map.put("nomeNaturezaDespesa", (String)itens[2]);
			 
			listaResultado.add(map);
		}
    	
    	return listaResultado;
	}
	
	@Transactional
	public static List<HashMap<String,Object>> selectPlanoAplicacaoPorCidade(String cidade, String uf){
		String query = "SELECT id, endereco, nomeNaturezaDespesa "
				+ "FROM PlanoAplicacao "
				+ "WHERE uf = :uf AND cidade = :cidade "
				+ "ORDER BY anoProposta DESC";
    	List<Object> lo = JPA.em().createNativeQuery(query)
    			.setParameter("uf", uf)
    			.setParameter("cidade", cidade)
    			.setMaxResults(250) // limitado por que retorna uma porrada de dados
    			.getResultList();
    	
    	List<HashMap<String,Object>> listaResultado = new ArrayList<HashMap<String,Object>>();
    	HashMap<String, Object> map = null;
    	
    	for (Object o : lo) {
    		Object[] itens = (Object[]) o;
    		map = new HashMap<String, Object>();
			 
			map.put("id", ((BigInteger)itens[0]).longValue());
			map.put("endereco", (String)itens[1]);
			map.put("nomeNaturezaDespesa", (String)itens[2]);
			 
			listaResultado.add(map);
		}
    	
    	return listaResultado;
	}
	
	@Transactional
	public static HashMap<String,Object> selectDetalhesPlanoAplicacaoPorId(long id){
		String query = "SELECT id, endereco, nomeNaturezaDespesa, uf, cidade, tipoDespesa, anoConvenio, "
				+ "anoProposta, situacao, unidadeFornecimento, valorUnitario, valorTotal, qtdItems, "
				+ "descricaoDespesa, idConvenio "
				+ "FROM PlanoAplicacao "
				+ "WHERE id = :id";
    	List<Object> lo = JPA.em().createNativeQuery(query)
    			.setParameter("id", id)
    			.getResultList();
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	if(lo.isEmpty()){
    		return null;
    	}else{
    		Object[] itens = (Object[]) lo.get(0);
    		map = new HashMap<String, Object>();
			 
			map.put("id", ((BigInteger)itens[0]).longValue());
			map.put("endereco", (String)itens[1]);
			map.put("nomeNaturezaDespesa", (String)itens[2]);
			map.put("uf", (String)itens[3]);
			map.put("cidade", (String)itens[4]);
			map.put("tipoDespesa", (String)itens[5]);
			map.put("anoConvenio", (Integer)itens[6]);
			map.put("anoProposta", (Integer)itens[7]);
			map.put("situacao", (String)itens[8]);
			map.put("unidadeFornecimento", (String)itens[9]);
			map.put("valorUnitario", (Float)itens[10]);
			map.put("valorTotal", (Float)itens[11]);
			map.put("qtdItems", (Float)itens[12]);
			map.put("descricaoDespesa", (String)itens[13]);
			map.put("idConvenio", (Integer)itens[14]);
			
			return map;
    	}
	}
	
	@Transactional
	public static Convenio selectDetalhesConvenioPorId(int id){
		String query = "FROM Convenio WHERE idConvenio = :id";
    	List<Convenio> lc = JPA.em().createQuery(query)
    			.setParameter("id", id)
    			.getResultList();
    	
    	if(lc.isEmpty()){
    		return null;
    	}else{
    		return lc.get(0);
    	}
	}
}
