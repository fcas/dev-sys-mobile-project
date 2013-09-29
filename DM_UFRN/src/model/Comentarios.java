package model;

import java.io.Serializable;

import dao.MySQLiteHelper;
//import java.util.ArrayList;
//import java.util.List;

//import android.database.Cursor;

public class Comentarios implements Serializable {

	private static final long serialVersionUID = 9179267868462004746L;
	  // Database creation sql statement
	public static final String TABLE_COMMENTS = "comments";
	public static final String COLUMN_COMMENT = "comment";
	public static final String CREATE_COMMENTS = "create table "
	      + TABLE_COMMENTS + "(" + MySQLiteHelper.COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_COMMENT
	      + " text not null);";
	

	private String autor;
	private Lugar lugar;
	private long id;
	private String comentario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comentario;
	}

	public void setComment(String comment) {
		this.comentario = comment;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}
	
	@Override
	public String toString() {
		return comentario;
	}

}
