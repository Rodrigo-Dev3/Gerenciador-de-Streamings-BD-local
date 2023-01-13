package com.example.bancoprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome;
    private TextView tvId;
    private Spinner spCategorias;
    private Button btSalvar;
    private String acao;
    private produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.nomeProduto);
        spCategorias = findViewById(R.id.spCategoriasProduto);
        btSalvar = findViewById(R.id.btnSalvarProduto);
        acao = getIntent().getStringExtra("acao");

        if(acao.equals("editar"))
        {
            carregarFormulario();
        }

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Salvar(); }
        });
    }

    public void Salvar(){
        String nome = etNome.getText().toString();
        if(nome.isEmpty() || spCategorias.getSelectedItemPosition() == 0){
            Toast.makeText(this, "Você deve preencher os dois campos", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(acao.equals("Inserir")){
                produto = new produtos();
            }
        }
            produto.setNome(nome);
            produto.setCategoria(spCategorias.getSelectedItem().toString());
            if(acao.equals("Inserir")){
                produtosDAO.Inserir(this, produto);
                etNome.setText("");
                spCategorias.setSelection(0, true);
            }
            else
            {
                produtosDAO.editar(this, produto);
                finish();
            }
        }

    public void carregarFormulario(){
        int id = getIntent().getIntExtra("idProduto", 0);
        tvId = findViewById(R.id.idProduto);
        produto = produtosDAO.getProdutoPorId(this, id);
        tvId.setText(String.valueOf(id));
        etNome.setText(produto.getNome());
        String[] categorias = getResources().getStringArray(R.array.strCategoriasProduto);
        for(int i = 0; i < categorias.length; i++){
            if(produto.getCategoria().equals(categorias[i])){
                spCategorias.setSelection(i);
                break;
            }
        }
    }
}