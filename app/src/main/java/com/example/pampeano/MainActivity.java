package com.example.pampeano;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextInputLayout n1, n2, n3, n4, n5, n6, n7;
    TextView nomeJ;
    AutoCompleteTextView AcategoriaT, AcategoriaI, Amodo, AnomeG;
    Button gerar;
    DBHelper DB;
    private static final int CREATEPDF = 1;

    String[] categoriaT = new String[]{"Pareja", "Cuarteto", "Grupo", "Malambo"};
    String[] categoriaI = new String[]{"Pré infantil", "Infantil", "Juvenil", "Adulto", "Veterano", "Adulto Mayor"};
    String[] modo = new String[]{"Tradicional", "Proyección", "Estilizado", "Solista", "Conjunto"};
    String[] nomeG = new String[]{"Un nuevo rumbo", "Entre Vientos", "PTG Tiradentes", "Arte t Expresión", "Luz de Luna",
            "Memoria y Esperanza", "Guazuvirá", "Andanza", "Rincão da Fronteira", "El Estribo", "Flor de Amancay", "El Reencuentro",
            "Raza Gaucha", "Rumbo Norte", "Independiente" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.black));

        ArrayAdapter<String> adaptadorcategoriaT = new ArrayAdapter<>(this, R.layout.spinner, categoriaT);
        ArrayAdapter<String> adaptadorcategoriaI = new ArrayAdapter<>(this, R.layout.spinner, categoriaI);
        ArrayAdapter<String> adaptadormodo = new ArrayAdapter<>(this, R.layout.spinner, modo);
        ArrayAdapter<String> adaptadornomeG = new ArrayAdapter<>(this, R.layout.spinner, nomeG);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.categoriaTamanho);
        AutoCompleteTextView autoCompleteTextView2 = findViewById(R.id.categoriaIdade);
        AutoCompleteTextView autoCompleteTextView3 = findViewById(R.id.modalidade);
        AutoCompleteTextView autoCompleteTextView4 = findViewById(R.id.nGrupo);
        autoCompleteTextView4.setThreshold(1);

        autoCompleteTextView.setAdapter(adaptadorcategoriaT);
        autoCompleteTextView2.setAdapter(adaptadorcategoriaI);
        autoCompleteTextView3.setAdapter(adaptadormodo);
        autoCompleteTextView4.setAdapter(adaptadornomeG);

        nomeJ = findViewById(R.id.nJurado);
        nomeJ.setText(this.getIntent().getStringExtra("jurado"));
        AnomeG = findViewById(R.id.nGrupo);
        AcategoriaT = findViewById(R.id.categoriaTamanho);
        AcategoriaI = findViewById(R.id.categoriaIdade);
        Amodo = findViewById(R.id.modalidade);
        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);
        n6 = findViewById(R.id.n6);
        n7 = findViewById(R.id.n7);
        gerar = findViewById(R.id.salvar);

        gerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeJuri = nomeJ.getText().toString();
                String nomeGrupo = AnomeG.getText().toString();
                String categoriaTamanho = AcategoriaT.getText().toString();
                criarPdf("Notas" + "_" + nomeJuri + "_" + nomeGrupo + "_" + categoriaTamanho);
            }
        });
    }

    public void criarPdf(String title) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        startActivityForResult(intent, CREATEPDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATEPDF) {
            if (data.getData() != null) {
                if (!(TextUtils.isEmpty(nomeJ.getText()))
                        && !(TextUtils.isEmpty(AnomeG.getText()))
                        && !(TextUtils.isEmpty(AcategoriaT.getText()))
                        && !(TextUtils.isEmpty(AcategoriaI.getText()))
                        && !(TextUtils.isEmpty(Amodo.getText()))
                        && !(TextUtils.isEmpty(n1.getEditText().getText()))
                        && !(TextUtils.isEmpty(n2.getEditText().getText()))
                        && !(TextUtils.isEmpty(n3.getEditText().getText()))
                        && !(TextUtils.isEmpty(n4.getEditText().getText()))
                        && !(TextUtils.isEmpty(n5.getEditText().getText()))
                        && !(TextUtils.isEmpty(n6.getEditText().getText()))
                        && !(TextUtils.isEmpty(n7.getEditText().getText()))) {

                    Uri caminhoDoArquivo = data.getData();

                    String nomeJurado = nomeJ.getText().toString();
                    String nomeGrupo = AnomeG.getText().toString();
                    String nomeCategoriaTamanho = AcategoriaT.getText().toString();
                    String nomeCategoriaIdade = AcategoriaI.getText().toString();
                    String nomeModo = Amodo.getText().toString();
                    String nota1 = n1.getEditText().getText().toString();
                    String nota2 = n2.getEditText().getText().toString();
                    String nota3 = n3.getEditText().getText().toString();
                    String nota4 = n4.getEditText().getText().toString();
                    String nota5 = n5.getEditText().getText().toString();
                    String nota6 = n6.getEditText().getText().toString();
                    String nota7 = n7.getEditText().getText().toString();

                    Double r1 = Double.parseDouble(n1.getEditText().getText().toString());
                    Double r2 = Double.parseDouble(n2.getEditText().getText().toString());
                    Double r3 = Double.parseDouble(n3.getEditText().getText().toString());
                    Double r4 = Double.parseDouble(n4.getEditText().getText().toString());
                    Double r5 = Double.parseDouble(n5.getEditText().getText().toString());
                    Double r6 = Double.parseDouble(n6.getEditText().getText().toString());
                    Double r7 = Double.parseDouble(n7.getEditText().getText().toString());

                    Double notafinal = r1 + r2 + r3 + r4 + r5 + r6 + r7;
                    String nf  = String.format("%.2f", notafinal);

                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1240, 2000, 1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                    Canvas canvas = page.getCanvas();
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(40f);
                    paint.setFakeBoldText(true);
                    canvas.drawText("Notas " + nomeGrupo + " - " + nomeCategoriaTamanho + " - " + nomeCategoriaIdade ,  pageInfo.getPageWidth() / 2, 50, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setTextSize(35f);
                    paint.setFakeBoldText(false);

                    canvas.drawText("Jurado: " + nomeJurado, 500, 150, paint);
                    canvas.drawText("Grupo: " + nomeGrupo, 220, 205, paint);
                    canvas.drawText("Modalidade: " + nomeModo, 220, 245, paint);
                    canvas.drawText("Categoria Tamanho: " + nomeCategoriaTamanho, 650, 205, paint);
                    canvas.drawText("Categoria Idade: " + nomeCategoriaIdade, 650, 245, paint);
                    canvas.drawText("Reglamento y Vestuario: " + nota1, 50, 320, paint);
                    canvas.drawText("Correción Coreografica: " + nota2, 50, 360, paint);
                    canvas.drawText("Creatividad Coreografica: " + nota3, 50, 400, paint);
                    canvas.drawText("Técnica: " + nota4, 50, 440, paint);
                    canvas.drawText("Interpretacion Artistica: " + nota5, 50, 480, paint);
                    canvas.drawText("Harmonia: " + nota6, 50, 520, paint);
                    canvas.drawText("Utilizacion de escenario: " + nota7, 50, 560, paint);
                    canvas.drawText("Total: " + nf, 50, 650, paint);

                    pdfDocument.finishPage(page);
                    gravarPdf(caminhoDoArquivo, pdfDocument);
                }
            }
        }
    }

    private void gravarPdf(Uri caminhoDoArquivo, PdfDocument pdfDocument) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(Objects.requireNonNull(getContentResolver().openOutputStream(caminhoDoArquivo)));
            pdfDocument.writeTo(stream);
            pdfDocument.close();
            stream.flush();
            Toast.makeText(this, "PDF Gravado Com Sucesso", Toast.LENGTH_LONG).show();
            limparCampos();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Erro de arquivo não encontrado", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erro de entrada e saída", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro desconhecido" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void limparCampos() {
        nomeJ.setText("");
        AnomeG.setText("");
        AcategoriaT.setText("");
        AcategoriaI.setText("");
        Amodo.setText("");
        n1.getEditText().setText("");
        n2.getEditText().setText("");
        n3.getEditText().setText("");
        n4.getEditText().setText("");
        n5.getEditText().setText("");
        n6.getEditText().setText("");
        n7.getEditText().setText("");
    }
}