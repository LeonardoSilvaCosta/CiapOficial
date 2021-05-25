package com.br.ciapoficial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.Usuario;

import java.util.List;

public class AtendidosAdapter extends RecyclerView.Adapter<AtendidosAdapter.MyViewHolder> {

    private List<Usuario> usuarios;
    private Context context;

    public AtendidosAdapter(List<Usuario> lista, Context c) {
        this.usuarios = lista;
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

        Usuario usuario = usuarios.get(position);

        holder.nome.setText(usuario.getNomeCompleto());
        holder.cpf.setText(usuario.getCpf());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
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
