package com.br.ciapoficial.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.model.Servico;

import java.util.ArrayList;
import java.util.List;

public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.MyViewHolder> {

    private List<Servico> servicos;
    private Context context;

    public ServicosAdapter(List<Servico> lista, Context c) {
        this.servicos = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_atendimentos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Servico servico = servicos.get(position);

        holder.data.setText(DateFormater.localDateToString(servico.getData()));

        if(!servico.getUsuarios().isEmpty() && servico.getUsuarios() != null) {

            String resumoAtendidos = new ArrayList<>(servico.getUsuarios()).
                    get(0).toString().replace("[", "").
                    replace("]", "");

            if(servico.getUsuarios().size() > 1)
            {
                holder.nomeAtendidos.setText(resumoAtendidos + "...+(" + (servico.getUsuarios().size() - 1) + ")");
            }else
            {
                holder.nomeAtendidos.setText(servico.getUsuarios().toString().
                        replace("[", "").replace("]", ""));
            }

        }

        if(!servico.getEspecialistas().isEmpty() && servico.getEspecialistas() != null) {
            String resumoOficiais = new ArrayList<>(servico.getEspecialistas()).get(0).toString().
                    replace("[", "").replace("]", "");

            if(servico.getEspecialistas().size() > 1)
            {
                holder.nomeOficiais.setText(resumoOficiais + "...+(" + (servico.getEspecialistas().size() - 1) + ")");
            }else
            {
                holder.nomeOficiais.setText(servico.getEspecialistas().toString().
                        replace("[", "").replace("]", ""));
            }
        }

    }

    @Override
    public int getItemCount() {
        return servicos.size();
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
