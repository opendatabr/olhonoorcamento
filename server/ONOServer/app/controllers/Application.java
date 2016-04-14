package controllers;

import static play.data.Form.form;

import java.util.Date;
import java.util.List;

import models.Convenio;
import models.EnderecoPagamento;
import models.Municipio;
import models.PlanoAplicacao;
import play.*;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.ConvenioService;
import services.MunicipioService;

import views.html.*;

public class Application extends Controller {
  
	public static Result video() {
		return redirect("https://youtu.be/VXREIaLwt3Q");
	}
	
	public static Result source() {
		return redirect("https://github.com/opendatabr/olhonoorcamento");
	}
	
	@Transactional
	public static Result index() {
		String [] cidades = {"São Paulo, SP", "Rio de Janeiro, RJ", "Salvador, BA", "Brasília, DF", "Fortaleza, CE", 
			"Belo Horizonte, MG", "Manaus, AM", "Curitiba, PR", "Recife, PE", "Porto Alegre, RS", "Belém, PA", 
			"Goiânia, GO", "São Luís, MA", "Maceió, AL", "Natal, RN", "Campo Grande, MS", "Teresina, PI", 
			"João Pessoa, PB", "Aracaju, SE", "Cuiabá, MT", "Porto Velho, RO", "lorianópolis, SC", 
			"Macapá, AM", "Rio Branco, AC", "Vitória, ES", "Boa Vista, RR", "Palmas, TO"};
		
		return ok(views.html.index.render());
    }
    
	@Transactional
    public static Result showMapaCidade(String estado, String cidade) {

        List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecos(estado, cidade);
        
        return ok(views.html.mostracidade.render(cidade, estado, enderecos));
    }
    
	@Transactional
    public static Result showDetalhes(String cep) {
		List<Convenio> convenios = ConvenioService.getConveniosExecutadosEm(cep);
		EnderecoPagamento endereco = ConvenioService.getEndereco(cep);
		
		return ok(views.html.detalheEndereco.render(endereco, convenios));
	}
	
    @Transactional
    public static Result showConvenio(String id) {
        return TODO;
    }
    
    @Transactional
    public static Result buscaCidade() {
    	DynamicForm dynamicForm = form().bindFromRequest();
		String busca = dynamicForm.get("busca");
    	
    	List<Municipio> municipios = MunicipioService.buscaMunicipios(busca);
    	return ok(views.html.buscabasicares.render("Resultado da busca por " + busca, municipios));
    }
    
    @Transactional
    public static Result buscaConvenio(String id) {
        return TODO;
    }
    
    @Transactional
    public static Result buscaEstado(String estado) {
    	List<Municipio> municipios = MunicipioService.getMunicipios(estado);
    	return ok(views.html.buscabasicares.render("Cidades do estado: " + estado, municipios));
    }
  
}
