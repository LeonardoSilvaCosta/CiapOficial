package com.br.ciapoficial.view.fragments;

import static com.br.ciapoficial.Constants.BASE_API_URL;
import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.Constants;
import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.Calendar;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.network.FuncionarioController;
import com.br.ciapoficial.view.PesquisarActivity;
import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.SneakyThrows;

public class PrincipalFragment extends Fragment {

    private String filepath = BASE_API_URL + "/relatorio/pdf/ciap/03";
    private URL url = null;
    private String fileName;
    private Long downloadId;

    private static final String TAG = "PrincipalFragment";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context,
                        permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private FuncionarioRegisterFragment1 funcionarioRegisterFragment1;
    private FuncionarioUpdateFragment1 funcionarioUpdateFragment1;
    private UsuarioRegisterFragment1 usuarioRegisterFragment1;
    private AtendimentoRegisterFragment1 atendimentoRegisterFragment1;
    private SharedPreferences sharedPreferences;
    private CircleImageView fotoPerfil;
    private ImageView imageAtualizarPerfil;
    private Button btnNovoFuncionario, btnAtualizaFuncionario, btnNovoUsuario,
            btnRegistrarServico, btnPesquisar, btnRelatorio;

    private String token;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        PRDownloader.initialize(getActivity());

        fotoPerfil = view.findViewById(R.id.imgUsuario);
        imageAtualizarPerfil = view.findViewById(R.id.imgAtualizarFuncionario);
        btnNovoFuncionario = view.findViewById(R.id.btnNovoFuncionario);
        btnAtualizaFuncionario = view.findViewById(R.id.btnAtualizarFuncionario);
        btnNovoUsuario = view.findViewById(R.id.btnNovoUsuario);
        btnRegistrarServico = view.findViewById(R.id.btnRegistrarServico);
        btnPesquisar = view.findViewById(R.id.btnPesquisar);
        btnRelatorio = view.findViewById(R.id.btnRelatorio);

        configurarImagemAtualizarPerfil();
        recuperarImagem();
        abrirTelaParaNovoFuncionario();
        abrirTelaAtualizarCadastro();
        abrirTelaNovoAtendido();
        abrirTelaNovoAtendimento();
        abrirTelaPesquisar();

        PRDownloader.initialize(getActivity());

        //getActivity().registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 112);

        btnRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baixarRelatorio();
            }
        });


        return view;

    }

    private void configurarImagemAtualizarPerfil() {
        String sexoUsuarioLogado = sharedPreferences.getString("userSex", "");
        if (sexoUsuarioLogado.equals("Masculino")) {
            imageAtualizarPerfil.setImageResource(R.drawable.icon_man_police);
        } else {
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

                if (isAdded()) {
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

//    private void abrirRelatorio() {
//        Log.v(TAG, "view() Method invoked");
//
//        if (!hasPermissions(getActivity(), PERMISSIONS)) {
//
//            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");
//
//            Toast t = Toast.makeText(getActivity(), "You don't have read access !", Toast.LENGTH_LONG);
//            t.show();
//        } else {
//            File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            File pdfFile = new File(d, "ciapReport.pdf");
//
//            Log.v(TAG, "view() Method pdfFile " + pdfFile.getAbsolutePath());
//
//            Uri path = FileProvider.getUriForFile(
//                    getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", pdfFile);
//
//            Log.v(TAG, "view() Method path " + path);
//
//            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//            pdfIntent.setDataAndType(path, "application/pdf");
//            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            try {
//                startActivity(pdfIntent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(getActivity(), "No application avaliable to view PDF", Toast.LENGTH_SHORT).show();
//            }
//        }
//        Log.v(TAG, "view() Method completed ");
//    }

    private void baixarRelatorio() {
        btnRelatorio.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                definirPeriodo();
            }
        });
    }

    private void definirPeriodo() throws ParseException {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View mView = inflater.inflate(R.layout.dialog_fragment_select_period, null);
        final AutoCompleteTextView autoCompleteTextViewDataInicial = mView.findViewById(R.id.autoCompleteTextViewDataInicial);
        final AutoCompleteTextView autoCompleteTextViewDataFinal = mView.findViewById(R.id.autoCompleteTextViewDataFinal);
        Calendar.pegarCalendarioDataAtual(getContext(), autoCompleteTextViewDataInicial);
        Calendar.pegarCalendarioDataAtual(getContext(), autoCompleteTextViewDataFinal);

        builder.setTitle("Insira um período dentro de um ano.");
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Baixar", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @SneakyThrows
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if (!hasPermissions(getActivity(), PERMISSIONS)) {

                            Toast t = Toast.makeText(getActivity(), "You don't have write access !", Toast.LENGTH_LONG);
                            t.show();
                        } else {

                            Date initialDate = new SimpleDateFormat("dd/MM/yyyy").parse(
                                    autoCompleteTextViewDataInicial.getText().toString().trim());
                            Date finalDate = new SimpleDateFormat("dd/MM/yyyy").parse(
                                    autoCompleteTextViewDataFinal.getText().toString().trim());

                            if(initialDate.after(finalDate)) {
                                autoCompleteTextViewDataFinal.setText(null);
                                Toast.makeText(getActivity(),
                                        "A data final não pode ser inferior a data inicial",
                                        Toast.LENGTH_SHORT).show();
                            }else {

                                String data_inicial = new SimpleDateFormat("yyyy-MM-dd").format(initialDate);
                                String data_final = new SimpleDateFormat("yyyy-MM-dd").format(finalDate);

                                ProgressDialog pd = new ProgressDialog(requireActivity());
                                pd.setMessage("Downloading...");
                                pd.setCancelable(false);
                                pd.show();

                                token = sharedPreferences.getString("token", "");

                                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                                PRDownloader.download(filepath+"?data_inicial="+data_inicial+"&data_final="+data_final,
                                        file.getPath(), "ciapReport.pdf")
                                        .setHeader("Authorization", token )
                                        .build()
                                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                            @Override
                                            public void onStartOrResume() {

                                            }
                                        })
                                        .setOnPauseListener(new OnPauseListener() {
                                            @Override
                                            public void onPause() {

                                            }
                                        })
                                        .setOnCancelListener(new OnCancelListener() {
                                            @Override
                                            public void onCancel() {

                                            }
                                        })
                                        .setOnProgressListener(new OnProgressListener() {
                                            @Override
                                            public void onProgress(Progress progress) {

                                                long per = progress.totalBytes*100 / progress.currentBytes;

                                                pd.setMessage("Downloading : " +per+" %");

                                            }
                                        })
                                        .start(new OnDownloadListener() {
                                            @Override
                                            public void onDownloadComplete() {
                                                pd.dismiss();
                                                Toast.makeText(getContext(), "Download completed", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onError(Error error) {
                                                pd.dismiss();
                                                Toast.makeText(getContext(), "Error " + error.getServerErrorMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                            PrincipalFragment.this.getDialog().cancel();
                    }
                });
        builder.show();
    }
}