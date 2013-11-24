package dao;

import java.util.ArrayList;
import java.util.List;

import model.Lugar;
import model.Tarefas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAOLugar {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	public DAOLugar(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void salvarLugar(Lugar lugar) {
		open();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUNA_ID, lugar.getId_local());
		values.put(Lugar.COLUNA_NOME_LUGAR, lugar.getNome());
		database.insert(Lugar.DATABASE_TABLE, null, values);
		database.close();
	}

	public List<String> listarLugares() {
		List<String> labels = new ArrayList<String>();

		String selectQuery = "SELECT  * FROM " + Lugar.DATABASE_TABLE;

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		database.close();

		return labels;
	}

	public int idLugar(String lugar) {
		open();
		int id = -1;
		String selectQuery = "SELECT * FROM " + Lugar.DATABASE_TABLE
				+ " WHERE " + Lugar.COLUNA_NOME_LUGAR + " = '" + lugar + "'";
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor != null && cursor.moveToFirst()) {
			cursor.moveToFirst();
			id = Integer.parseInt(cursor.getString(0));
		}

		cursor.close();
		database.close();

		return id;
	}

	public Lugar getLugarById(int id) {
		Cursor result = database.rawQuery("Select * from Lugar where _id = ?",
				new String[] { String.valueOf(id) });
		Log.d("ID", String.valueOf(id));
		Lugar lugar = new Lugar();
		if (result.moveToNext()) {
			lugar.setId_local((result.getInt(0)));

			Log.d("Nome do Lugar", result.getString(1));
			lugar.setNome(result.getString(1));
		} else {
			lugar = null;
		}
		return lugar;
	}

	public void deleteTarefa(Tarefas tarefa) {
		long id = tarefa.getId();
		System.out.println("Tarefa deleted with id: " + id);
		database.delete(Tarefas.TABELA_TAREFA, MySQLiteHelper.COLUNA_ID + " = "
				+ id, null);
	}
	
	public void atualizarLugares(List<Lugar> lista){
		open();
		database.delete("Lugar", null, null);
		for(int i = 0; i < lista.size(); i++){
			salvarLugar(lista.get(i));
		}
	}
}
