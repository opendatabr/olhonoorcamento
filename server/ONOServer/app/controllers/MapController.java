/* Copyright 2013 de Kellyton Brito. Este arquivo é parte 
* do programa MeuCongressoNacional.com . O MeuCongressoNacional.com 
* é um software livre; você pode redistribuí-lo e/ou modificá-lo 
* dentro dos termos da GNU Affero General Public License como 
* publicada pela Fundação do Software Livre (FSF) na versão 3 
* da Licença. Este programa é distribuído na esperança que possa 
* ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita 
* de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja 
* a licença para maiores detalhes, disponível em 
* meucongressonacional.com/license. Você deve ter recebido uma cópia 
* da GNU Affero General Public License, sob o título "LICENCA.txt", 
* junto com este programa, se não, acesse http://www.gnu.org/licenses/
**/

package controllers;

import java.util.List;

import models.EnderecoPagamento;
import models.Municipio;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.ConvenioService;
import services.MunicipioService;

public class MapController extends Controller {
	
	@Transactional
	public static Result showMapCidade(String estado, String cidade){
		 List<EnderecoPagamento> enderecos = ConvenioService.buscaEnderecos(estado, cidade);
		 Municipio m = MunicipioService.getMunicipio(estado, cidade);
		 
		 //enderecos = enderecos.subList(0, 10);
		 
		return ok(views.html.mapaenderecos.render(m, enderecos));
	}
	
	@Transactional
	public static Result showMapEndereco(String cep){
		EnderecoPagamento ep = ConvenioService.getEndereco(cep);
		return ok(views.html.mapaenderecoindividual.render(ep));
	}

}
