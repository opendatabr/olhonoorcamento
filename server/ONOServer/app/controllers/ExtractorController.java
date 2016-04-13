package controllers;

import extractors.ConvenioExtractor;
import extractors.ExtractorResults;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import services.GoogleLocationService;

public class ExtractorController extends Controller {

	@Transactional
	public static Result processaConvenios() {
		ExtractorResults er = ConvenioExtractor.processaConvenios();
		Logger.info(er.toString());
		
        return ok(er.toString());
    }
	
	@Transactional
	public static Result processaLatLongCidades(){
		ExtractorResults er = new ExtractorResults("Processamento de latitude e longitude das cidades");
		
		//Não precisei refazer, aproveitei a tabela do Educacao Inteligente
		//GoogleLocationService.processaLatLongCidades();
		
		return ok("Sem processamento ainda");
		
	}
	
}
