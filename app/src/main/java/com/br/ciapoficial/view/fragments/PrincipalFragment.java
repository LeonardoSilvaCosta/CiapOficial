package com.br.ciapoficial.view.fragments;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.Constants;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.FuncionarioController;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.view.PesquisarActivity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrincipalFragment extends Fragment {

    private FuncionarioRegisterFragment1 funcionarioRegisterFragment1;
    private FuncionarioUpdateFragment1 funcionarioUpdateFragment1;
    private UsuarioRegisterFragment1 usuarioRegisterFragment1;
    private AtendimentoRegisterFragment1 atendimentoRegisterFragment1;
    private SharedPreferences sharedPreferences;
    private CircleImageView fotoPerfil;
    private ImageView imageAtualizarPerfil;
    private Button btnNovoFuncionario, btnAtualizaFuncionario, btnNovoUsuario, btnRegistrarServico, btnPesquisar;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        fotoPerfil = view.findViewById(R.id.imgUsuario);
        imageAtualizarPerfil = view.findViewById(R.id.imgAtualizarFuncionario);
        btnNovoFuncionario = view.findViewById(R.id.btnNovoFuncionario);
        btnAtualizaFuncionario = view.findViewById(R.id.btnAtualizarFuncionario);
        btnNovoUsuario = view.findViewById(R.id.btnNovoUsuario);
        btnRegistrarServico = view.findViewById(R.id.btnRegistrarServico);
        btnPesquisar = view.findViewById(R.id.btnPesquisar);


        configurarImagemAtualizarPerfil();
        recuperarImagem();
        abrirTelaParaNovoFuncionario();
        abrirTelaAtualizarCadastro();
        abrirTelaNovoAtendido();
        abrirTelaNovoAtendimento();
        abrirTelaPesquisar();
        return view ;

    }

    private void configurarImagemAtualizarPerfil() {
        String sexoUsuarioLogado = sharedPreferences.getString("userSex", "");
        if(sexoUsuarioLogado.equals("Masculino"))
        {
            imageAtualizarPerfil.setImageResource(R.drawable.icon_man_police);
        }
        else
        {
            imageAtualizarPerfil.setImageResource(R.drawable.icon_woman_police);
        }
    }

    private void recuperarImagem() {

        FuncionarioController funcionarioController = new FuncionarioController();
        UserModel userModel = new UserModel();
        //
        funcionarioController.recuperarImagem(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                if(isAdded()) {
                    String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String sucesso = jsonObject.getString("success");

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        if (sucesso.equals("1")) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String email = object.getString("email");

                                if (email.equals(emailUsuarioLogado)) {

                                    String imagemUrl = object.getString("imagem");

                                    String url = Constants.URLUsuarios + "/Images/" + imagemUrl;

                                    Glide.with(getActivity()).
                                            load(url).
                                            centerCrop().
                                            placeholder(R.drawable.ic_launcher_foreground)
                                            .into(fotoPerfil);

                                    break;
                                }


                            }

                        }

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }

            }
        });
    }

    private void abrirTelaParaNovoFuncionario() {
        btnNovoFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                funcionarioRegisterFragment1 = new FuncionarioRegisterFragment1();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, funcionarioRegisterFragment1);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    private void abrirTelaAtualizarCadastro() {
        btnAtualizaFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionarioUpdateFragment1 = new FuncionarioUpdateFragment1();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, funcionarioUpdateFragment1);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    private void abrirTelaNovoAtendido() {
        btnNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioRegisterFragment1 = new UsuarioRegisterFragment1();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, usuarioRegisterFragment1);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    private void abrirTelaNovoAtendimento() {
        btnRegistrarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atendimentoRegisterFragment1 = new AtendimentoRegisterFragment1();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, atendimentoRegisterFragment1);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    private void abrirTelaPesquisar() {
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PesquisarActivity.class);
                startActivity(i);
            }
        });

    }

}