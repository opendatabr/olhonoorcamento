package projeto.combatecorrupcao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import projeto.combatecorrupcao.R;

public class DetalheConvenioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_convenio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalhes do convênio");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        Intent i =getIntent();
        int pos=i.getIntExtra("position", Integer.MIN_VALUE);
        Toast.makeText(DetalheConvenioActivity.this, "posit detalhes "+ pos, Toast.LENGTH_SHORT).show();
        TextView nome = (TextView)findViewById(R.id.nome);
        TextView cidade = (TextView)findViewById(R.id.cidade);
        TextView tipo = (TextView)findViewById(R.id.tipo);
        TextView anoConvenio = (TextView)findViewById(R.id.anoconvenio);
        TextView anoProposta = (TextView)findViewById(R.id.anoproposta);
        TextView situacao = (TextView)findViewById(R.id.situacao);

    }

}
