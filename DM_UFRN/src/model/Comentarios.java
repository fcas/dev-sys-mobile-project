package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public class Comentarios implements Serializable {

	private static final long serialVersionUID = 9179267868462004746L;
	public static final String DATABASE_TABLE = "COMENTARIO";
	public static final String[] COLUMNS = new String[] { "LUGAR", "AUTOR",
			"COMENTARIO" };
	public static final String COMENTARIO_TABLE_CREATE = " CREATE TABLE COMENTARIO ("
			+ "LUGAR TEXT, " + "AUTOR TEXT, " + "COMENTARIO);";
	private String comentario;
	private String autor;
	private Lugar lugar;
	private List<Comentarios> comentarios;
	private IServicoComentario sComentario;

	public Comentarios() {
		this.comentarios = new ArrayList<Comentarios>();
		this.sComentario = ServicoComentario.getInstance();
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getComentario() {
		return comentario;
	}

	public List<Comentarios> getComentarios() {

		Cursor cursor = null;
		cursor = sComentario.listComentarios(Comentarios.COLUMNS);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				int count = cursor.getCount();
				for (int i = 0; i < count; i++) {
					Comentarios alimentoRet = cursorParaComentarios(cursor);
					comentarios.add(alimentoRet);
					cursor.moveToNext();
				}
			}
		}

		return comentarios;

	}

	private Comentarios cursorParaComentarios(Cursor cursor) {
		if (cursor.getCount() == 0) {
			return null;
		}
		int autor = cursor.getColumnIndex("AUTOR");
		int comentario = cursor.getColumnIndex("COMENTARIO");
		Comentarios comentarios = new Comentarios();

		comentarios.setAutor(cursor.getString(autor));
		comentarios.setComentario(cursor.getString(comentario));

		return comentarios;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
