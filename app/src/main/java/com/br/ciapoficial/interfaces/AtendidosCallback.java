package com.br.ciapoficial.interfaces;

import com.br.ciapoficial.model.Usuario;

import java.util.List;

public interface AtendidosCallback {
    void onCallback(List<Usuario> usuarios);
}
