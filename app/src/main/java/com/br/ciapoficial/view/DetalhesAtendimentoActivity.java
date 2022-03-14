package com.br.ciapoficial.view;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.FormatarTexto;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.PutPdf;
import com.br.ciapoficial.model.Servico;
import com.br.ciapoficial.model.in_servico.Atendimento;
import com.br.ciapoficial.model.in_servico.Avaliacao;
import com.br.ciapoficial.model.in_servico.ServicoDeAssistenciaEspecial;
import com.br.ciapoficial.network.AtendimentoController;
import com.br.ciapoficial.network.AvaliacaoController;
import com.br.ciapoficial.network.ServicoDeAssistenciaEspecialController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import lombok.SneakyThrows;

public class DetalhesAtendimentoActivity extends AppCompatActivity {

    private TextView txtData, txtOficialResponsavel, txtAtendido, txtUnidade, txtModalidade,
            txtAcesso, txtTipo, txtPrograma, txtDeslocamento, txtDemandaGeral,
            txtDemandaEspecifica, txtProcedimento, txtDocumentoProduzido,
            txtAfastamento, txtEvolucao, txtDataHoraCadastro, txtResponsavelCadastro,
            txtAvaliacao, txtCondicaoLaboral;
    private TextView legendaAvaliacao,
            legendaCondicaoLaboral;
    private Servico servicoSelecionado;
    private Button btnCriarPdf, btnUploadPdf, btnUp, btnCancelUpload,
            btnSignedPdf, btnNotSignedPdf;
    private ImageView cancelDownloadImage;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private EditText edtUploadPdf;

    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atendimento);

        legendaAvaliacao = findViewById(R.id.txtLegendaAvaliacao);
        legendaCondicaoLaboral = findViewById(R.id.txtLegendaCondicaoLaboral);
        txtTipo = findViewById(R.id.txtTipo);
        txtData = findViewById(R.id.txtData);
        txtOficialResponsavel = findViewById(R.id.txtOficialResponsavel);
        txtAtendido = findViewById(R.id.txtAtendido);
        txtUnidade = findViewById(R.id.txtUnidade);
        txtModalidade = findViewById(R.id.txtModalidade);
        txtAcesso = findViewById(R.id.txtAcesso);
        txtPrograma = findViewById(R.id.txtPrograma);
        txtDeslocamento = findViewById(R.id.txtDeslocamento);
        txtDemandaGeral = findViewById(R.id.txtDemandaGeral);
        txtDemandaEspecifica = findViewById(R.id.txtDemandaEspecifica);
        txtProcedimento = findViewById(R.id.txtProcedimento);
        txtDocumentoProduzido = findViewById(R.id.txtDocumentoProduzido);
        txtAfastamento = findViewById(R.id.txtAfastamento);
        txtEvolucao = findViewById(R.id.txtEvolucao);
        txtDataHoraCadastro = findViewById(R.id.txtDataHoraCadastro);
        txtResponsavelCadastro = findViewById(R.id.txtResponsavelCadastro);
        txtAvaliacao = findViewById(R.id.txtAvaliacao);
        txtCondicaoLaboral = findViewById(R.id.txtCondicaoLaboral);
        btnCriarPdf = findViewById(R.id.btnCriarPdf);
        btnUploadPdf = findViewById(R.id.btnUploadPdf);

        storageReference = FirebaseStorage.getInstance().getReference("/prontuarios");
        databaseReference = FirebaseDatabase.getInstance().getReference("/prontuarios");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            servicoSelecionado = (Servico) bundle.getSerializable("servicoSelecionado");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtTipo.setText(servicoSelecionado.getTipo().toString());
                txtData.setText(DateFormater.localDateToString(servicoSelecionado.getData())); }
            txtOficialResponsavel.setText(servicoSelecionado.getEspecialistas().toString()
                    .replace("[", "").replace("]", ""));
            txtAtendido.setText(servicoSelecionado.getUsuarios().toString()
                    .replace("[", "").replace("]", ""));
            txtUnidade.setText(servicoSelecionado.getUnidade().toString());
            txtModalidade.setText(servicoSelecionado.getModalidade().toString());
            txtAcesso.setText(servicoSelecionado.getAcesso().toString());
            txtPrograma.setText(servicoSelecionado.getPrograma().toString());
            txtDeslocamento.setText(servicoSelecionado.getDeslocamentos().toString()
                    .replace("[", "")
                    .replace("]", ""));
            txtDemandaGeral.setText(servicoSelecionado.getDemandaGeral().toString());
            txtDemandaEspecifica.setText(servicoSelecionado.getDemandasEspecificas().toString()
                    .replace("[", "")
                    .replace("]", ""));
            txtProcedimento.setText(servicoSelecionado.getProcedimento().toString());
            txtDocumentoProduzido.setText(servicoSelecionado.getDocumentosProduzidos().toString()
                    .replace("[", "")
                    .replace("]", ""));
            if(servicoSelecionado.isAfastamento())
            {
                txtAfastamento.setText("Sim");
            }else
            {
                txtAfastamento.setText("Não");
            }
            txtEvolucao.setText(servicoSelecionado.getEvolucao());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtDataHoraCadastro.setText(DateFormater.localDateTimeToString(servicoSelecionado.getDataCadastro()));
            }
            txtResponsavelCadastro.setText(servicoSelecionado.getResponsavelCadastroServico().toString());
        }

        if(servicoSelecionado instanceof Atendimento) {
            legendaAvaliacao.setVisibility(View.GONE);
            txtAvaliacao.setVisibility(View.GONE);
            legendaCondicaoLaboral.setVisibility(View.GONE);
            txtCondicaoLaboral.setVisibility(View.GONE);
        }else if(servicoSelecionado instanceof Avaliacao) {
            txtAvaliacao.setText(((Avaliacao) servicoSelecionado).getTipoAvaliacao().toString());

            legendaCondicaoLaboral.setVisibility(View.GONE);
            txtCondicaoLaboral.setVisibility(View.GONE);
        }else if (servicoSelecionado instanceof ServicoDeAssistenciaEspecial){
            txtCondicaoLaboral.setText(((ServicoDeAssistenciaEspecial) servicoSelecionado).getCondicaoLaboral().toString());

            legendaAvaliacao.setVisibility(View.GONE);
            txtAvaliacao.setVisibility(View.GONE);
        }

        btnUploadPdf.setOnClickListener(v -> {
            createUploadPdfDialog();
        });

        btnCriarPdf.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createDownloadPdfDialog();
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                if(servicoSelecionado.isSigned()) {
                    btnSignedPdf.setEnabled(true);
                }
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String nomePdf() {
        return DateFormater.localDateToString(servicoSelecionado.getData()).replace("/", "-") + "-"
                + normalizar(retirarColchetes(
                substituirEspacos(servicoSelecionado.getTipo().getNome().toLowerCase()))) + "-"
                + normalizar(retirarColchetes(
                substituirEspacos(servicoSelecionado.getUsuarios().toString().toLowerCase())))
                + ".pdf";
    }

    private String retirarColchetes(String texto) {
        return new FormatarTexto().retirarColchetes(texto);

    }

    private String substituirEspacos(String texto) {
        return new FormatarTexto().substituirEspacos(texto);
    }

    private String normalizar(String texto) {
        return new FormatarTexto().normalizar(texto);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createDownloadPdfDialog() {
        Dialog dialog = new Dialog(DetalhesAtendimentoActivity.this);
        dialog.setContentView(R.layout.dialog_fragment_download_pdf);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        edtUploadPdf = dialog.findViewById(R.id.edtUploadPdf);
        btnSignedPdf = dialog.findViewById(R.id.btnSignedPdf);
        btnNotSignedPdf = dialog.findViewById(R.id.btnNotSignedPdf);
        cancelDownloadImage = dialog.findViewById(R.id.cancelImage);

        btnNotSignedPdf.setOnClickListener(v -> {
            try {
                createAndDonwloadNotSignedPdf();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        btnSignedPdf.setEnabled(false);

        btnSignedPdf.setOnClickListener(V -> {
            downloadSignedPdf();
        });

        cancelDownloadImage.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Donwload cancelado", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createAndDonwloadNotSignedPdf() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,
                nomePdf());

        PdfWriter writer = null;
        try {
            OutputStream outputStream = new FileOutputStream(file);
            writer = new PdfWriter(file);

            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            pdfDocument.setDefaultPageSize(PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            Table headerImageTables = new Table(2).setBorder(Border.NO_BORDER);
            headerImageTables.setWidth(document.getPdfDocument().getDefaultPageSize().getWidth());

            Drawable brasaoEstado = getDrawable(R.drawable.brasao_estado);
            Bitmap bitmapBE = ((BitmapDrawable) brasaoEstado).getBitmap();
            ByteArrayOutputStream osBE = new ByteArrayOutputStream();
            bitmapBE.compress(Bitmap.CompressFormat.PNG, 100, osBE);
            byte[] bitMapDataBE = osBE.toByteArray();

            ImageData imageDataBE = ImageDataFactory.create(bitMapDataBE);
            Image imageBE = new Image(imageDataBE);
            imageBE.
                    scale(12, 12)
                    .setMarginTop(5);

            Drawable brasaoPm = getDrawable(R.drawable.brasao_pm);
            Bitmap bitmapPM = ((BitmapDrawable) brasaoPm).getBitmap();
            ByteArrayOutputStream osPM = new ByteArrayOutputStream();
            bitmapPM.compress(Bitmap.CompressFormat.PNG, 100, osPM);
            byte[] bitMapDataPM = osPM.toByteArray();

            ImageData imageDataPM = ImageDataFactory.create(bitMapDataPM);
            Image imagePM = new Image(imageDataPM);
            imagePM.
                    scale(12, 12)
                    .setMarginTop(5)
                    .setMarginRight(30)
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);

            headerImageTables.addCell(new Cell().add(imageBE.setHeight(50).setWidth(50))
                    .setBorder(Border.NO_BORDER));
            headerImageTables.addCell(new Cell().add(imagePM.setHeight(50).setWidth(50))
                    .setBorder(Border.NO_BORDER));

            Paragraph cabecalho = new Paragraph(
                    "GOVERNO DO ESTADO DO PARÁ\n" +
                            "SECRETARIA DE ESTADO DE SEGURANÇA PÚBLICA E DEFESA SOCIAL\n" +
                            "POLÍCIA MILITAR DO PARÁ\n" +
                            "DEPARTAMENTO GERAL DE PESSOAL")
                    .setFontSize(12)
                    .setMarginTop(-70)
                    .setFixedLeading(20)
                    .setTextAlignment(TextAlignment.CENTER);
            Paragraph ciapTitle = new Paragraph(
                    "CENTRO INTEGRADO DE ATENÇÃO PSICOSSOCIAL")
                    .setBold()
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMultipliedLeading(0f);

            Paragraph tipoDeAtendimento = new Paragraph(
                    "Tipo de atendimento: " + servicoSelecionado.getTipo().getNome())
                    .setMarginTop(30f)
                    .setFontSize(14);
            Paragraph data = new Paragraph(
                    "Data: " +  DateFormater.localDateToString(servicoSelecionado.getData()))
                    .setFontSize(14);
            Paragraph especialistas = new Paragraph(
                    "Especialista: " + servicoSelecionado.getEspecialistas().toString()
                            .replace("[", "")
                            .replace("]", "")).setFontSize(14);
            Paragraph atendidos = new Paragraph(
                    "Atendido(s): " + servicoSelecionado.getUsuarios().toString()
                            .replace("[", "")
                            .replace("]", "")).setFontSize(14);
            Paragraph unidade = new Paragraph(
                    "Unidade: " + servicoSelecionado.getUnidade().getNome())
                    .setFontSize(14);
            Paragraph modalidade = new Paragraph(
                    "Modalidade: " + servicoSelecionado.getModalidade().getNome())
                    .setFontSize(14);
            Paragraph acesso = new Paragraph(
                    "Acesso: " + servicoSelecionado.getAcesso().getNome())
                    .setFontSize(14);
            Paragraph programa = new Paragraph(
                    "Programa: " + servicoSelecionado.getPrograma().getNome())
                    .setFontSize(14);
            Paragraph deslocamento = new Paragraph(
                    "Deslocamento: " + servicoSelecionado.getDeslocamentos().toString()
                            .replace("[", "")
                            .replace("]", "")).setFontSize(14);
            Paragraph demandaGeral = new Paragraph(
                    "Demanda geral: " + servicoSelecionado.getDemandaGeral().getNome())
                    .setFontSize(14);
            Paragraph procedimento = new Paragraph(
                    "Procedimento: " + servicoSelecionado.getProcedimento().getNome())
                    .setFontSize(14);
            Paragraph documentacao = new Paragraph(
                    "Documentação produzida: " + servicoSelecionado.getDocumentosProduzidos().toString()
                            .replace("[", "")
                            .replace("]", "")).setFontSize(14);
            String isAfastamento = "";
            if(servicoSelecionado.isAfastamento()) { isAfastamento = "Sim"; }
            else { isAfastamento = "Não"; }
            Paragraph afastamento = new Paragraph(
                    "Houve afastamento?: " + isAfastamento)
                    .setFontSize(14);
            Paragraph sinaisSintomas = null;

            Paragraph tituloEvolucao = new Paragraph("Evolução:")
                    .setBold()
                    .setFontSize(14);
            Paragraph evolucao = new Paragraph(servicoSelecionado.getEvolucao())
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFirstLineIndent(20)
                    .setFontSize(14);

            Paragraph assinatura = new Paragraph(servicoSelecionado.getEspecialistas().toString()
                    .replace("[", "")
                    .replace("]", ""))
                    .setHeight(evolucao.getHeight())
                    .setVerticalAlignment(VerticalAlignment.BOTTOM)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(100)
                    .setFontSize(14);

            document.add(headerImageTables);
            document.add(cabecalho);
            document.add(ciapTitle);

            document.add(tipoDeAtendimento);
            document.add(data);
            document.add(especialistas);
            document.add(atendidos);
            document.add(unidade);
            document.add(modalidade);
            document.add(acesso);
            document.add(programa);
            document.add(deslocamento);
            document.add(demandaGeral);
            document.add(procedimento);
            document.add(documentacao);
            document.add(afastamento);
            document.add(assinatura);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            document.add(headerImageTables);
            document.add(cabecalho);
            document.add(ciapTitle);
            document.add(tituloEvolucao);
            document.add(evolucao);
            document.add(assinatura);

            document.close();

            Toast.makeText(getApplicationContext(), "Download realizado com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void downloadSignedPdf() {
        StorageReference path = storageReference.child(
                substituirEspacos(normalizar(
                        retirarColchetes(
                                servicoSelecionado.getUsuarios().toString())))
                        +"/"+ nomePdf());

        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Context context = DetalhesAtendimentoActivity.this;
                DownloadManager downloadManager = (DownloadManager) context
                        .getSystemService(context.DOWNLOAD_SERVICE);
                Uri uriResource = Uri.parse(uri.toString());
                DownloadManager.Request request = new DownloadManager.Request(uriResource);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS, "atendimento--.pdf");

                downloadManager.enqueue(request);
                Toast.makeText(getApplicationContext(), "Download realizado com sucesso!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void createUploadPdfDialog() {
        Dialog dialog = new Dialog(DetalhesAtendimentoActivity.this);
        dialog.setContentView(R.layout.dialog_fragment_upload_pdf);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        edtUploadPdf = dialog.findViewById(R.id.edtUploadPdf);
        btnUp = dialog.findViewById(R.id.btnUp);
        btnCancelUpload = dialog.findViewById(R.id.btnCancelUpload);

        btnUp.setEnabled(false);

        edtUploadPdf.setOnClickListener(v -> {
            selectPdf();
        });

        btnCancelUpload.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Upload cancelado", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECTED"), 12);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            btnUp.setEnabled(true);
            edtUploadPdf.setText(data.getDataString().substring(
                    data.getDataString().lastIndexOf("/") + 1));

            btnUp.setOnClickListener(v -> {
                uploadPdf(data.getData());
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadPdf(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(DetalhesAtendimentoActivity.this);
        progressDialog.setTitle("O arquivo está carregando...");
        progressDialog.show();

        StorageReference reference = storageReference.
                child(substituirEspacos(
                        normalizar(
                                retirarColchetes(servicoSelecionado.getUsuarios().toString()))))
                .child(nomePdf());

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        PutPdf putPdf = new PutPdf(nomePdf().replace(".pdf", ""), uri.toString());
                        databaseReference.child(substituirEspacos(normalizar(retirarColchetes(servicoSelecionado.getUsuarios().toString()))))
                                .setValue(putPdf);

                        atualizarAtributoSignedDoServico();

                        Toast.makeText(getApplicationContext(), "Uploaod realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Envio do arquivo em " + (int) progress+"%");
            }
        });

    }

    public void atualizarAtributoSignedDoServico() {
        if(servicoSelecionado instanceof Atendimento) {
            new AtendimentoController().atualizar(
                    getApplicationContext(),
                    (Atendimento) servicoSelecionado,
                    new IVolleyCallback() {
                        @Override
                        public void onSucess(String response) throws JSONException {
                            try{

                                JSONObject jsonObject = new JSONObject(response);

                            }catch (JSONException e) {
                                Log.e("updateSigned", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
        }else if(servicoSelecionado instanceof Avaliacao) {
            new AvaliacaoController().atualizar(
                    getApplicationContext(),
                    (Avaliacao) servicoSelecionado,
                    new IVolleyCallback() {
                        @Override
                        public void onSucess(String response) throws JSONException {
                            try{

                                JSONObject jsonObject = new JSONObject(response);

                            }catch (JSONException e) {
                                Log.e("updateSigned", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
        }else {
            new ServicoDeAssistenciaEspecialController().atualizar(
                    getApplicationContext(),
                    (ServicoDeAssistenciaEspecial) servicoSelecionado,
                    new IVolleyCallback() {
                        @Override
                        public void onSucess(String response) throws JSONException {
                            try{

                                JSONObject jsonObject = new JSONObject(response);

                            }catch (JSONException e) {
                                Log.e("updateSigned", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

}