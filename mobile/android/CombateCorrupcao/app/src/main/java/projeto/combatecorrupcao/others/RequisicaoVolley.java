package projeto.combatecorrupcao.others;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Angelica on 12/04/2016.
 */
public class RequisicaoVolley{
   static String resposta;
    public static String metodo_POST(final Context context, final Map<String,String> parametros,String url){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       resposta=response;
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       resposta = error.toString();
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = parametros;
                // Map<String,String> params = new HashMap<String, String>();
              //  params.put(KEY_USERNAME,username);
               // params.put(KEY_PASSWORD,password);
               // params.put(KEY_EMAIL, email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    return resposta;
    }
}
