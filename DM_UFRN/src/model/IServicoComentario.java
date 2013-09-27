package model;

import android.content.ContentValues;
import android.database.Cursor;

public interface IServicoComentario {

	public void addComentario(ContentValues values);

	public void editComentario(ContentValues values, long id);

	public void deleteComentario(long id);

	public Cursor listComentarios(String where, String[] colunas);
}
