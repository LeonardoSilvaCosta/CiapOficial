package com.br.ciapoficial.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.br.ciapoficial.Constants;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.Permissao;
import com.br.ciapoficial.helper.ValidarCPF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.br.ciapoficial.view.LoginActivity.fileName;

public class UserUpdateFragment1 extends Fragment {

    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;

    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextTelefone, textInputEditTextEmail, textInputEditTextSenha;
    private RadioGroup radioGroupSexo;
    private RadioButton rbtnMasculino, rbtnFeminino;

    private UserUpdateFragment2 userUpdateFragment2;
    private Button btnProxima;
    private CircleImageView fotoPerfil;
    private ImageButton imageButtonCamera, imageButtonGaleria;
    private String encodedImage;

    private String nomeCompleto;
    private String dataNascimento;
    private String cpf;
    private String sexo;
    private String email;
    private String senha;

    private ArrayList<String> arrayListTelefonesRecuperados = new ArrayList<>();

    SharedPreferences sharedPreferences;

    public UserUpdateFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_update1, container, false);

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Permissao.validarPermissoes(permissoesNecessarias, getActivity(), 1);

        configurarComponentes(view);
        configurarMascaraData();
        configurarMascaraCpf();
        definirComportamentoRadioButtons();
        abrirCamera();
        abrirGaleria();
        abrirProximaTela();
        recuperarImagem();

        return view ;
    }

    private void configurarComponentes(View view)
    {
        fotoPerfil = view.findViewById(R.id.imgUsuario);
        textInputEditTextNomeCompleto = view.findViewById(R.id.edtNomeCompleto);
        textInputEditTextDataNascimento = view.findViewById(R.id.edtDataNascimento);
        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
        textInputEditTextSenha = view.findViewById(R.id.edtSenha);
        imageButtonCamera = view.findViewById(R.id.imageButtonCamera);
        imageButtonGaleria = view.findViewById(R.id.imageButtonGaleria);
        btnProxima = view.findViewById(R.id.btnProxima);

        bloquearEditText();

        receberDadosUsuarioPreviamentePreenchidos();
        receberTelefoneUsuarioLogado();
    }

    private void bloquearEditText() {
        textInputEditTextCpf.setFocusable(false);
        textInputEditTextCpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
            }
        });
        textInputEditTextEmail.setFocusable(false);
        textInputEditTextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
            }
        });
        textInputEditTextTelefone.setFocusable(false);
        textInputEditTextTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarMascaraData()
    {
        Mascaras mascara = new Mascaras();
        mascara.mascaraData(textInputEditTextDataNascimento);
    }

    private void configurarMascaraCpf()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraCpf(textInputEditTextCpf);
    }

    private void definirComportamentoRadioButtons() {

        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnMasculino) {
                    sexo = "Masculino";
                } else if (checkedId == R.id.rbtnFeminino) {
                    sexo = "Feminino";
                }
            }
        });
    }

    private void receberDadosUsuarioPreviamentePreenchidos() {

        String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");

        UsuarioController usuarioController = new UsuarioController();
        usuarioController.recuperarUsuarioLogado(getActivity(), emailUsuarioLogado, new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    Log.e("Response", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String email = object.getString("email");

                            if(email.equals(emailUsuarioLogado)) {

                                Usuario usuario = new Usuario();
                                usuario.setNomeCompleto(object.getString("nomeCompleto"));
                                usuario.setDataNascimento(DataEntreJavaEMysql.
                                        receberDataDoMySqlComoString(object.getString(
                                        "dataNascimento")));
                                usuario.setCpf(object.getString("cpf"));
                                usuario.setSexo(object.getString("sexo"));


                                usuario.setEmail(object.getString("email"));

                                textInputEditTextNomeCompleto.setText(usuario.getNomeCompleto());
                                textInputEditTextDataNascimento.setText(usuario.getDataNascimento());
                                textInputEditTextCpf.setText(usuario.getCpf());
                                textInputEditTextEmail.setText(usuario.getEmail());

                                if(usuario.getSexo().equals("Masculino")) {

                                    rbtnMasculino.setChecked(true);
                                }else {
                                    rbtnFeminino.setChecked(true);
                                }

                                break;
                            }


                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void receberTelefoneUsuarioLogado()
    {
        String idUsuarioLogado = sharedPreferences.getString("userId", "");
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.recuperarTelefoneUsuarioLogado(getActivity(), idUsuarioLogado, new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    Log.e("Resposta", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    ArrayList<String> telefones = new ArrayList<>();

                    if(success.equals("1"))
                    {
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                                telefones.add(object.getString("telefone"));

                            }
                            Usuario usuario = new Usuario();
                            usuario.setTelefones(telefones);

                            String telefonesRecuperados = usuario.getTelefones().toString().
                                    replace("[", "").
                                    replace("]", "").
                                    replace(",", "/").
                                    replaceAll(" ", "");

                            textInputEditTextTelefone.setText(telefonesRecuperados);
                        }
                    } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                for(int t = 0; t < arrayListTelefonesRecuperados.size(); t++) {

                }
            }

        });

    }

    private void receberDadosUsuarioPreenchidos()
    {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
        dataNascimento = textInputEditTextDataNascimento.getText().toString();
        cpf = textInputEditTextCpf.getText().toString();
        email = textInputEditTextEmail.getText().toString();
        senha = textInputEditTextSenha.getText().toString();

    }

    private boolean validarCadastroUsuario() {
        receberDadosUsuarioPreenchidos();
        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());

        if (!TextUtils.isEmpty(nomeCompleto)) {

            if (!TextUtils.isEmpty(dataNascimento)) {

                if (dataNascimento.replaceAll("[/]", "").length() == 8) {

                    if (!TextUtils.isEmpty(cpf)) {

                        if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {

                            if (!TextUtils.isEmpty(sexo)) {

                                if (!TextUtils.isEmpty(email)) {

                                    if(senha.isEmpty() || senha.length() >= 6) {

                                        if (validarCPF.equals(true)) {

                                            encapsularValoresParaEnvio();
                                            return true;
                                        }
                                        else {
                                            textInputEditTextCpf.setError("CPF invalido!");
                                            textInputEditTextCpf.requestFocus();
                                            return false; }
                                    }
                                    else {
                                        textInputEditTextSenha.setError("o campo SENHA deve conter, no mínimo, 6 caracteres.");
                                        textInputEditTextSenha.requestFocus();
                                        return false;}

                                }
                                else {
                                    textInputEditTextEmail.setError("o campo EMAIL deve ser preenchido.");
                                    textInputEditTextEmail.requestFocus();
                                    return false; }

                            }
                            else {
                                Toast.makeText(getActivity(),
                                        "Selecione uma opção de SEXO",
                                        Toast.LENGTH_SHORT).show();
                                rbtnMasculino.requestFocus();
                                return false; }

                        }
                        else {
                            Toast.makeText(getActivity(),
                                    "Selecione uma opção de SEXO",
                                    Toast.LENGTH_SHORT).show();
                            rbtnMasculino.requestFocus();
                            return false; }

                    }
                    else {
                        textInputEditTextCpf.setError("O campo CPF é obrigatório!");
                        textInputEditTextCpf.requestFocus();
                        return false; }

                }
                else {
                    textInputEditTextDataNascimento.setError("Digite uma data válida.");
                    textInputEditTextDataNascimento.requestFocus();
                    return false; }

            }
            else {
                textInputEditTextDataNascimento.setError("O campo DATA DE NASCIMENTO é obrigatório!");
                textInputEditTextDataNascimento.requestFocus();
                return false; }

        }
        else {
            textInputEditTextNomeCompleto.setError("O campo NOME COMPLETO é obrigatório!");
            textInputEditTextNomeCompleto.requestFocus();
            return false; }
    }

    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("dataNascimento", dataNascimento);
        bundle.putString("cpf", cpf);
        bundle.putString("sexo", sexo);
        bundle.putString("email", email);
        bundle.putString("senha", senha);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    userUpdateFragment2 = new UserUpdateFragment2();
                    userUpdateFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, userUpdateFragment2);
                    fragmentTransaction.addToBackStack(null).commit();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults) {

            if(permissaoResultado == getActivity().getPackageManager().PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permissões negadas.");
        builder.setMessage("Para utilziar o app, é necessário aceitar as permissões.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirCamera() {
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivityForResult(i, SELECAO_CAMERA);
                }

            }
        });

    }

    private void abrirGaleria() {

        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void uploadImage() {
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.carregarImagemUsuario(getActivity(), encodedImage, new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean isErro = jsonObject.getBoolean("erro");

                    if(isErro) {
                        Toast.makeText(getActivity(),
                                "Falha ao realizar apload",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getActivity(),
                                "Upload realizado com sucesso",
                                Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
    }

    private void recuperarImagem() {

        UsuarioController usuarioController = new UsuarioController();
        Usuario usuario = new Usuario();
        //
        usuarioController.recuperarImagem(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucesso = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(sucesso.equals("1")) {

                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String email = object.getString("email");

                            if(email.equals(emailUsuarioLogado)) {

                                String imagemUrl = object.getString("imagem");

                                String url = Constants.URLUsuariosCasa + "/Images/"+imagemUrl;


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
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK) {

            Bitmap imagem = null;

            try {
                switch (requestCode) {
                    case SELECAO_CAMERA:

                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), localImagemSelecionada);
                        break;
                }

                if(imagem != null) {

                    fotoPerfil.setImageBitmap(imagem);

                    imageStore(imagem);
                    uploadImage();
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}