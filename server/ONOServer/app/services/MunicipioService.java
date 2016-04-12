package services;

import java.util.List;

import play.db.jpa.JPA;

import models.Municipio;

public class MunicipioService {

	public static List<Municipio> getMunicipios(String estado){
		List<Municipio> municipios;
		
		if (!estado.equals("DF")){
			String query = "FROM Municipio " +
					" WHERE codDistrito = 5 " + 
					" AND siglaEstado=:estado";
			
			municipios = JPA.em().createQuery(query)
				.setParameter("estado", estado)
				.getResultList();
		} else {//o DF não tem uma "capital", mas coloquei como sendo Brasilia mesmo
			String query = "FROM Municipio " +
					" WHERE codDistrito = 6 " + 
					" AND siglaEstado=:estado";
			
			municipios = JPA.em().createQuery(query)
				.setParameter("estado", estado)
				.getResultList();
		}
		
		if (municipios.isEmpty()){
			return null;
		} else {
			return municipios;
		}
	}
	
	/**
	 * Útil para pegar um município qualquer do estado para saber de qual região ele é
	 * @param estado
	 * @return
	 */
	public static Municipio getUmMunicipioDoEstado(String estado){
		String query = "FROM Municipio " + 
				" WHERE siglaEstado=:estado";
		
		try {
			return (Municipio)JPA.em().createQuery(query)
				.setParameter("estado", estado)
				.setMaxResults(1)
				.getSingleResult();
		} catch (Exception e){
			return null;
		}
	}
	
	public static List<Municipio> getMunicipiosSemLocalizacao(){

		String query = "FROM Municipio where latitude = 0";
		List<Municipio> municipios = JPA.em().createQuery(query).getResultList();
		
		return municipios;
	}
	
	public static List<Municipio> getMunicipiosComLocalizacao(){

		String query = "FROM Municipio where latitude != 0";
		List<Municipio> municipios = JPA.em().createQuery(query).getResultList();
		
		return municipios;
	}
	
	public static Municipio getMunicipio(int codDistrito, int codMunicipio){

		String query = "FROM Municipio WHERE codDistrito =:distrito AND codMunicipio = :municipio";
		Municipio m = (Municipio)JPA.em().createQuery(query)
				.setParameter("distrito", codDistrito)
				.setParameter("municipio", codMunicipio)
				.getSingleResult();
		
		return m;
	}
	
	public static Municipio getMunicipio(int codMunicipio){
		String query = "FROM Municipio WHERE codDistrito =:distrito AND codMunicipio = :municipio";
		
		Municipio m;
		
		try {
			m = (Municipio)JPA.em().createQuery(query)
					.setParameter("distrito", 5)
					.setParameter("municipio", codMunicipio)
					.getSingleResult();
		} catch (Exception e){//nos casos em que não tem distrito 5, como o DF. Aí pega Brasilia, codigo 6
			m = (Municipio)JPA.em().createQuery(query)
					.setParameter("distrito", 6)
					.setParameter("municipio", codMunicipio)
					.getSingleResult();
		}
		
		return m;
	}

	public static Municipio getMunicipio(String estado, String cidade) {
		String query = "FROM Municipio WHERE nomeMunicipio = :cidade" +
				" AND siglaEstado = :estado" +
				" AND nomeDistrito = nomeMunicipio";
		
		try {
			return(Municipio)JPA.em().createQuery(query)
					.setParameter("cidade", cidade)
					.setParameter("estado", estado)
					.getSingleResult();
		} catch (Exception e){
			return null;
		}
	}
	
}
