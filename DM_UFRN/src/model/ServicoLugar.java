package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOLugar;
import dao.IDAOLugar;

public class ServicoLugar implements IServicoLugar {

	private static ServicoLugar singleton = null;
	private IDAOLugar daoLugar;
	private static final String DATABASE_TABLE = "LUGARES";
	static Context context;

	public ServicoLugar() {
		this.daoLugar = new DAOLugar(context);

	}

	public static ServicoLugar getInstance() {
		if (singleton == null) {
			singleton = new ServicoLugar();
		}
		return singleton;
	}

	public void addLugar(ContentValues values) {
		daoLugar.add(DATABASE_TABLE, values);
	}

	public void editLugar(ContentValues values, long id) {
		daoLugar.edit(DATABASE_TABLE, id, values);
	}

	public void deleteLugar(long id) {
		daoLugar.delete(DATABASE_TABLE, id);
	}

	public Cursor listLugares(String where, String[] colunas) {
		return daoLugar.list(DATABASE_TABLE, colunas, where);
	}

}
