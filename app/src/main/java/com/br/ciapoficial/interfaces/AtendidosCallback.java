package com.br.ciapoficial.interfaces;

import com.br.ciapoficial.model.Atendido;

import java.util.List;

public interface AtendidosCallback {
    void onCallback(List<Atendido> atendidos);
}
