package com.br.ciapoficial.model.dto;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.Usuario;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class UsuarioTitularDto implements Serializable {
    private Integer id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private PostoGradCat postoGradCat;
    private Quadro quadro;
    private String rgMilitar;
    private String nomeGuerra;

    public UsuarioTitularDto(Usuario usuario) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.postoGradCat = postoGradCat;
        this.quadro = quadro;
        this.rgMilitar = rgMilitar;
        this.nomeGuerra = nomeGuerra;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<UsuarioTitularDto> converter(List<Usuario> usuarios){

        return usuarios.stream().map(UsuarioTitularDto::new).collect(Collectors.toList());
    }

}
