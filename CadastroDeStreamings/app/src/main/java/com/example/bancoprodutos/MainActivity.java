package com.example.bancoprodutos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvProdutos;
    private ArrayAdapter adapter;
    private List<produtos> listaDeProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProdutos = findViewById(R.id.listaProdutos);
        carregarProdutos();

        FloatingActionButton botaoAcionar = findViewById(R.id.floatingActionButtonIncluir);
        botaoAcionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent novaintent = new Intent(MainActivity.this, FormularioActivity.class);
                novaintent.putExtra("acao", "Inserir");
                startActivity(novaintent);
            }
        });

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int idProduto = listaDeProdutos.get(position).getId();
                Intent novaintent = new Intent(MainActivity.this, FormularioActivity.class);
                novaintent.putExtra("acao", "editar");
                novaintent.putExtra("idProduto", idProduto);
                startActivity(novaintent);
            }
        });

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                excluir(position);
                return true;
            }
        });
    }

    private void excluir(int posicao){
        produtos novoProdutoExcluir = listaDeProdutos.get(posicao);
        AlertDialog.Builder alertaExclusao = new AlertDialog.Builder(this);
        alertaExclusao.setTitle("Excluir");
        alertaExclusao.setIcon(android.R.drawable.ic_delete);
        alertaExclusao.setMessage("Você gostaria de excluir o item selecionado?  " + novoProdutoExcluir.getNome());
        alertaExclusao.setNeutralButton("Cancelar", null);
        alertaExclusao.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                produtosDAO.excluir(MainActivity.this, novoProdutoExcluir.getId());
                carregarProdutos();
            }
        });
        alertaExclusao.show();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        carregarProdutos();
    }

    public void carregarProdutos(){
        listaDeProdutos = produtosDAO.getProdutos(this);
        if(listaDeProdutos.size() == 0){
            //Para aparecer a mensagem, é instanciado um novo produto e dentro do campo que seria do "nome" é inserida a mensagem
            produtos listaVazia = new produtos("Você ainda não possui nenhum serviço de streaming cadastrado", "");
            listaDeProdutos.add(listaVazia);
            lvProdutos.setEnabled(false);
        } else{
            lvProdutos.setEnabled(true);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeProdutos);
        lvProdutos.setAdapter(adapter);
    }
}