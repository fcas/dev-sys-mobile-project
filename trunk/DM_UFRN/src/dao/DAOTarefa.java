package dao;

import java.util.ArrayList;
import java.util.List;

import model.Tarefas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAOTarefa {

	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {MySQLiteHelper.COLUNA_ID, Tarefas.COLUNA_USUARIO, Tarefas.COLUNA_LOCAL,  Tarefas.COLUNA_DATA,  Tarefas.COLUNA_HORARIO, Tarefas.COLUNA_DESCRICAO};
	  public DAOTarefa(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Tarefas createTarefa(Tarefas tarefa) {
	    ContentValues values = new ContentValues();
	    values.put(Tarefas.COLUNA_USUARIO, "doido");
	    values.put(Tarefas.COLUNA_LOCAL, tarefa.getLocal());
	    values.put(Tarefas.COLUNA_DATA, tarefa.getData());
	    values.put(Tarefas.COLUNA_HORARIO, tarefa.getHorario());
	    values.put(Tarefas.COLUNA_DESCRICAO, tarefa.getDescricao());
	    @SuppressWarnings("unused")
		long insertId = database.insert(Tarefas.TABELA_TAREFA, null, values);
	    /*Cursor cursor = database.query(Tarefas.TABELA_TAREFA,
	        allColumns, MySQLiteHelper.COLUNA_ID + " = " + insertId, null,
	        null, null, null, null);
	    cursor.moveToFirst();
	    Tarefas novaTarefa = cursorToTarefa(cursor);
	    cursor.close();
	    return novaTarefa;*/
	    return tarefa;
	  }

	  public void deleteTarefa(Tarefas tarefa) {
	    long id = tarefa.getId();
	    System.out.println("Tarefa deleted with id: " + id);
	    database.delete(Tarefas.TABELA_TAREFA, MySQLiteHelper.COLUNA_ID
	        + " = " + id, null);
	  }

	  public List<Tarefas> getAllTasks() {
	    List<Tarefas> tarefas = new ArrayList<Tarefas>();

	    Cursor cursor = database.query(Tarefas.TABELA_TAREFA,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Tarefas tarefa = cursorToTarefa(cursor);
	    	Log.d("Login", tarefa.getUsuario());
	    	Log.d("Data", tarefa.getData());
	    	Log.d("Hora", tarefa.getHorario());
	    	Log.d("Descricao", tarefa.getDescricao());
	    	tarefas.add(tarefa);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return tarefas;
	  }

	  public List<Tarefas> getTasksByUser(String login) {
		    List<Tarefas> tarefas = new ArrayList<Tarefas>();
		    Cursor cursor = database.rawQuery("Select * from "+Tarefas.TABELA_TAREFA+" where "+Tarefas.COLUNA_USUARIO+" = ?",  new String[] {login});

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	Tarefas tarefa = cursorToTarefa(cursor);
		    	Log.d("Login", tarefa.getUsuario());
		    	Log.d("Data", tarefa.getData());
		    	Log.d("Hora", tarefa.getHorario());
		    	Log.d("Descricao", tarefa.getHorario());
		    	tarefas.add(tarefa);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return tarefas;
		  }

	  
	  private Tarefas cursorToTarefa(Cursor cursor) {
	    Tarefas tarefa = new Tarefas();
	    tarefa.setId(cursor.getLong(0));
	    tarefa.setUsuario(cursor.getString(1));
	    tarefa.setLocal(cursor.getString(2));
	    tarefa.setData(cursor.getString(3));
	    tarefa.setHorario(cursor.getString(4));
	    tarefa.setDescricao(cursor.getString(5));
	    return tarefa;
	  }

}
