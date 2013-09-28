package dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import model.Comentarios;

public class DAOComentario extends DAOGenerico<Comentarios> implements
		IDAOComentario {

	private static SQLiteDatabase db;
	private static DAOComentario instance;
	private static final String COMENTARIO_TABLE_CREATE = " CREATE TABLE COMENTARIO ("
			+ "LUGAR TEXT, " + "AUTOR TEXT, " + "COMENTARIO);";

	public DAOComentario(Context context) {
		super(context, nomeTabela, nomeTabela);
		try {
			db.execSQL(COMENTARIO_TABLE_CREATE);
			super.onCreate(db);
		} catch (SQLException se) {
			Log.e("", "Nao foi possible criar o banco de dados", se);
		}
		
	}

	public static DAOComentario getInstance(Context contexto, String sql,
			String nomeTabela) {
		if (instance == null) {
			instance = new DAOComentario(contexto);
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
