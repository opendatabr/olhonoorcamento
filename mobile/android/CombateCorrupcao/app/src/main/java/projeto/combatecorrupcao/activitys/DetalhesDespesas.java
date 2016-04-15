package projeto.combatecorrupcao.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import projeto.combatecorrupcao.R;

/**
 * Created by Angelica on 11/04/2016.
 */
public class DetalhesDespesas extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_despesas_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalhes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        Intent intent = getIntent();
        String title =  intent.getStringExtra("title");
        double lat = intent.getDoubleExtra("lat", 0.0);
        double log =intent.getDoubleExtra("long",0.0);
        Toast.makeText(this,title+lat+log,Toast.LENGTH_SHORT).show();

    }
}
