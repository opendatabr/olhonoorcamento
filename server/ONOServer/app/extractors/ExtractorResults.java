package extractors;

import java.util.Date;

import play.Logger;

public class ExtractorResults {

	public long registros;
	public long acertos;
	public long erros;
	public long pulados;
	String mensagem;
	public Date inicio;
	public Date fim;
	
	public ExtractorResults(){
		registros = 0;
		acertos = 0;
		erros = 0;
		pulados = 0;
		this.inicio = new Date();
		Logger.info("Iniciado em " + inicio);
	}
	
	public ExtractorResults(String mensagem) {
		registros = 0;
		acertos = 0;
		erros = 0;
		pulados = 0;
		this.mensagem = mensagem;
		this.inicio = new Date();
		
		Logger.info("################# Processando: " + mensagem + ". Iniciado em " + inicio);
	}

	public String getTempo(){
		if (fim == null){
			fim = new Date();
		}
		return (fim.getTime() - inicio.getTime())/1000 + "s";
	}
	
	public String toString(){
		String newline = System.getProperty("line.separator");
		return "################# Processamento realizado: " + mensagem + " - " + new Date() + newline +
				"Registros = " + registros + newline + 
				"Acertos = " + acertos + newline +
				"Pulados = " + pulados + newline +
				"Erros = " + erros + newline + 
				"Tempo: " + getTempo() + newline;
		
	}
	
}
