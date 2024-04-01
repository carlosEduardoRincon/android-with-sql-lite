package br.edu.ifsp.dpdm.enterprisecrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dpdm.enterprisecrud.model.Funcionario;

public class FuncionarioDAO extends DAO<Funcionario> {

    private SQLiteDatabase database;

    public FuncionarioDAO(Context context) {
        super(context);
        campos = new String[]{"id", "idade", "nome", "sexo"};
        tableName = "funcionario";
        this.database = getWritableDatabase();
    }

    public Funcionario getByNome(String nome) {
        Funcionario funcionario = null;

        Cursor cursor = executeSelect("nome = ?", new String[]{nome}, null);
        if (cursor!=null && cursor.moveToFirst()) {
            funcionario = serializeByCursor(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return funcionario;
    }

    public Funcionario getByID(Integer id) {
        Funcionario funcionario = null;

        Cursor cursor = executeSelect("id = ?", new String[]{String.valueOf(id)}, null);
        if (cursor!=null && cursor.moveToFirst()) {
            funcionario = serializeByCursor(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return funcionario;
    }

    public List<Funcionario> listAll() {
        List<Funcionario> list = new ArrayList<Funcionario>();
        Cursor cursor = executeSelect(null, null, "1");

        if (cursor!=null && cursor.moveToFirst()) {
            do {
                list.add(serializeByCursor(cursor));
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }

    public boolean salvar(Funcionario funcionario) {
        ContentValues values = serializeContentValues(funcionario);
        if (database.insert(tableName, null, values)>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deletar(Integer id) {
        if (database.delete(tableName, "id = ?", new String[]{String.valueOf(id)})>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean atualizar(Funcionario funcionario) {
        ContentValues values = serializeContentValues(funcionario);
        if (database.update(tableName,
                values,
                "id = ? ",
                new String[]{String.valueOf(funcionario.getId())})>0) {
            return true;
        } else {
            return false;
        }
    }


    private Funcionario serializeByCursor(Cursor cursor)
    {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(cursor.getInt(0));
        funcionario.setIdade(cursor.getInt(1));
        funcionario.setNome(cursor.getString(2));
        funcionario.setSexo(cursor.getString(3));

        return funcionario;

    }

    private ContentValues serializeContentValues(Funcionario funcionario)
    {
        ContentValues values = new ContentValues();
        values.put("id", funcionario.getId());
        values.put("idade", funcionario.getIdade());
        values.put("nome", funcionario.getNome());
        values.put("sexo", funcionario.getSexo());

        return values;
    }

    private Cursor executeSelect(String selection, String[] selectionArgs, String orderBy) {
        return database.query(tableName,
                campos,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
    }
}
