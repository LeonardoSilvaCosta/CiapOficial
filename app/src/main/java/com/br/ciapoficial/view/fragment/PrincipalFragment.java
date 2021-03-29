package com.br.ciapoficial.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.Constants;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.view.PesquisarActivity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.br.ciapoficial.view.LoginActivity.fileName;
import static com.br.ciapoficial.view.LoginActivity.userSex;

public class PrincipalFragment extends Fragment {

    private UserUpdateFragment1 userUpdateFragment1;
    private AtendidoRegisterFragment1 atendidoRegisterFragment1;
    private AtendimentoRegisterFragment1 atendimentoRegisterFragment1;
    private SharedPreferences sharedPreferences;
    private CircleImageView fotoPerfil;
    private ImageView imageAtualizarPerfil;
    private Button btnAtualizarPerfil, btnNovoAtendido, btnNovoAtendimento, btnPesquisar;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);

        fotoPerfil = view.findViewById(R.id.imgUsuario);
        imageAtualizarPerfil = view.findViewById(R.id.imgAtualizarPerfil);
        btnAtualizarPerfil = view.findViewById(R.id.btnAtualizarPerfil);
        btnNovoAtendido = view.findViewById(R.id.btnNovoAtendido);
        btnNovoAtendimento = view.findViewById(R.id.btnNovoAtendimento);
        btnPesquisar = view.findViewById(R.id.btnPesquisar);


        configurarImagemAtualizarPerfil();
        recuperarImagem();
        abrirTelaAtualizarCadastro();
        abrirTelaNovoAtendido();
        abrirTelaNovoAtendimento();
        abrirTelaPesquisar();
        return view ;

    }

    private void configurarImagemAtualizarPerfil() {
        String sexoUsuarioLogado = sharedPreferences.getString("userSex", "");
        if(sexoUsuarioLogado.equals("1"))
        {
            imageAtualizarPerfil.setImageResource(R.drawable.icon_man_police);
        }
        else
        {
            imageAtualizarPerfil.setImageResource(R.drawable.icon_woman_police);
        }
    }

    private void recuperarImagem() {

        UsuarioController usuarioController = new UsuarioController();
        UserModel userModel = new UserModel();
        //
        usuarioController.recuperarImagem(getActivity(), new VolleyCallback() {
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

                                    String url = Constants.URLUsuariosCasa + "/Images/" + imagemUrl;

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

    private void abrirTelaAtualizarCadastro() {
        btnAtualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdateFragment1 = new UserUpdateFragment1();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, userUpdateFragment1);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    private void abrirTelaNovoAtendido() {
        btnNovoAtendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atendidoRegisterFragment1 = new AtendidoRegisterFragment1();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameConteudo, atendidoRegisterFragment1);
                transaction.addToBackStack(null).commit();
            }
        });

    }

    private void abrirTelaNovoAtendimento() {
        btnNovoAtendimento.setOnClickListener(new View.OnClickListener() {
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