package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DAOGenerico extends SQLiteOpenHelper implements IDaoGenerico {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "xdproductions";
	private static DAOGenerico instance;
	protected static SQLiteDatabase db;
	public static final String ID = "ID";

	public DAOGenerico(Context contexto) {
		super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DAOGenerico getInstance(Context contexto) {
	      Log.i("", "Tentando criar o banco...");
		if (instance == null) {
			instance = new DAOGenerico(contexto);
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

	@Override
	public void onCreate(SQLiteDatabase db) {
//		for (String criarTabela : Tabelas.SQL_DATABASE_CREATE) {
//			db.execSQL(criarTabela);
//		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		try {
//			for (String nomeTabela : Tabelas.TABLES) {
//				db.execSQL("DROP TABLE IF EXISTS " + nomeTabela);
//			}
//
//		} catch (SQLException se) {
//			Log.e("", "Nao foi possivel atualizar o banco de dados", se);
//		}
//		onCreate(db);
	}

	@Override
	public void close() {
		if (instance != null) {
			Log.i("", "Finalizando o banco de dados");
			db.close();
			instance = null;
		}
	}

	@Override
	public void add(String table, ContentValues values) {
		db.insert(table, null, values);
	}

	@Override
	public void edit(String table, long id, ContentValues values) {
		db.update(table, values, ID + "=" + id, null);
	}

	@Override
	public void delete(String table, long id) {
		db.delete(table, ID + "=" + id, null);
	}

	@Override
	public Cursor list(String tabela, String[] colunas) {
		return db.query(tabela, colunas, null, null, null, null, null);
	}

	public Cursor listWhere(String tabela, String[] colunas, String where) {
		Cursor cursor = db.query(true, tabela, colunas, where, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public boolean logar(String usuario, String senha) {
		return false;
	}

}