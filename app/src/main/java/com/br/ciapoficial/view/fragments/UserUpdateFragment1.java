package com.br.ciapoficial.view.fragments;

import androidx.fragment.app.Fragment;

public class UserUpdateFragment1 extends Fragment {

//    private String[] permissoesNecessarias = new String[] {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA
//    };
//
//    private static final int SELECAO_CAMERA = 100;
//    private static final int SELECAO_GALERIA = 200;
//
//    private LinearLayout linearLayoutTelefone;
//    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
//            textInputEditTextCpf, textInputEditTextTelefone, textInputEditTextEmail, textInputEditTextSenha;
//    private RadioGroup radioGroupSexo;
//    private RadioButton rbtnMasculino, rbtnFeminino;
//
//    private UserUpdateFragment2 userUpdateFragment2;
//    private Button btnAdicionarTelefone, btnProxima;
//    private CircleImageView fotoPerfil;
//    private ImageButton imageButtonCamera, imageButtonGaleria;
//    private String encodedImage;
//
//    private String nomeCompleto;
//    private String dataNascimento;
//    private String cpf;
//    private String sexo;
//    private String email;
//    private String senha;
//
//
//    private Telefone telefone;
//    private ArrayList<Telefone> arrayListTelefonesRecuperados = new ArrayList<>();
//    private ArrayList<TextView> textViesTelefonesRecuperados = new ArrayList<>();
//    private ArrayList<Telefone> arrayListTelefonesParaDeletar = new ArrayList<>();
//    private ArrayList<String> listaDeTelefonesAdicionados = new ArrayList<>();
//    private ArrayList<Telefone> listaDeTelefonesParaDeletar = new ArrayList<>();
//
//    SharedPreferences sharedPreferences;
//
//    public UserUpdateFragment1() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_user_update1, container, false);
//
//        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
//        Permissao.validarPermissoes(permissoesNecessarias, getActivity(), 1);
//
//        configurarComponentes(view);
//        configurarMascaraData();
//        configurarMascaraCpf();
//        configurarMascaraTelefone();
//        receberDadosUsuarioPreviamentePreenchidos();
//        definirComportamentoRadioButtons();
//        receberTelefoneUsuarioLogado();
//        abrirCamera();
//        abrirGaleria();
//        abrirProximaTela();
//        recuperarImagem();
//
//        return view ;
//    }
//
//    private void configurarComponentes(View view)
//    {
//        linearLayoutTelefone = view.findViewById(R.id.linearLayoutTelefone);
//        fotoPerfil = view.findViewById(R.id.imgUsuario);
//        textInputEditTextNomeCompleto = view.findViewById(R.id.edtNomeCompleto);
//        textInputEditTextDataNascimento = view.findViewById(R.id.edtDataNascimento);
//        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
//        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
//        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
//        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
//        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
//        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
//        textInputEditTextSenha = view.findViewById(R.id.edtSenha);
//        imageButtonCamera = view.findViewById(R.id.imageButtonCamera);
//        imageButtonGaleria = view.findViewById(R.id.imageButtonGaleria);
//        btnAdicionarTelefone = view.findViewById(R.id.btnAdicionarTelefone);
//        btnProxima = view.findViewById(R.id.btnProxima);
//
//        bloquearEditText();
//    }
//
//    private void bloquearEditText() {
//        textInputEditTextCpf.setFocusable(false);
//        textInputEditTextCpf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
//            }
//        });
//        textInputEditTextEmail.setFocusable(false);
//        textInputEditTextEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void configurarMascaraData()
//    {
//        Mascaras mascara = new Mascaras();
//        mascara.mascaraData(textInputEditTextDataNascimento);
//    }
//
//    private void configurarMascaraCpf()
//    {
//        Mascaras mascara = new Mascaras();
//        mascara.criarMascaraCpf(textInputEditTextCpf);
//    }
//
//    private void configurarMascaraTelefone()
//    {
//        Mascaras mascara = new Mascaras();
//        mascara.criarMascaraTelefone(textInputEditTextTelefone);
//    }
//
//    private void configurarCampoTelefone(ArrayList<TextView> textViews, ArrayList<Telefone> listaDeTelefonesAdd,
//                                         LinearLayout linearLayoutTelefone)
//    {
//        int idUsuarioLogado = Integer.parseInt(sharedPreferences.getString("userId", ""));
//
//        btnAdicionarTelefone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AddRemoveTextView.adicionarTextViewTelefoneUsuarioObjeto(getActivity(), idUsuarioLogado, textInputEditTextTelefone,
//                        listaDeTelefonesAdd, linearLayoutTelefone);
//
//            }
//
//        });
//
//        AddRemoveTextView.removerItemDaListaDeTelefonesEListarParaDelecao(textViews, listaDeTelefonesAdd,
//                arrayListTelefonesParaDeletar);
//
//
//    }
//
//    private void definirComportamentoRadioButtons() {
//
//        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.rbtnMasculino) {
//                    sexo = "1";
//                } else if (checkedId == R.id.rbtnFeminino) {
//                    sexo = "2";
//                }
//            }
//        });
//    }
//
//    private void receberDadosUsuarioPreviamentePreenchidos() {
//
//        String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");
//
//        FuncionarioController funcionarioController = new FuncionarioController();
//        funcionarioController.recuperarUsuarioLogado(getActivity(), emailUsuarioLogado, new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
//                    Log.e("Response", response);
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String email = object.getString("email");
//
//                            if(email.equals(emailUsuarioLogado)) {
//
//                                Funcionario funcionario = new Funcionario();
//                                funcionario.setNomeCompleto(object.getString("nomeCompleto"));
//                                funcionario.setDataNascimento(DataEntreJavaEMysql.
//                                        receberDataDoMySqlComoString(object.getString(
//                                        "dataNascimento")));
//                                funcionario.setCpf(object.getString("cpf"));
//                                funcionario.setSexo(object.getString("sexo"));
//
//
//                                funcionario.setEmail(object.getString("email"));
//
//                                textInputEditTextNomeCompleto.setText(funcionario.getNomeCompleto());
//                                textInputEditTextDataNascimento.setText(funcionario.getDataNascimento());
//                                textInputEditTextCpf.setText(funcionario.getCpf());
//                                textInputEditTextEmail.setText(funcionario.getEmail());
//
//                                if(funcionario.getSexo().equals("Masculino")) {
//
//                                    rbtnMasculino.setChecked(true);
//                                }else {
//                                    rbtnFeminino.setChecked(true);
//                                }
//
//                                break;
//                            }
//
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void receberTelefoneUsuarioLogado()
//    {
//        String idUsuarioLogado = sharedPreferences.getString("userId", "");
//        FuncionarioController funcionarioController = new FuncionarioController();
//        funcionarioController.recuperarTelefoneUsuarioLogado(getActivity(), idUsuarioLogado, new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
//                    Log.e("Resposta", response);
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1"))
//                    {
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                                telefone = new Telefone();
//                                telefone.setId(Integer.parseInt(object.getString("id")));
//                                telefone.setTelefone(object.getString("telefone"));
//
//                                arrayListTelefonesRecuperados.add(telefone);
//                            }
//
//                            for(int i = 0; i < arrayListTelefonesRecuperados.size(); i++) {
//                                String telefoneRecuperado = arrayListTelefonesRecuperados.get(i).getTelefone();
//                                TextView textView = new TextView(getActivity());
//
//                                textView.setPadding(0, 10, 0, 10);
//                                textView.setText(telefoneRecuperado);
//                                textView.setTag("lista");
//
//                                linearLayoutTelefone.addView(textView);
//
//                                textViesTelefonesRecuperados.add(textView);
//                            }
//
//                            configurarCampoTelefone(textViesTelefonesRecuperados, arrayListTelefonesRecuperados, linearLayoutTelefone);
//
//                        }
//                    } catch (JSONException jsonException) {
//                    jsonException.printStackTrace();
//                }
//            }
//
//        });
//
//    }
//
//    private void receberDadosUsuarioPreenchidos()
//    {
//        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
//        dataNascimento = textInputEditTextDataNascimento.getText().toString();
//        cpf = textInputEditTextCpf.getText().toString();
//
//        for(Telefone contato : arrayListTelefonesRecuperados) {
//            listaDeTelefonesAdicionados.add(contato.getTelefone());
//        }
//
//        listaDeTelefonesParaDeletar = arrayListTelefonesParaDeletar;
//
//        email = textInputEditTextEmail.getText().toString();
//        senha = textInputEditTextSenha.getText().toString();
//
//    }
//
//    private boolean validarCadastroUsuario() {
//        receberDadosUsuarioPreenchidos();
//        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());
//
//        if (!TextUtils.isEmpty(nomeCompleto)) {
//
//            if (!TextUtils.isEmpty(dataNascimento)) {
//
//                if (dataNascimento.replaceAll("[/]", "").length() == 8) {
//
//                    if (!TextUtils.isEmpty(cpf)) {
//
//                        if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {
//
//                            if (!TextUtils.isEmpty(sexo)) {
//
//                                if (!listaDeTelefonesAdicionados.isEmpty()) {
//
//                                    if (!TextUtils.isEmpty(email)) {
//
//                                        if(senha.isEmpty() || senha.length() >= 6) {
//
//                                            if (validarCPF.equals(true)) {
//
//                                                encapsularValoresParaEnvio();
//                                                return true;
//                                            }
//                                            else {
//                                                textInputEditTextCpf.setError("CPF invalido!");
//                                                textInputEditTextCpf.requestFocus();
//                                                return false; }
//                                        }
//                                        else {
//                                            textInputEditTextSenha.setError("o campo SENHA deve conter, no mínimo, 6 caracteres.");
//                                            textInputEditTextSenha.requestFocus();
//                                            return false;}
//
//                                    }
//                                    else {
//                                        textInputEditTextEmail.setError("o campo EMAIL deve ser preenchido.");
//                                        textInputEditTextEmail.requestFocus();
//                                        return false; }
//
//                                }
//                                else {
//                                    textInputEditTextTelefone.setError("É necessário adicionar ao menos um telefone.");
//                                    textInputEditTextTelefone.requestFocus();
//                                    return false; }
//
//                            }
//                            else {
//                                Toast.makeText(getActivity(),
//                                        "Selecione uma opção de SEXO",
//                                        Toast.LENGTH_SHORT).show();
//                                rbtnMasculino.requestFocus();
//                                return false; }
//
//                        }
//                        else {
//                            Toast.makeText(getActivity(),
//                                    "Selecione uma opção de SEXO",
//                                    Toast.LENGTH_SHORT).show();
//                            rbtnMasculino.requestFocus();
//                            return false; }
//
//                    }
//                    else {
//                        textInputEditTextCpf.setError("O campo CPF é obrigatório!");
//                        textInputEditTextCpf.requestFocus();
//                        return false; }
//
//                }
//                else {
//                    textInputEditTextDataNascimento.setError("Digite uma data válida.");
//                    textInputEditTextDataNascimento.requestFocus();
//                    return false; }
//
//            }
//            else {
//                textInputEditTextDataNascimento.setError("O campo DATA DE NASCIMENTO é obrigatório!");
//                textInputEditTextDataNascimento.requestFocus();
//                return false; }
//
//        }
//        else {
//            textInputEditTextNomeCompleto.setError("O campo NOME COMPLETO é obrigatório!");
//            textInputEditTextNomeCompleto.requestFocus();
//            return false; }
//    }
//
//    public Bundle encapsularValoresParaEnvio()
//    {
//        Bundle bundle = new Bundle();
//
//        bundle.putString("nomeCompleto", nomeCompleto);
//        bundle.putString("dataNascimento", dataNascimento);
//        bundle.putString("cpf", cpf);
//        bundle.putString("sexo", sexo);
//        bundle.putStringArrayList("telefones", listaDeTelefonesAdicionados);
//        bundle.putSerializable("telefonesParaDeletar", listaDeTelefonesParaDeletar);
//        bundle.putString("email", email);
//        bundle.putString("senha", senha);
//
//        return bundle;
//    }
//
//    private void abrirProximaTela() {
//
//        btnProxima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(validarCadastroUsuario())
//                {
//                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();
//
//                    userUpdateFragment2 = new UserUpdateFragment2();
//                    userUpdateFragment2.setArguments(valoresEncapsuladosParaEnvio);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameConteudo, userUpdateFragment2);
//                    fragmentTransaction.addToBackStack(null).commit();
//                }
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        for(int permissaoResultado : grantResults) {
//
//            if(permissaoResultado == getActivity().getPackageManager().PERMISSION_DENIED) {
//                alertaValidacaoPermissao();
//            }
//        }
//    }
//
//    private void alertaValidacaoPermissao() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Permissões negadas.");
//        builder.setMessage("Para utilziar o app, é necessário aceitar as permissões.");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                getActivity().finish();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private void abrirCamera() {
//        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
//
//                    startActivityForResult(i, SELECAO_CAMERA);
//                }
//
//            }
//        });
//
//    }
//
//    private void abrirGaleria() {
//
//        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
//
//                    startActivityForResult(i, SELECAO_GALERIA);
//                }
//            }
//        });
//    }
//
//    private void imageStore(Bitmap bitmap) {
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//
//        byte[] imageBytes = stream.toByteArray();
//
//        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
//    }
//
//    public void uploadImage() {
//        FuncionarioController funcionarioController = new FuncionarioController();
//        funcionarioController.carregarImagemUsuario(getActivity(), encodedImage, new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean isErro = jsonObject.getBoolean("erro");
//
//                    if(isErro) {
//                        Toast.makeText(getActivity(),
//                                "Falha ao realizar apload",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        Toast.makeText(getActivity(),
//                                "Upload realizado com sucesso",
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } catch (JSONException jsonException) {
//                    jsonException.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private void recuperarImagem() {
//
//        FuncionarioController funcionarioController = new FuncionarioController();
//        Funcionario funcionario = new Funcionario();
//        //
//        funcionarioController.recuperarImagem(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String sucesso = jsonObject.getString("success");
//
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(sucesso.equals("1")) {
//
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String email = object.getString("email");
//
//                            if(email.equals(emailUsuarioLogado)) {
//
//                                String imagemUrl = object.getString("imagem");
//
//                                String url = Constants.URLUsuarios + "/Images/"+imagemUrl;
//
//
//                                Glide.with(getActivity()).
//                                        load(url).
//                                        centerCrop().
//                                        placeholder(R.drawable.ic_launcher_foreground)
//                                        .into(fotoPerfil);
//
//                                break;
//                            }
//
//
//                        }
//
//                    }
//
//                } catch (JSONException jsonException) {
//                    jsonException.printStackTrace();
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if(resultCode == RESULT_OK) {
//
//            Bitmap imagem = null;
//
//            try {
//                switch (requestCode) {
//                    case SELECAO_CAMERA:
//
//                        imagem = (Bitmap) data.getExtras().get("data");
//                        break;
//                    case SELECAO_GALERIA:
//                        Uri localImagemSelecionada = data.getData();
//                        imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), localImagemSelecionada);
//                        break;
//                }
//
//                if(imagem != null) {
//
//                    fotoPerfil.setImageBitmap(imagem);
//
//                    imageStore(imagem);
//                    uploadImage();
//                }
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onStop() {
//        arrayListTelefonesRecuperados.clear();
//        super.onStop();
//    }
}