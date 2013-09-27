package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOTarefa;

public class ServicoTarefa implements IServicoTarefa {

	private static ServicoTarefa singleton = null;
	private DAOTarefa daoTarefa;
	private static final String DATABASE_TABLE = "TAREFAS";
	static Context context;

	public ServicoTarefa() {
		this.daoTarefa = new DAOTarefa(context);
	}

	public static ServicoTarefa getInstance() {
		if (singleton == null) {
			singleton = new ServicoTarefa();
		}
		return singleton;
	}

	public void addTarefas(ContentValues values) {
		daoTarefa.add(DATABASE_TABLE, values);
	}

	public void editTarefas(ContentValues values) {
		daoTarefa.add(DATABASE_TABLE, values);
	}

	public void deleteTarefas(long id) {
		daoTarefa.delete(DATABASE_TABLE, id);
	}

	public Cursor listTarefas(String[] colunas, String where) {
		return daoTarefa.list(DATABASE_TABLE, colunas, where);
	}

}
