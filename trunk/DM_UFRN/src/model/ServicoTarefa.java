package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOGenerico;

public class ServicoTarefa implements IServicoTarefa {

	private static ServicoTarefa singleton = null;
	private DAOGenerico dao;
	private static final String DATABASE_TABLE = "TAREFAS";
	static Context contexto;

	public ServicoTarefa() {
		this.dao = DAOGenerico.getInstance(contexto);
	}

	public static ServicoTarefa getInstance() {
		if (singleton == null) {
			singleton = new ServicoTarefa();
		}
		return singleton;
	}

	public void addTarefas(ContentValues values) {
		dao.add(DATABASE_TABLE, values);
	}

	public void editTarefas(ContentValues values) {
		dao.add(DATABASE_TABLE, values);
	}

	public void deleteTarefas(long id) {
		dao.delete(DATABASE_TABLE, id);
	}

	public Cursor listTarefas(String[] colunas, String where) {
		return dao.listWhere(DATABASE_TABLE, colunas, where);
	}

}
