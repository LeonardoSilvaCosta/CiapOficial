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

public class ServicosAtendidoAdapter extends RecyclerView.Adapter<ServicosAtendidoAdapter.MyViewHolder> {

    private List<Servico> servicos;
    private Context context;

    public ServicosAtendidoAdapter(List<Servico> lista, Context c) {
        this.servicos = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_atendimentos_atendido, parent, false);
        return new MyViewHolder(itemLista);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Servico servico = servicos.get(position);

        holder.data.setText(DateFormater.localDateToString(servico.getData()));

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

        TextView data, nomeOficiais;

        public MyViewHolder(View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.txtData);
            nomeOficiais = itemView.findViewById(R.id.txtNomeOficial);
        }
    }
}
