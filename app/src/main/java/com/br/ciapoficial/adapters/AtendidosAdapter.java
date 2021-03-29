package com.br.ciapoficial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.Atendido;

import java.util.List;

public class AtendidosAdapter extends RecyclerView.Adapter<AtendidosAdapter.MyViewHolder> {

    private List<Atendido> atendidos;
    private Context context;

    public AtendidosAdapter(List<Atendido> lista, Context c) {
        this.atendidos = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_atendidos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Atendido atendido = atendidos.get(position);

        holder.nome.setText(atendido.getNomeCompleto());
        holder.cpf.setText(atendido.getCpf());
    }

    @Override
    public int getItemCount() {
        return atendidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView cpf;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            cpf = itemView.findViewById(R.id.txtCpf);
        }
    }
}
