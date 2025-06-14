package com.example.agendapessoal.database;

public class CompromissosDBSchema {

    public static final class CompromissosTable {
        public static final String NAME = "compromissos";

        public static final class Cols {
            public static final String ID = "id";
            public static final String DATA = "data";
            public static final String HORA = "hora";
            public static final String DESCRICAO = "descricao";
        }
    }

    // SQL para criar a tabela
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + CompromissosTable.NAME + " (" +
                    CompromissosTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CompromissosTable.Cols.DATA + " INTEGER NOT NULL, " +
                    CompromissosTable.Cols.HORA + " TEXT NOT NULL, " +
                    CompromissosTable.Cols.DESCRICAO + " TEXT NOT NULL" +
                    ")";

    // SQL para excluir a tabela

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + CompromissosTable.NAME;
}