package com.br.ciapoficial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.Atendimento;

import java.util.List;

public class AtendimentosAdapter extends RecyclerView.Adapter<AtendimentosAdapter.MyViewHolder> {

    private List<Atendimento> atendimentos;
    private Context context;

    public AtendimentosAdapter(List<Atendimento> lista, Context c) {
        this.atendimentos = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_atendimentos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Atendimento atendimento = atendimentos.get(position);

        String resumoOficiais = atendimento.getOficiaisResponsaveis().get(0).
                replace("[", "").replace("]", "");
        String resumoAtendidos = atendimento.getAtendidos().
                get(0).replace("[", "").
                replace("]", "");

        holder.data.setText(atendimento.getData());
        if(atendimento.getOficiaisResponsaveis().size() > 1)
        {
            holder.nomeOficiais.setText(resumoOficiais + "...+(" + (atendimento.getOficiaisResponsaveis().size() - 1) + ")");
        }else
        {
            holder.nomeOficiais.setText(atendimento.getOficiaisResponsaveis().toString().
                    replace("[", "").replace("]", ""));
        }

        if(atendimento.getAtendidos().size() > 1)
        {
            holder.nomeAtendidos.setText(resumoAtendidos + "...+(" + (atendimento.getAtendidos().size() - 1) + ")");
        }else
        {
            holder.nomeAtendidos.setText(atendimento.getAtendidos().toString().
                    replace("[", "").replace("]", ""));
        }

    }

    @Override
    public int getItemCount() {
        return atendimentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView data, nomeAtendidos, nomeOficiais;

        public MyViewHolder(View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.txtData);
            nomeAtendidos = itemView.findViewById(R.id.txtNomeAtendido);
            nomeOficiais = itemView.findViewById(R.id.txtNomeOficial);
        }
    }
}
