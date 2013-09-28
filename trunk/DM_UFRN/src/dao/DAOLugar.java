package dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import model.Lugar;

public class DAOLugar extends DAOGenerico<Lugar> implements IDAOLugar {
	
	private static SQLiteDatabase db;
	private static DAOLugar instance;
	private static final String LUGAR_TABLE_CREATE = " CREATE TABLE COMENTARIO ("
			+ "LUGAR TEXT, " + "AUTOR TEXT, " + "COMENTARIO);";

	public DAOLugar(Context context) {
		super(context);
		try {
			db.execSQL(LUGAR_TABLE_CREATE);
			super.onCreate(db);
		} catch (SQLException se) {
			Log.e("", "Nao foi possible criar o banco de dados", se);
		}
		
	}

	public static DAOLugar getInstance(Context contexto, String sql,
			String nomeTabela) {
		if (instance == null) {
			instance = new DAOLugar(contexto);
			try {
				db = instance.getWritableDatabase();
			} catch (SQLiteException se) {
				Log.e("", "Exception na instanciacao do DAOGenerico", se);
			} catch (Exception e) {
				Log.e("", "Exception em DAOGenerico", e);
			}
		}
		return instance;
	}

}
