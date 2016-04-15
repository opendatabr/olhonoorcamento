package projeto.combatecorrupcao.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import projeto.combatecorrupcao.R;
import projeto.combatecorrupcao.design.RecyclerViewOnClickListenerHack;
import projeto.combatecorrupcao.others.ResumoConvenio;
import projeto.combatecorrupcao.others.Utils;

/**
 * Created by Angelica on 11/04/2016.
 */
public class ConveniosAdapters extends RecyclerView.Adapter<ConveniosAdapters.MyViewHolder> {
    private List<ResumoConvenio> lista;
    LayoutInflater mLayoutInflater;
    public RecyclerViewOnClickListenerHack RVOCH;

    public ConveniosAdapters(List<ResumoConvenio> lista,Context c){
        this.lista = lista;
        this.mLayoutInflater = (LayoutInflater)c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_convenio, parent, false);

        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }
        public void addList(ResumoConvenio c,int pos){
            lista.add(c);
            notifyItemInserted(pos);
        }
    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        RVOCH=r;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.objetoConvenio.setText( Utils.limitaTitulo(lista.get(position).getObjetoConvenio()) );
        String situa = lista.get(position).getSituacao();
        if(situa.equals("")){
            holder.situacao.setText("Situação não informada.");
        }else{
            holder.situacao.setText(lista.get(position).getSituacao());
        }

        holder.Ano.setText(lista.get(position).getAno()+"");
        int valor=lista.get(position).getValor();
        if(valor==0){
            holder.Valor.setText("Valor não informado.");
        }else{
            holder.Valor.setText(lista.get(position).getValor()+"");
        }

        holder.Estado_munic.setText(lista.get(position).getEstado_munic());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView objetoConvenio;
        public TextView situacao;
        public TextView Ano;
        public TextView Valor;
        public TextView Estado_munic;

        public MyViewHolder(View itemView) {
            super(itemView);

                objetoConvenio=(TextView)itemView.findViewById(R.id.objConvenio);
                situacao =(TextView)itemView.findViewById(R.id.Situacao);
                Ano=(TextView)itemView.findViewById(R.id.ano);
                Valor=(TextView)itemView.findViewById(R.id.valor);
                Estado_munic=(TextView)itemView.findViewById(R.id.estado_municipio);
                itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (RVOCH!=null){
                RVOCH.onClickListener(v,getPosition());
            }
        }
    }

}
