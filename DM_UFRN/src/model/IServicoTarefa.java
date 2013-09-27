package model;

import android.content.ContentValues;
import android.database.Cursor;

public interface IServicoTarefa {
	
	public void addTarefas(ContentValues values);
	public void editTarefas(ContentValues values);
	public void deleteTarefas(long id);
	public Cursor listTarefas(String[] colunas, String where);

}
