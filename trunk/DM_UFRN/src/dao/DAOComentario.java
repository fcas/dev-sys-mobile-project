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
	      Comentarios.COLUNA_COMENTARIO};

	  public DAOComentario(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Comentarios createComentarios(String comment) {
	    ContentValues values = new ContentValues();
	    values.put(Comentarios.COLUNA_COMENTARIO, comment);
	    long insertId = database.insert(Comentarios.TABELA_COMENTARIOS, null,
	        values);
	    Cursor cursor = database.query(Comentarios.TABELA_COMENTARIOS,
	        allColumns, MySQLiteHelper.COLUNA_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Comentarios newComentarios = cursorToComentarios(cursor);
	    cursor.close();
	    return newComentarios;
	  }

	  public void deleteComentarios(Comentarios comment) {
	    long id = comment.getId();
	    System.out.println("Comentarios deleted with id: " + id);
	    database.delete(Comentarios.TABELA_COMENTARIOS, MySQLiteHelper.COLUNA_ID
	        + " = " + id, null);
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
	    // Make sure to close the cursor
	    cursor.close();
	    return Comentarioss;
	  }

	  private Comentarios cursorToComentarios(Cursor cursor) {
	    Comentarios Comentarios = new Comentarios();
	    Comentarios.setId(cursor.getLong(0));
	    Comentarios.setComment(cursor.getString(1));
	    return Comentarios;
	  }

}
