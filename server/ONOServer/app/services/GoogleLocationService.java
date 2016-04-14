package services;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;

import extractors.ExtractorResults;

import models.EnderecoPagamento;
import models.Municipio;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.WS;
import play.libs.F.Promise;

public class GoogleLocationService {
	
	private static final String[] estados = {
		"PE", "SP", "DF", "RJ", "MG","AC","AL","AP","AM","BA",
		"CE","ES","GO","MA","MT","MS","PA","PB","PR","PI","RN",
		"RS","RO","RR","SC","SE","TO"};
	
	private static final String[] chaves = {
		"chave1",
		"chave2",
		"chave3",
		"chave4"
	};
	
	public static ExtractorResults processaLatLongEnderecos(String estado) {
		//List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecosSemLocalizacao(estado);
		//return processaLatLongEnderecos(enderecos);
		return null;
	}
	
	public static ExtractorResults processaLatLongEnderecos() {
		//List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecosSemLocalizacao();
		ExtractorResults er = new ExtractorResults("###Processamento de Latitude e Longitude dos Endereços###");
		ExtractorResults erInner;
		
		List<EnderecoPagamento> enderecos;
		for (int c = 0; c < chaves.length; c++){
			String chave = chaves[c];
			for (int i = 0; i < estados.length; i++){
				String estado = estados[i];
				Logger.info("Processando estado + " + estado + " - " + new Date());
				
				enderecos =  ConvenioService.buscaEnderecosSemLocalizacao(estado);
				erInner = processaLatLongEnderecos(enderecos, chave);
				
				Logger.info(erInner.toString());
				er.add(erInner);
			}
		}
		return er;
	}
	
	public static ExtractorResults processaLatLongEnderecos(List<EnderecoPagamento> enderecos, String chave) {
		ExtractorResults er = new ExtractorResults();
				
		for (EnderecoPagamento e: enderecos){
			er.registros++;
			String endereco = e.getEnderecoAjustado();
			
			org.w3c.dom.Document doc = getMapsInfo(endereco, chave);
			String status = doc.getElementsByTagName("status").item(0).getTextContent();
			
			if (status.equals("OK")){
				
				processaRetorno(doc, e, er);
				
			} else if (status.equals("ZERO_RESULTS")){	//indica que esse endereço está bugado
				Logger.info("Deu ZERO_RESULTS para o endereco " + endereco);
				
				//tentar removendo a ultima palavra. as vezes tem o bairro, que atrapalha
				endereco = e.getEnderecoAjustado2();
				doc = getMapsInfo(endereco, chave);			
				status = doc.getElementsByTagName("status").item(0).getTextContent();
				
				if (status.equals("OK")){
					
					processaRetorno(doc, e, er);
					
				} else if (status.equals("ZERO_RESULTS")){ //deu zero novamente, pega pelo cep
					Logger.info("Deu ZERO_RESULTS para o endereco " + endereco);
				
					//tentar pelo cep
					endereco = e.getCep() + ", " + e.getCidade() + ", " + e.getEstado() + ", Brasil";
					doc = getMapsInfo(endereco, chave);
					status = doc.getElementsByTagName("status").item(0).getTextContent();
					
					if (status.equals("OK")){
						
						processaRetorno(doc, e, er);
					
					} else {//não teve jeito, não achou mesmo
						erroProcessando(doc, e, er);
					}
				} else {
					erroProcessando(doc, e, er);
				}
			} else if (status.equals("OVER_QUERY_LIMIT")){
				Logger.error("############ OVER QUERY LIMIT ################");
				er.erros++;
				break;
			} else { //outros erros buscando o endereco
				erroProcessando(doc, e, er);
			}
			
			if (er.registros % 50 == 0){
				Logger.info("Processando endereco " + er.registros + ", de " + enderecos.size() + " - " + new Date());
				JPA.em().getTransaction().commit();
				JPA.em().getTransaction().begin();
				//System.gc();
			}
		}
		
		Logger.info(er.toString());
		
		return er;
		
	}

	private static void erroProcessando(Document doc, EnderecoPagamento e, ExtractorResults er) {
		er.erros++;
		String message = "";
		
		String status = doc.getElementsByTagName("status").item(0).getTextContent();
		
		try { message = doc.getElementsByTagName("error_message").item(0).getTextContent();
		} catch (Exception ex){}
		
		Logger.error("Erro buscando endereco (" + e.getEndereco() + " - " + e.getCep() + "):"
				+ "Status: " + status + "; Message = " + message);
		
	}

	private static void processaRetorno(Document doc, EnderecoPagamento e, ExtractorResults er) {
		//Process XML
		String lat = doc.getElementsByTagName("lat").item(0).getTextContent();
		String lng = doc.getElementsByTagName("lng").item(0).getTextContent();
		
		//Salva sozinho em EnderecoPagamento sem precisar fazer nada
		e.setLatitude(Double.parseDouble(lat));
		e.setLongitude(Double.parseDouble(lng));
		
		//Salva na tabela de planoAplicacao, onde tem os enderecos de verdade
		int updated = ConvenioService.updateLocalizacaoEndereco(e.getCep(), e.getLatitude(), e.getLongitude());
		if (!(updated>0)){
			Logger.info("Não atualizou as linhas do cep " + e.getCep() + "! Saindo...");
			er.erros++;
		} else {
			er.acertos++;
		}
		
	}

	private static Document getMapsInfo(String endereco, String chave) {
		//exemplo: http://maps.googleapis.com/maps/api/geocode/xml?address=antonio%20nogueira&region=br&components=country:BR&sensor=false
		Promise<WS.Response> response = WS.url("https://maps.googleapis.com/maps/api/geocode/xml")
		.setQueryParameter("address", endereco)
		.setQueryParameter("region", "br")
		.setQueryParameter("components", "country:BR")
		.setQueryParameter("sensor", "false")
		.setQueryParameter("key", chave)
		.get();

		org.w3c.dom.Document doc = response.get().asXml();
		doc.getDocumentElement().normalize();
		
		return doc;
	}
	
	//Isso foi feito no projeto do EducacaoInteligente. Apenas importei a tabela
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
