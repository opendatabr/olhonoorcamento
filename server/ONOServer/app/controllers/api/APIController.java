package controllers.api;

import static play.data.Form.form;

import java.util.HashMap;

import play.Logger;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.ConvenioService;
import services.MunicipioService;
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
			String estado = (dynamicForm.get("estado") == null || dynamicForm.get("estado").trim().isEmpty())? null : dynamicForm.get("estado");
			String cidade = (dynamicForm.get("cidade") == null || dynamicForm.get("cidade").trim().isEmpty())? null : dynamicForm.get("cidade");
			String cep = (dynamicForm.get("cep") == null || dynamicForm.get("cep").trim().isEmpty())? null : dynamicForm.get("cep");
			
			if(cep != null && cidade != null && estado != null){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Convenios", ConvenioService.getConveniosExecutadosEm(cep));
				map.put("Endereco", ConvenioService.getEndereco(cep));
				return ok(AdminJson.getObject(map, "Pagamento"));
				
			}else if(cidade != null && estado != null){
				return ok(AdminJson.getObject(ConvenioService.buscaEnderecos(estado, cidade), "EnderecoPagamentos"));
				
			}else if(estado != null){
				return ok(AdminJson.getObject(MunicipioService.getMunicipios(estado), "Cidades"));
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
