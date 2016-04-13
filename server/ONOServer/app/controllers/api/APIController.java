package controllers.api;

import static play.data.Form.form;
import play.Logger;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.api.APIService;
import util.AdminJson;

public class APIController extends Controller {
  
	public static Result index() {
		return TODO;
    }
	
	@Transactional
    public static Result mapa() {
    	response().setContentType("application/json; charset=utf-8");
		response().setHeader("Access-Control-Allow-Origin","*");
		response().setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		try{			
			DynamicForm dynamicForm = form().bindFromRequest();
			String uf = (dynamicForm.get("uf") == null || dynamicForm.get("uf").trim().isEmpty())? null : dynamicForm.get("uf");
			String cidade = (dynamicForm.get("cidade") == null || dynamicForm.get("cidade").trim().isEmpty())? null : dynamicForm.get("cidade");

			if(cidade != null && uf != null){
				return ok(AdminJson.getObject(APIService.selectPlanoAplicacaoPorCidade(cidade, uf), "PlanoAplicacao"));
			}else if(uf != null){
				return ok(AdminJson.getObject(APIService.selectPlanoAplicacaoPorEstado(uf), "PlanoAplicacao"));
			}
		}catch(Exception e){
			Logger.error("ERRO - APIController/mapa():\n"+ e.getMessage());
		}
		return badRequest(AdminJson.getMensagem(AdminJson.msgConsulteAPI));
    }
    
    @Transactional
    public static Result planoAplicacao(long id) {
    	response().setContentType("application/json; charset=utf-8");
		response().setHeader("Access-Control-Allow-Origin","*");
		response().setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		try{			
			return ok(AdminJson.getObject(APIService.selectDetalhesPlanoAplicacaoPorId(id), "PlanoAplicacao"));
		}catch(Exception e){
			Logger.error("ERRO - APIController/planoAplicacao():\n"+ e.getMessage());
		}
		return badRequest(AdminJson.getMensagem(AdminJson.msgConsulteAPI));
    }
    
    @Transactional
    public static Result convenio(int id) {
    	response().setContentType("application/json; charset=utf-8");
		response().setHeader("Access-Control-Allow-Origin","*");
		response().setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		try{			
			return ok(AdminJson.getObject(APIService.selectDetalhesConvenioPorId(id), "Convenio"));
		}catch(Exception e){
			Logger.error("ERRO - APIController/convenio():\n"+ e.getMessage());
		}
		return badRequest(AdminJson.getMensagem(AdminJson.msgConsulteAPI));
    }
  
}
