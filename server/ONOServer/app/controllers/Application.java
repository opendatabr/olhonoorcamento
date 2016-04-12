package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
	public static Result index() {
		return ok(views.html.index.render());
    }
	
    public static Result showMapa(String estado) {
        return TODO;
    }
    
    public static Result showMapaCidade(String estado, String cidade) {
        return TODO;
    }
    
    public static Result showConvenio(String id) {
        return TODO;
    }
  
}
