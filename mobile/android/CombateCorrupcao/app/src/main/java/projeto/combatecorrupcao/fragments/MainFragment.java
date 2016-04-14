package projeto.combatecorrupcao.fragments;
import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import projeto.combatecorrupcao.R;
import projeto.combatecorrupcao.activitys.DetalhesDespesas;
import projeto.combatecorrupcao.activitys.MainActivity;
import projeto.combatecorrupcao.others.MySingletonNetwork;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    MapView mMapView;
    private GoogleMap googleMap;
    Context context;
    GoogleApiClient mGoogleApiClient;
    public final String TAG ="posicao";
    public final String TAGNET ="requisicao";
    StringRequest stringRequest;
    private ArrayList<Double> lat = new ArrayList<>();
    private ArrayList<Double> log = new ArrayList<>();
    private ArrayList<String> nome = new ArrayList<>();
    private LatLng positions;


    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_main, container,
                false);
        context = container.getContext();

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;
        // Get the intent, verify the action and get the query
        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String nomeLocal = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getActivity(),nomeLocal, Toast.LENGTH_SHORT).show();
            try {
                getLatLongGeocode( nomeLocal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            buscaPosicaoAtual();
        }

     //   buscaNomeCidade();
    //    buscaPorCidade();//bruno

     //new LatLng(17.385044, 78.486671)
        // adding marker
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

              Intent i =new Intent(context, DetalhesDespesas.class);
                i.putExtra("title", marker.getTitle());
                i.putExtra("lat", marker.getPosition().latitude);
                i.putExtra("long", marker.getPosition().longitude);
                startActivity(i);
               // Log.i("marker", "4: Marker: " + marker.getTitle() + " e " + marker.getId() + " e " + marker.getSnippet() + "e" + marker.getPosition().latitude);
            }
        });
      setHasOptionsMenu(true);
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflate) {
        inflate.inflate(R.menu.searchable, menu);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_searchItem);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Local");

        //String text = searchView.getQuery().toString();
      //  Toast.makeText(getActivity(), "oncrreate "+text, Toast.LENGTH_SHORT).show();

        super.onCreateOptionsMenu(menu, inflate);
        // Configure the search info and add any event listeners...

    }
    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String searQur = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getActivity(),"handle: "+searQur, Toast.LENGTH_SHORT).show();
        }
    }
    public void getLatLongGeocode(String nomeLocal) throws IOException {
        Geocoder gc = new Geocoder(getActivity());
        List<Address> results = gc.getFromLocationName(nomeLocal, 1);
        Address add = results.get(0);

        String Country =add.getCountryName();
        if(Country.equals("Brasil")){
          
            String city= add.getLocality();
            double lat = add.getLatitude();
            double log =add.getLongitude();
            LatLng ll = new LatLng(lat,log);

            posicionaCamera(ll);
        }else{
            Toast.makeText(getActivity(), "Orçamento apenas do Brasil.", Toast.LENGTH_SHORT).show();
        }
    }


    protected void onNewIntent(Intent intent){
        getActivity().setIntent(intent);
        handleIntent(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_searchItem:
                //Toast.makeText(getActivity(), "teste", Toast.LENGTH_SHORT).show();
                return true;
            default:return false;
        }
    }

    public void buscaPosicaoAtual(){

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

    }
    public void posicionaCamera(LatLng posAtual){

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(posAtual).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
        String[] permissions = new String[2];
        permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[1] =Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().requestPermissions(permissions,2);
            }
            Log.d(TAG,"erro de permissão");
            Toast.makeText(getActivity(), "Permita-nos obter sua localização.", Toast.LENGTH_LONG).show();
        }
        Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(loc != null){
            Log.d(TAG,"Longitude: "+loc.getLongitude());
            Log.d(TAG,"Latitude: "+loc.getLatitude());
          String Local = "Longitude: "+loc.getLongitude() + " Latitude: "+loc.getLatitude();
            LatLng pos = new LatLng(loc.getLatitude(),loc.getLongitude());
            posicionaCamera(pos);
            googleMap.addMarker(new MarkerOptions().position(pos).title("teste").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            getLocalEndereco(-8.356447,-46.362305);
           // sendEmail(Local);
        }else{
            Toast.makeText(getActivity(), "Não foi possível obter localização, verifique se GPS ligado e tente novamente.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended " + i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
        Toast.makeText(getActivity(), "verifique a atualização do Google play. Erro: "+connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }

    public void getLocalEndereco(double lat,double log) {

        Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());
        try {
            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(lat, log, 1);
            if(addresses != null) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }
               // myAddress.setText("I am at: " +strAddress.toString());
                Log.d("geoc",addresses.get(0).getCountryName()+" "+ addresses.get(0).getLocality()+" " +addresses.get(0).getSubAdminArea()+" size: "+addresses.size());
                String municipio = addresses.get(0).getLocality();
               // getConvenios(municipio);
            }else{
              //  myAddress.setText("No location found..!");
                Log.d("geoc","No location found..!");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Erro! não conseguimos obter endereço.", Toast.LENGTH_LONG).show();
        }
    }
    public void getConvenios(String municipio){

        String urlpem ="http://ipem.pe.gov.br/noticias/";
        stringRequest = new StringRequest(Request.Method.GET, urlpem,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String PontosJson= new String( response);
                JSONArray pontosArray = null;
                JSONObject ponto= null;
                //Toast.makeText(getActivity(), "resposta: " + user, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject reader = new JSONObject(PontosJson);
                    pontosArray = reader.getJSONArray("pontos");
                    for (int i=0; i < pontosArray.length(); i++){
                        ponto = pontosArray.getJSONObject(i);
                        lat.add(Double.parseDouble( ponto.getString("lat").replace(",",".") ) );
                        log.add(Double.parseDouble(ponto.getString("log").replace(",", ".")) );
                        String[] first = ponto.getString("nome").split("|");
                        nome.add( first[0] );
                    }

                    for(int k=0; k < nome.size();k++){
                        positions = new LatLng(lat.get(k), log.get(k));
                        googleMap.addMarker(new MarkerOptions().position(positions).title(nome.get(k)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                        //call posicaoCamera

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.print(e.getMessage());
                }
                //  Toast.makeText(getActivity(),"sucess: "+ response, Toast.LENGTH_LONG).show();
              //  addMarkers();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // prgDialog.hide();
                Toast.makeText(getActivity(),"Erro: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setTag(TAGNET);
        MySingletonNetwork.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

/* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://projeto.combatecorrupcao/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://projeto.combatecorrupcao/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}
