package controllers;

import java.util.Date;
import java.util.List;

import models.Municipio;
import models.PlanoAplicacao;
import models.util.EnderecoPagamento;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.ConvenioService;
import services.MunicipioService;

import views.html.*;

public class Application extends Controller {
  
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
    public static Result showConvenio(String id) {
        return TODO;
    }
    
    @Transactional
    public static Result buscaCidade(String id) {
        return TODO;
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
