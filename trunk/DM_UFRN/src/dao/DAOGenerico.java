package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressWarnings("rawtypes")
public class DAOGenerico<T> extends SQLiteOpenHelper implements IDaoGenerico<T> {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dxproductions";
	protected static String nomeTabela;
	@SuppressWarnings("unused")
	private static String sql;
	private static DAOGenerico instance;
	protected static SQLiteDatabase db;
	public static final String ID = "ID";

	public DAOGenerico(Context contexto, String sql, String nomeTabela) {
		super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
		DAOGenerico.sql = sql;
		DAOGenerico.nomeTabela = nomeTabela;
	}
	
	public static DAOGenerico getInstance(Context contexto, String sql,
			String nomeTabela) {
		if (instance == null) {
			instance = new DAOGenerico(contexto, nomeTabela, nomeTabela);
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
		for (String criarTabela : Tabelas.SQL_DATABASE_CREATE) {
            db.execSQL(criarTabela);
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + nomeTabela);
		} catch (SQLException se) {
			Log.e("", "Nao foi possivel atualizar o banco de dados", se);
		}
		onCreate(db);
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
	public Cursor list(String tabela, String[] colunas, String where) {
        Cursor cursor = db.query(true, tabela, colunas, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
