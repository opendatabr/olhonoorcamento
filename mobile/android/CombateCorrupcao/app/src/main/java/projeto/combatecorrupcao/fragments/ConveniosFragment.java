package projeto.combatecorrupcao.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import projeto.combatecorrupcao.R;
import projeto.combatecorrupcao.activitys.DetalheConvenioActivity;
import projeto.combatecorrupcao.adapters.ConveniosAdapters;
import projeto.combatecorrupcao.design.RecyclerViewOnClickListenerHack;
import projeto.combatecorrupcao.others.ResumoConvenio;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConveniosFragment extends Fragment implements RecyclerViewOnClickListenerHack{

    RecyclerView mReciRecyclerView;
    List<ResumoConvenio> lista;

    public ConveniosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_convenios, container, false);
        mReciRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        mReciRecyclerView.setHasFixedSize(true);
        mReciRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
/*
                LinearLayoutManager llm = (LinearLayoutManager) mReciRecyclerView.getLayoutManager();
                ConveniosAdapters conv = (ConveniosAdapters)mReciRecyclerView.getAdapter();

                if (lista.size()==llm.findLastCompletelyVisibleItemPosition()+1){
                    List<ResumoConvenio> listaaux=new ArrayList<ResumoConvenio>();
                    for(int i=0;i<listaaux.size();i++){
                        conv.addList(listaaux.get(i),lista.size());
                    }

                }*/
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mReciRecyclerView.setLayoutManager(llm);

        lista= new ArrayList<>();
        ResumoConvenio conv1 = new ResumoConvenio("Realização do Dança Pará, Festival em sua 17º versão na cidade de Belém...","Prestação de Contas Aprovada",0,2008,"PA/BELEM");
        ResumoConvenio conv2 =new ResumoConvenio(" II FESTIVAL DE CIRANDA DA CIDADE DE RECIFE","Prestação de Contas em análise",0,2008,"PE/GARANHUNS");
        ResumoConvenio conv3 = new ResumoConvenio(" Aviva Pará","",0,2008,"DF/BRASILIA");
        lista.add(conv1);
        lista.add(conv2);
        lista.add(conv3);
        ConveniosAdapters conv = new ConveniosAdapters(lista,getActivity());
        mReciRecyclerView.setAdapter(conv);
        conv.setRecyclerViewOnClickListenerHack(this);
        return v;
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), DetalheConvenioActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
        //Toast.makeText(getActivity(), "position "+position, Toast.LENGTH_SHORT).show();
    }

}
