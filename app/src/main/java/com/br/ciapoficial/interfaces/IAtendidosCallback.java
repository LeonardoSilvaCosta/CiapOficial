package com.br.ciapoficial.interfaces;

import com.br.ciapoficial.model.Usuario;

import java.util.List;

public interface IAtendidosCallback {
    void onCallback(List<Usuario> usuarios);
}
