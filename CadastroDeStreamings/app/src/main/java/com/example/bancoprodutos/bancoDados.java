package com.example.bancoprodutos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bancoDados extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "AppPersist";

    public bancoDados(@Nullable Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS produtos(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , nome TEXT NOT NULL, categoria TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
