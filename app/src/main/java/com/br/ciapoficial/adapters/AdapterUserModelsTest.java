package com.br.ciapoficial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.UserModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterUserModelsTest extends ArrayAdapter<UserModel> {

    Context context;
    List<UserModel> arrayListUser;

    public AdapterUserModelsTest(@NonNull Context context, List<UserModel> users) {
        super(context, R.layout.triple_text_view, users);
        this.context = context;
        this.arrayListUser = users;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.triple_text_view, null, true);


        UserModel usuario = getItem(position);

        TextView txtId = (TextView) view.findViewById(R.id.id_text_view);
        TextView txtNome = (TextView) view.findViewById(R.id.name_text_view);
        TextView txtEmail = (TextView) view.findViewById(R.id.email_text_view);

        txtId.setText(String.valueOf(usuario.getId()));
        txtNome.setText(usuario.getName());
        txtEmail.setText(usuario.getEmail());

        return view;
    }
}
