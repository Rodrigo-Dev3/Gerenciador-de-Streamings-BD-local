package com.example.bancoprodutos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class produtosDAO {

    //Método Inserir
    public static void Inserir(Context context, produtos produto){
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("categoria", produto.getCategoria());

        bancoDados novoBanco = new bancoDados(context);
        SQLiteDatabase db = novoBanco.getWritableDatabase();

        db.insert("produtos", null, values);
    }

    //Método Editar
    public static void editar(Context context, produtos produto){
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("categoria", produto.getCategoria());

        bancoDados novoBancoEditar = new bancoDados(context);
        SQLiteDatabase db = novoBancoEditar.getWritableDatabase();

        db.update("produtos", values, " id = " + produto.getId(), null);
    }

    //Método Excluir
    public static void excluir(Context context, int idProduto){
        bancoDados novoBancoExcluir = new bancoDados(context);
        SQLiteDatabase db = novoBancoExcluir.getWritableDatabase();

        db.delete("produtos", " id = " + idProduto, null);
    }

    //Método Listar
    public static List<produtos> getProdutos(Context context){
        List<produtos> lista = new ArrayList<>();
        bancoDados novoBanco = new bancoDados(context);
        SQLiteDatabase db = novoBanco.getWritableDatabase();

        Cursor novoCursor = db.rawQuery("SELECT * FROM produtos ORDER BY nome", null);
        if(novoCursor.getCount() > 0){
            novoCursor.moveToFirst();
            do{
                produtos novoProduto = new produtos();
                novoProduto.setId(novoCursor.getInt(0));
                novoProduto.setNome(novoCursor.getString(1));
                novoProduto.setCategoria(novoCursor.getString(2));
                lista.add(novoProduto);
            }while(novoCursor.moveToNext());
        }
        return lista;
    }

    //Método Listar por ID
    public static produtos getProdutoPorId(Context context, int idProduto){
        bancoDados novoBancoId = new bancoDados(context);
        SQLiteDatabase db = novoBancoId.getWritableDatabase();
        Cursor novoCursorId = db.rawQuery("SELECT * FROM produtos WHERE id = "+idProduto, null);
        if(novoCursorId.getCount() > 0){
            novoCursorId.moveToFirst();
            produtos novoProdutoId = new produtos();
            novoProdutoId.setId(novoCursorId.getInt(0));
            novoProdutoId.setNome(novoCursorId.getString(1));
            novoProdutoId.setCategoria(novoCursorId.getString(2));
            return novoProdutoId;
        }
        else { return null; }
    }
}
