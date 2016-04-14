package projeto.combatecorrupcao.others;

/**
 * Created by Angelica on 11/04/2016.
 */
public class ResumoConvenio {
   private  String objetoConvenio;
    private  String situacao;
    private int Ano;
    private int Valor;
    private String Estado_munic;

    public String getObjetoConvenio() {
        return objetoConvenio;
    }

    public ResumoConvenio(String objetoConvenio, String situacao, int valor, int ano, String estado_munic) {
        this.objetoConvenio = objetoConvenio;
        this.situacao = situacao;
        Valor = valor;
        Ano = ano;
        Estado_munic = estado_munic;
    }

    public void setObjetoConvenio(String objetoConvenio) {
        this.objetoConvenio = objetoConvenio;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int ano) {
        Ano = ano;
    }

    public String getEstado_munic() {
        return Estado_munic;
    }

    public void setEstado_munic(String estado_munic) {
        Estado_munic = estado_munic;
    }

    public int getValor() {
        return Valor;
    }

    public void setValor(int valor) {
        Valor = valor;
    }
}
