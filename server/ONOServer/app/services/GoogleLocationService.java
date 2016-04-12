package services;

import java.util.Date;
import java.util.List;

import models.Municipio;
import models.util.EnderecoPagamento;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.WS;
import play.libs.F.Promise;

public class GoogleLocationService {

	public static void processaLatLongEnderecos(String estado, String cidade) {
		List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecosSemLocalizacao(estado, cidade);
		processaLatLongEnderecos(enderecos);
	}
	
	public static void processaLatLongEnderecos(String estado) {
		List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecosSemLocalizacao(estado);
		processaLatLongEnderecos(enderecos);
	}
	
	public static void processaLatLongEnderecos() {
		List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecosSemLocalizacao();
		processaLatLongEnderecos(enderecos);
	}
	
	public static void processaLatLongEnderecos(List<EnderecoPagamento> enderecos) {
		Date dataIni = new Date();
		int count = 0;
		
		//trata o endereço. tira o nome da cidade e do estado, virgula, traço e ponto
		for (EnderecoPagamento e: enderecos){
			String texto = e.getEndereco();
			texto = texto.replace(e.getCidade(), "");
			texto = texto.replace(e.getEstado(), "");
			texto = texto.replace(e.getCep(), "");
			texto = texto.replace(",", " ");
			texto = texto.replace("-", " ");
			texto = texto.replace(".", " ");
			texto = texto.replace("  ", " ");
			texto = texto.trim();
			e.setEndereco(texto);
		}
		
		//Primeiro busca pelo endereço. Se for localização exata, beleza. Senão, busca pelo cep
		
		Promise<WS.Response> response;
		for (EnderecoPagamento e: enderecos){
			String endereco = e.getEndereco() + "," + e.getCidade() + "," + e.getEstado() + ", Brasil";
			
			//exemplo: http://maps.googleapis.com/maps/api/geocode/xml?address=antonio%20nogueira&region=br&components=country:BR&sensor=false
			 response = WS.url("http://maps.googleapis.com/maps/api/geocode/xml")
			.setQueryParameter("address", endereco)
			.setQueryParameter("region", "br")
			.setQueryParameter("components", "country:BR")
			.setQueryParameter("sensor", "false")
			.get();
	
			org.w3c.dom.Document doc = response.get().asXml();
			doc.getDocumentElement().normalize();
			
			//Process XML
			String lat = doc.getElementsByTagName("lat").item(0).getTextContent();
			String lng = doc.getElementsByTagName("lng").item(0).getTextContent();
			
			e.setLatitude(Double.parseDouble(lat));
			e.setLongitude(Double.parseDouble(lng));
			//TODO
			//fazer um update forte em todos os endereços que tenham esse cep
			//JPA.em().persist(m);
			
			if (count++ % 100 == 0){
				Logger.info("Processando endereco " + count + ", de " + enderecos.size() + " - " + new Date());
				//JPA.em().getTransaction().commit();
				//JPA.em().getTransaction().begin();
				//System.gc();
			}
		}
		
		Date dataFim= new Date();
		Logger.info("Processado em " + (dataFim.getTime() - dataIni.getTime())/1000 + "s");
		
	}
	
	/*public static void processaLatLongCidades() {
		Date dataIni = new Date();
		int count = 0;
		
		List<Municipio> municipios = ApplicationService.getMunicipiosSemLocalizacao();
		
		Promise<WS.Response> response;
		for (Municipio m: municipios){
			String endereco = m.getNomeDistrito() + ", " + m.getNomeMunicipio() +", " + m.getSiglaEstado();
			
			//exemplo: http://maps.googleapis.com/maps/api/geocode/xml?address=antonio%20nogueira&region=br&components=country:BR&sensor=false
			 response = WS.url("http://maps.googleapis.com/maps/api/geocode/xml")
			.setQueryParameter("address", endereco)
			.setQueryParameter("region", "br")
			.setQueryParameter("components", "country:BR")
			.setQueryParameter("sensor", "false")
			.get();
	
			org.w3c.dom.Document doc = response.get().asXml();
			doc.getDocumentElement().normalize();
			
			//Process XML
			String lat = doc.getElementsByTagName("lat").item(0).getTextContent();
			String lng = doc.getElementsByTagName("lng").item(0).getTextContent();
			
			m.setLatitude(Double.parseDouble(lat));
			m.setLongitude(Double.parseDouble(lng));
			JPA.em().persist(m);
			
			if (count++ % 50 == 0){
				Logger.info("Processando municipio " + count);
				JPA.em().getTransaction().commit();
				JPA.em().getTransaction().begin();
				System.gc();
			}
		}
		
		Date dataFim= new Date();
		Logger.info("Processado em " + (dataFim.getTime() - dataIni.getTime())/1000 + "s");
		
	}*/

}
