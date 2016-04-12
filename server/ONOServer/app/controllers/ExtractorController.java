package controllers;

import extractors.ConvenioExtractor;
import extractors.ExtractorResults;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class ExtractorController extends Controller {

	@Transactional
	public static Result processaConvenios() {
		ExtractorResults er = ConvenioExtractor.processaConvenios();
		Logger.info(er.toString());
		
        return ok(er.toString());
    }
	
}
