package extractors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import models.Convenio;
import models.PlanoAplicacao;
import models.util.DateUtil;
import play.Logger;
import play.db.jpa.JPA;

public class ConvenioExtractor {

	public static ExtractorResults processaConvenios() {
		ExtractorResults erTotal = new ExtractorResults("##### Processando Convenios #####");
		
		//ExtractorResults er1 = processaConveniosProgramas();
		//Logger.info(er1.toString());
		
		//ExtractorResults er2 = processaPlanoAplicacao();
		//Logger.info(er2.toString());
		
		return erTotal;
		
	}

	private static ExtractorResults processaConveniosProgramas() {
		ExtractorResults er = new ExtractorResults("## Processando Convenios/Programas ##");
		
		BufferedReader br;
		String line;
		String fields[];
		String csvSplitBy = ";";
		
		Convenio conv;
		
		String arquivo = "./dados/01_ConveniosProgramasv2.csv";
		String arquivoErro = "./dados/LinhasComProblemasAplicacao.csv";
		
		new File(arquivoErro).delete();

		try {
		
			Logger.info("Processing file: " + arquivo);
			
			//br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF8"));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "Cp1252"));
			
			//Jump first line
			line = br.readLine();				
			
			while ((line = br.readLine()) != null) {
				er.registros++;
				
				/*if (er.registros <= 80000){ //pra debug. pula os que já foram salvos
					continue;
				}*/
				
				conv = new Convenio();
				fields = line.split(csvSplitBy, -1);
				
				if (fields.length != 65){
					escreveNoArquivo(line);
					Logger.error("Linha com erro: " + line.substring(0, 100));
					continue;
				}
				
				//ANO_PROPOSTA;NR_PROPOSTA;TX_SITUACAOPROPOSTA;TX_MODALIDADE;ANO_CONVENIO;
				//NR_CONVENIO;CD_ORGAO_CONCEDENTE;NM_ORGAO_CONCEDENTE;CD_IDENT_PROPONENTE;NM_IDENT_PROPONENTE;
				//TP_IDENT_PROPONENTE;TX_ESFERA_ADM_PROPONENTE;NM_MUNICIPIO_PROPONENTE;UF_PROPONENTE;TX_REGIAO_PROPONENTE;
				//TP_DESPESA;TX_NATUREZAAQUISICAO;TX_DESCRICAODESPESA;CD_NATUREZADESPESA;NM_NATUREZADESPESA;
				//QD_NATUREZADESPESA;VL_UNITARIO;VL_TOTAL;TX_UNIDADEFORNECIMENTO;TX_ENDERECO;
				//TX_MUNICIPIO;CD_MUNICIPIO;UF_DESPESA;NR_CEP;TX_SITUACAODESPESA;
				//ID_CONVENIO;ID_PROPOSTA
				
				try {
				
					conv.setNumConvenio(parseInt(fields[1]));
					conv.setIdConvenio(parseInt(fields[62]));
					conv.setAnoConvenio(parseInt(fields[0]));
					conv.setNumProposta(parseInt(fields[3]));
					conv.setIdProposta(parseInt(fields[63]));
					conv.setAnoProposta(parseInt(fields[2]));
					conv.setModalidade(parseString(fields[4]));
					conv.setSituacao(parseString(fields[5]));
					conv.setCodOrgaoSuperior(parseInt(fields[7]));
					conv.setNomeOrgaoSuperior(parseString(fields[8]));
					conv.setCodOrgaoConcedente(parseInt(fields[9]));
					conv.setNomeOrgaoConcedente(parseString(fields[16]));
	
					conv.setDataInicioVigencia(parseDate(fields[21]));
					conv.setDataFimVigencia(parseDate(fields[22]));
					conv.setDataAssinatura(parseDate(fields[23]));
					conv.setValorGlobal(parseFloat(fields[31]));
					conv.setValorRepasse(parseFloat(fields[32]));
					conv.setValorContrapartida(parseFloat(fields[33]));
					conv.setValorEmpenhado(parseFloat(fields[37]));
					conv.setObjeto(parseString(fields[38]));
					conv.setJustificativa(parseString(fields[39]));
					conv.setQtdAditivos(parseInt(fields[59]));
					conv.setQtdProrrogacao(parseInt(fields[60]));
					conv.setQualificacaoProponente(parseString(fields[61]));
					
					conv.setNomeProponente(parseString(fields[43]));
					conv.setCodProponente(parseString(fields[44]));
					conv.setCargoProponente(parseString(fields[45]));
					conv.setEnderecoProponente(parseString(fields[40]));
					conv.setBairroProponente(parseString(fields[41]));
					conv.setCepProponente(parseString(fields[42]));
				
					JPA.em().persist(conv);		
					er.acertos++;
					JPA.em().getTransaction().commit();
					JPA.em().getTransaction().begin();
				} catch (Exception ex){
					Logger.error("Erro salvando convenio " + line.substring(0,100) + ": " + ex.getLocalizedMessage());
					er.erros++;
					//as linhas que dão pau eu salvo em um arquivo
					escreveNoArquivo(line);
					JPA.em().getTransaction().rollback();
					JPA.em().getTransaction().begin();
				}
				
				if (er.registros % 10000 == 0){
					//JPA.em().getTransaction().commit();
					//JPA.em().getTransaction().begin();
					Logger.info("Processados " + er.registros + " - " + new Date());
				}
			}
			
			Logger.info("Success processing " + arquivo);
			
			br.close();
			
		} catch (Exception e){
			Logger.error("Erro processando arquivo " + arquivo + ": " + e.getLocalizedMessage());
			er.erros++;
		}
		
		return er;
	}

