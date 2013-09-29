package model;

import java.util.List;

import android.content.ContentValues;
//import android.database.Cursor;

public interface IServicoComentario {

	public void addComentario(ContentValues values);

	//public void editComentario(ContentValues values, long id);

	public void deleteComentario(long id);

	//public Cursor listComentarios(String[] colunas);

	//public Cursor listComentariosWhere(String tabela, String where, String[] colunas);
	
	public List<Comentarios> getAllComments();
	
}
