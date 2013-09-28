package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOGenerico;
import dao.IDaoGenerico;

public class ServicoLugar implements IServicoLugar {

	private static ServicoLugar singleton = null;
	private IDaoGenerico dao;
	private static final String DATABASE_TABLE = "LUGARES";
	static Context contexto;

	public ServicoLugar() {
		this.dao = DAOGenerico.getInstance(contexto);

	}

	public static ServicoLugar getInstance() {
		if (singleton == null) {
			singleton = new ServicoLugar();
		}
		return singleton;
	}

	public void addLugar(ContentValues values) {
		dao.add(DATABASE_TABLE, values);
	}

	public void editLugar(ContentValues values, long id) {
		dao.edit(DATABASE_TABLE, id, values);
	}

	public void deleteLugar(long id) {
		dao.delete(DATABASE_TABLE, id);
	}

	public Cursor listLugares(String where, String[] colunas) {
		return dao.listWhere(DATABASE_TABLE, colunas, where);
	}

}
