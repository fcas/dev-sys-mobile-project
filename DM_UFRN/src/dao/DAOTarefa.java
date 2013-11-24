package dao;

import java.util.ArrayList;
import java.util.List;

import model.Comentarios;
import model.Tarefas;
import activities.DataCalculos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAOTarefa {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private DAOLugar daoLugar;
	@SuppressWarnings("unused")
	private String[] allColumns = { MySQLiteHelper.COLUNA_ID,
			Tarefas.COLUNA_USUARIO, Tarefas.COLUNA_ID_LUGAR,
			Tarefas.COLUNA_DATA, Tarefas.COLUNA_HORARIO,
			Tarefas.COLUNA_DESCRICAO };

	public DAOTarefa(Context context) {
		dbHelper = new MySQLiteHelper(context);
		daoLugar = new DAOLugar(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Tarefas createTarefa(Tarefas tarefa) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUNA_ID, tarefa.getId());
		values.put(Tarefas.COLUNA_USUARIO, tarefa.getUsuario());
		values.put(Tarefas.COLUNA_ID_LUGAR, tarefa.getLugar().getId_local());
		values.put(Tarefas.COLUNA_DATA,
				DataCalculos.visaoToBanco(tarefa.getData()));
		values.put(Tarefas.COLUNA_HORARIO, tarefa.getHorario());
		values.put(Tarefas.COLUNA_DESCRICAO, tarefa.getDescricao());
		@SuppressWarnings("unused")
		long insertId = database.insert(Tarefas.TABELA_TAREFA, null, values);
		return tarefa;
	}

	public Tarefas updateTarefa(Tarefas tarefa) {

		Log.d("Nova Data", tarefa.getData());
		Log.d("ID Tarefa", String.valueOf(tarefa.getId()));
		String strFilter = MySQLiteHelper.COLUNA_ID + "='" + tarefa.getId()
				+ "'";
		ContentValues args = new ContentValues();
		args.put(Tarefas.COLUNA_DESCRICAO, tarefa.getDescricao());
		args.put(Tarefas.COLUNA_ID_LUGAR, tarefa.getLugar().getId_local());
		args.put(Tarefas.COLUNA_DATA,
				DataCalculos.visaoToBanco(tarefa.getData()));
		args.put(Tarefas.COLUNA_HORARIO, tarefa.getHorario());
		// args.put(Tarefas.COLUNA_LOCAL, tarefa.getLocal());

		database.update(Tarefas.TABELA_TAREFA, args, strFilter, null);
		return tarefa;
	}

	public void deleteTarefa(Tarefas tarefa) {
		long id = tarefa.getId();
		System.out.println("Tarefa deleted with id: " + id);
		database.delete(Tarefas.TABELA_TAREFA, MySQLiteHelper.COLUNA_ID + " = "
				+ id, null);
	}


	public List<Tarefas> getFutureTasksByUser(String userLogin) {
		List<Tarefas> tarefas = new ArrayList<Tarefas>();
		Log.d("Login-Parametro", userLogin);
		String now = DataCalculos.dataHoraAtual();
		String[] dataHora = new String[2];
	    dataHora = now.split(" ");
		Cursor cursor = database.rawQuery("Select * from "
				+ Tarefas.TABELA_TAREFA
				+ " where Usuario = ? and Data >= ? order by Data, Horario",
				new String[] { userLogin , dataHora[0]});

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tarefas tarefa = cursorToTarefa(cursor);
			Log.d("Login", tarefa.getUsuario());
			Log.d("Data", DataCalculos.bancoToVisao(tarefa.getData()));
			Log.d("Hora", tarefa.getHorario());
			Log.d("Descricao", tarefa.getDescricao());
			tarefa.setData(DataCalculos.bancoToVisao(tarefa.getData()));
			tarefas.add(tarefa);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tarefas;
	}

	
	public List<Tarefas> getAllTasks() {
		List<Tarefas> tarefas = new ArrayList<Tarefas>();

		/*
		 * Cursor cursor = database.query(Tarefas.TABELA_TAREFA, allColumns,
		 * null, null, null, null, null);
		 */
		Cursor cursor = database.rawQuery("Select * from "
				+ Tarefas.TABELA_TAREFA + " order by Data, Horario", null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tarefas tarefa = cursorToTarefa(cursor);
			Log.d("Login", tarefa.getUsuario());
			Log.d("Data", DataCalculos.bancoToVisao(tarefa.getData()));
			Log.d("Hora", tarefa.getHorario());
			Log.d("Descricao", tarefa.getDescricao());
			tarefa.setData(DataCalculos.bancoToVisao(tarefa.getData()));
			tarefas.add(tarefa);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tarefas;
	}

	public List<Tarefas> getTasksByUser(String userLogin) {
		List<Tarefas> tarefas = new ArrayList<Tarefas>();

		/*
		 * Cursor cursor = database.query(Tarefas.TABELA_TAREFA, allColumns,
		 * null, null, null, null, null);
		 */
		Log.d("Login-Parametro", userLogin);
		Cursor cursor = database.rawQuery("Select * from "
				+ Tarefas.TABELA_TAREFA
				+ " where Usuario = ? order by Data, Horario",
				new String[] { userLogin });

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tarefas tarefa = cursorToTarefa(cursor);
			Log.d("Login", tarefa.getUsuario());
			Log.d("Data", DataCalculos.bancoToVisao(tarefa.getData()));
			Log.d("Hora", tarefa.getHorario());
			Log.d("Descricao", tarefa.getDescricao());
			tarefa.setData(DataCalculos.bancoToVisao(tarefa.getData()));
			tarefas.add(tarefa);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tarefas;
	}

	private Tarefas cursorToTarefa(Cursor cursor) {
		daoLugar.open();
		Tarefas tarefa = new Tarefas();
		tarefa.setId(cursor.getLong(0));
		tarefa.setUsuario(cursor.getString(1));
		tarefa.setLugar(daoLugar.getLugarById(cursor.getInt(2)));
		tarefa.setData(cursor.getString(3));
		tarefa.setHorario(cursor.getString(4));

		tarefa.setDescricao(cursor.getString(5));
		daoLugar.close();
		return tarefa;
	}

	public void atualizarTarefas(List<Tarefas> tarefas){
		open();
		database.delete(Tarefas.TABELA_TAREFA, null, null);
		for(int i = 0; i < tarefas.size(); i++){
			createTarefa(tarefas.get(i));
		}
	}
	
}
