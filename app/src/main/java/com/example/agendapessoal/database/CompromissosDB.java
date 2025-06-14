package com.example.agendapessoal.database;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import com.example.agendapessoal.model.Compromisso;



public class CompromissosDB {

    private SQLiteDatabase database;
    private CompromissosDBHelper dbHelper;

    public CompromissosDB(Context context) {
        dbHelper = new CompromissosDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Inserir um novo compromisso
    public long inserirCompromisso(Compromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put(CompromissosDBSchema.CompromissosTable.Cols.DATA,
                compromisso.getData().getTime()); // Salva como timestamp
        values.put(CompromissosDBSchema.CompromissosTable.Cols.HORA,
                compromisso.getHora());
        values.put(CompromissosDBSchema.CompromissosTable.Cols.DESCRICAO,
                compromisso.getDescricao());

        long id = database.insert(CompromissosDBSchema.CompromissosTable.NAME, null, values);
        compromisso.setId(id);
        return id;
    }
    public List<Compromisso> buscarTodosCompromissos() {
        List<Compromisso> compromissos = new ArrayList<>();

        Cursor cursor = database.query(
                CompromissosDBSchema.CompromissosTable.NAME,
                //Tudo nulçl
                null,
                null,
                null,
                null,
                null,
                CompromissosDBSchema.CompromissosTable.Cols.DATA + " ASC, " +
                        CompromissosDBSchema.CompromissosTable.Cols.HORA + " ASC" // ordenar por data e hora
        );

        if (cursor.moveToFirst()) {
            do {
                Compromisso compromisso = cursorParaCompromisso(cursor);
                compromissos.add(compromisso);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return compromissos;
    }

   //vai buscar por data especifica
    public List<Compromisso> buscarCompromissosPorData(Date data) {
        List<Compromisso> compromissos = new ArrayList<>();

        // inicio ao fim
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(data);
        inicio.set(Calendar.HOUR_OF_DAY, 0);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.SECOND, 0);
        inicio.set(Calendar.MILLISECOND, 0);

        Calendar fim = Calendar.getInstance();
        fim.setTime(data);
        fim.set(Calendar.HOUR_OF_DAY, 23);
        fim.set(Calendar.MINUTE, 59);
        fim.set(Calendar.SECOND, 59);
        fim.set(Calendar.MILLISECOND, 999);

        String selection = CompromissosDBSchema.CompromissosTable.Cols.DATA + " >= ? AND " +
                CompromissosDBSchema.CompromissosTable.Cols.DATA + " <= ?";
        String[] selectionArgs = {
                String.valueOf(inicio.getTimeInMillis()),
                String.valueOf(fim.getTimeInMillis())
        };

        Cursor cursor = database.query(
                CompromissosDBSchema.CompromissosTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                CompromissosDBSchema.CompromissosTable.Cols.HORA + " ASC" // ordenar por hora
        );

        if (cursor.moveToFirst()) {
            do {
                Compromisso compromisso = cursorParaCompromisso(cursor);
                compromissos.add(compromisso);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return compromissos;
    }

    //att os existentes
    public int atualizarCompromisso(Compromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put(CompromissosDBSchema.CompromissosTable.Cols.DATA,
                compromisso.getData().getTime());
        values.put(CompromissosDBSchema.CompromissosTable.Cols.HORA,
                compromisso.getHora());
        values.put(CompromissosDBSchema.CompromissosTable.Cols.DESCRICAO,
                compromisso.getDescricao());

        String whereClause = CompromissosDBSchema.CompromissosTable.Cols.ID + " = ?";
        String[] whereArgs = { String.valueOf(compromisso.getId()) };

        return database.update(
                CompromissosDBSchema.CompromissosTable.NAME,
                values,
                whereClause,
                whereArgs
        );
    }

   //deleta
    public int excluirCompromisso(long id) {
        String whereClause = CompromissosDBSchema.CompromissosTable.Cols.ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        return database.delete(
                CompromissosDBSchema.CompromissosTable.NAME,
                whereClause,
                whereArgs
        );
    }

    // Converter cursor para objeto Compromisso
    private Compromisso cursorParaCompromisso(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                CompromissosDBSchema.CompromissosTable.Cols.ID));
        long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(
                CompromissosDBSchema.CompromissosTable.Cols.DATA));
        String hora = cursor.getString(cursor.getColumnIndexOrThrow(
                CompromissosDBSchema.CompromissosTable.Cols.HORA));
        String descricao = cursor.getString(cursor.getColumnIndexOrThrow(
                CompromissosDBSchema.CompromissosTable.Cols.DESCRICAO));

        Date data = new Date(timestamp);

        return new Compromisso(id, data, hora, descricao);
    }

    public void adicionarDadosExemplo() {
        Calendar hoje = Calendar.getInstance();

        // representação, em tela.
        inserirCompromisso(new Compromisso(hoje.getTime(), "09:00", "Reunião de trabalho"));
        inserirCompromisso(new Compromisso(hoje.getTime(), "14:30", "Consulta médica"));
        inserirCompromisso(new Compromisso(hoje.getTime(), "18:00", "Academia"));

        // representação, em tela.
        Calendar amanha = Calendar.getInstance();
        amanha.add(Calendar.DAY_OF_MONTH, 1);
        inserirCompromisso(new Compromisso(amanha.getTime(), "10:00", "Apresentação do projeto"));
        inserirCompromisso(new Compromisso(amanha.getTime(), "15:00", "Dentista"));
    }
}