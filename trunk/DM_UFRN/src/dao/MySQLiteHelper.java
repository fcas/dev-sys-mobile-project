package dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String COLUMN_ID = "_id";

	private static final String DATABASE_NAME = "xdproductions";
	private static final int DATABASE_VERSION = 1;

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		for (String criarTabela : Tabelas.SQL_DATABASE_CREATE) {
			database.execSQL(criarTabela);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
		for (String nomeTabela : Tabelas.TABLES) {
			db.execSQL("DROP TABLE IF EXISTS " + nomeTabela);
		}

	} catch (SQLException se) {
		Log.e("", "Nao foi possivel atualizar o banco de dados", se);
	}
	onCreate(db);
	}

}
