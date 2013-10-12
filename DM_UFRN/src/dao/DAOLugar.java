package dao;

import java.util.ArrayList;
import java.util.List;

import model.Lugar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DAOLugar {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	public DAOLugar(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void salvarLugar(String lugar) {
		open();
		ContentValues values = new ContentValues();
		values.put(Lugar.COLUNA_NOME_LUGAR, lugar);
		database.insert(Lugar.DATABASE_TABLE, null, values);
		database.close();
	}

	public List<String> listarLugares() {
		List<String> labels = new ArrayList<String>();

		String selectQuery = "SELECT  * FROM " + Lugar.DATABASE_TABLE;

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		database.close();

		return labels;
	}

}