	private static void escreveNoArquivo(String line) {
		String arquivo = "./dados/LinhasComProblemas.csv";
		
		BufferedWriter bw = null;

		try {
			// APPEND MODE SET HERE
			bw = new BufferedWriter(new FileWriter(arquivo, true));
			bw.write(line);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (IOException ioe2) {
				// just ignore it
			}
		} 
	}

	private static float parseFloat(String s) {
		if (s.startsWith("\"")){
			s = s.substring(1, s.length());
		}
		if (s.endsWith("\"")){
			s = s.substring(0, s.length() - 1);
		}
		s = s.replace("R$ ", "").replace("R$", "").replace(".","").replace(",", ".");
		if (s.isEmpty()){
			return 0;
		} else {
			return Float.valueOf(s);
		}
	}
	
	private static int parseInt(String s){
		if (s.startsWith("\"")){
			s = s.substring(1, s.length());
		}
		if (s.endsWith("\"")){
			s = s.substring(0, s.length() - 1);
		}
		if (s.isEmpty()){
			return 0;
		} else {
			return Integer.valueOf(s);
		}
	}

	private static String parseString(String s){
		if (s.startsWith("\"")){
			s = s.substring(1, s.length());
		}
		if (s.endsWith("\"")){
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}
	
	private static Date parseDate(String s){
		if (s.startsWith("\"")){
			s = s.substring(1, s.length());
		}
		if (s.endsWith("\"")){
			s = s.substring(0, s.length() - 1);
		}
		if (s.isEmpty()){
			return null;
		} else {
			return DateUtil.parseToDate(s);
		}
	}
	
	private static ExtractorResults processaPlanoAplicacao() {
		ExtractorResults er = new ExtractorResults("## Processando Plano de Aplicação##");
		
		BufferedReader br = null;
		String line;
		String fields[];
		String csvSplitBy = ";";
		
		PlanoAplicacao pa;
		
		String arquivo = "./dados/21_PlanoAplicacaoPT.csv";
		String arquivoErro = "./dados/LinhasComProblemasAplicacao.csv";
		new File(arquivoErro).delete();
		
		BufferedWriter bw = null;
		
		try {
		
			Logger.info("Processing file: " + arquivo);
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF8"));
			//br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "Cp1252"));
		
			bw = new BufferedWriter(new FileWriter(arquivoErro, true));
			
			//Jump first line
			line = br.readLine();				
			
			while ((line = br.readLine()) != null) {
				if (er.registros % 10000 == 0){
					JPA.em().getTransaction().commit();
					JPA.em().getTransaction().begin();
					System.gc();
					Logger.info("######### Processados " + er.registros + " - " + new Date());
				}
				er.registros++;
				/*if (er.registros <=1640000){  // para ajudar na carga, pular os já processados
					continue;
				}*/
				
				pa = new PlanoAplicacao();
				//fields = line.split(csvSplitBy, -1);
				fields = splitByKellyton(line);
				
				if (fields.length != 32){
					//Logger.error("##### Parseou a linha errado: " + line);
					
					bw.write(line);
					bw.newLine();
					bw.flush();
					
					er.erros++;
					continue;
				}
				
				//ANO_PROPOSTA;NR_PROPOSTA;TX_SITUACAOPROPOSTA;TX_MODALIDADE;ANO_CONVENIO;
				//NR_CONVENIO;CD_ORGAO_CONCEDENTE;NM_ORGAO_CONCEDENTE;CD_IDENT_PROPONENTE;NM_IDENT_PROPONENTE;
				//TP_IDENT_PROPONENTE;TX_ESFERA_ADM_PROPONENTE;NM_MUNICIPIO_PROPONENTE;UF_PROPONENTE;TX_REGIAO_PROPONENTE;
				//TP_DESPESA;TX_NATUREZAAQUISICAO;TX_DESCRICAODESPESA;CD_NATUREZADESPESA;NM_NATUREZADESPESA;
				//QD_NATUREZADESPESA;VL_UNITARIO;VL_TOTAL;TX_UNIDADEFORNECIMENTO;TX_ENDERECO;
				//TX_MUNICIPIO;CD_MUNICIPIO;UF_DESPESA;NR_CEP;TX_SITUACAODESPESA;
				//ID_CONVENIO;ID_PROPOSTA
				
				//2008;14;Proposta/Plano de Trabalho em Análise;Convênio;;
				//;44000;MINISTERIO DO MEIO AMBIENTE;87613196000178;MUNICIPIO DE SEBERI;
				//CNPJ;MUNICIPAL;SEBERI;RS;Sul;
				//OUTROS;Recursos do convênio;Aquisição de máquina carregadeira;46909290;INTEGR. DADOS ORGAOS E ENTID. PARCIAIS SIAFI;
				//1;R$ 370.800,00;R$ 370.800,00;Ministerio do Meio Ambiente;Avenida General Flores da Cunha;
				//SEBERI;8905;RS;98380-000;;
				//;1205
				
				try {
					pa.setNumConvenio(parseInt(fields[5]));
					pa.setIdConvenio(parseInt(fields[30]));
					pa.setAnoConvenio(parseInt(fields[4]));
					pa.setNumProposta(parseInt(fields[1]));
					pa.setIdProposta(parseInt(fields[31]));
					pa.setAnoProposta(parseInt(fields[0]));
					pa.setTipoDespesa(parseString(fields[15]));
					pa.setDescricaoDespesa(parseString(fields[17]));
					pa.setCodNaturezaDespesa(parseString(fields[18]));
					pa.setNomeNaturezaDespesa(parseString(fields[19]));
					pa.setQtdItems(parseFloat(fields[20]));
					pa.setValorUnitario(parseFloat(fields[21]));
					pa.setValorTotal(parseFloat(fields[22]));
					pa.setUnidadeFornecimento(parseString(fields[23]));
					pa.setEndereco(parseString(fields[24]));
					pa.setCidade(parseString(fields[25]));
					pa.setUf(parseString(fields[27]));
					pa.setCep(parseString(fields[28]));
					pa.setSituacao(parseString(fields[29]));
					
					JPA.em().persist(pa);
					er.acertos++;
					
				} catch (Exception ex){
					Logger.error("Erro salvando convenio " + line.substring(0,100) + ": " + ex.getLocalizedMessage());
					er.erros++;
					//as linhas que dão erro eu salvo em um arquivo
					bw.write(line);
					bw.newLine();
					bw.flush();
				}
				
			}
			
			Logger.info("Success processing " + arquivo);
			
			br.close();
			bw.close();
			
		} catch (Exception e){
			Logger.error("Erro processando arquivo " + arquivo + ": " + e.getLocalizedMessage());
			er.erros++;
			try {
				br.close();
				bw.close();
			} catch (Exception e2){};
		}
		
		return er;
		
	}

	private static String[] splitByKellyton(String line) {		
		//String line = "foo,bar,c;qual=\"baz,blurb\",d;junk=\"quux,syzygy\"";
		if (line.contains("\"")){
			//Logger.info("Tem aspas");
		}
        String[] tokens = line.split(";(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        return tokens;
	}

}
