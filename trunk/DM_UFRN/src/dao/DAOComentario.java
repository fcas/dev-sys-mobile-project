package dao;

import java.util.ArrayList;
import java.util.List;
import model.Comentarios;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DAOComentario {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUNA_ID,
			Comentarios.COLUNA_AUTOR, Comentarios.COLUNA_COMENTARIO,
			Comentarios.COLUNA_ID_LUGAR };
	private DAOLugar daoLugar;

	public DAOComentario(Context context) {
		dbHelper = new MySQLiteHelper(context);
		daoLugar = new DAOLugar(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Comentarios createComentarios(Comentarios comment) {
		ContentValues values = new ContentValues();
		values.put(Comentarios.COLUNA_AUTOR, comment.getAutor());
		values.put(Comentarios.COLUNA_COMENTARIO, comment.getComment());
		values.put(Comentarios.COLUNA_ID_LUGAR, comment.getLugar()
				.getId_local());
		long insertId = database.insert(Comentarios.TABELA_COMENTARIOS, null,
				values);
		comment.setId(insertId);
		return comment;
	}

	public void deleteComentarios(Comentarios comment) {
		long id = comment.getId();
		System.out.println("Comentarios deleted with id: " + id);
		database.delete(Comentarios.TABELA_COMENTARIOS,
				MySQLiteHelper.COLUNA_ID + " = " + id, null);
	}

	public List<Comentarios> getAllComments() {
		List<Comentarios> Comentarioss = new ArrayList<Comentarios>();

		Cursor cursor = database.query(Comentarios.TABELA_COMENTARIOS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Comentarios Comentarios = cursorToComentarios(cursor);
			Comentarioss.add(Comentarios);
			cursor.moveToNext();
		}
		cursor.close();
		return Comentarioss;
	}

	private Comentarios cursorToComentarios(Cursor cursor) {
		Comentarios Comentarios = new Comentarios();
		daoLugar.open();
		Comentarios.setId(cursor.getLong(0));
		Comentarios.setAutor(cursor.getString(1));
		Comentarios.setComment(cursor.getString(2));
		Comentarios.setLugar(daoLugar.getLugarById(cursor.getInt(3)));
		daoLugar.close();
		return Comentarios;
	}

}
