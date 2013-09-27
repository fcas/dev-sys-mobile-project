package model;

import android.content.ContentValues;
import android.database.Cursor;

public interface IServicoLugar {
	
	public void addLugar(ContentValues values) ;
	public void editLugar(ContentValues values, long id);
	public void deleteLugar(long id);
	public Cursor listLugares(String where, String[] colunas);

}
