package projeto.combatecorrupcao.others;

/**
 * Created by Angelica on 14/04/2016.
 */
public class Utils {
    public static String limitaTitulo(String titulo){
        int tamTitulo = titulo.length();
        if( tamTitulo > 38){
            String aux = titulo.substring(0,36 );
            titulo = aux.concat("..");
            return titulo;
        }

        return titulo;

    }
}
