package model;

import java.io.Serializable;

import dao.MySQLiteHelper;
//import java.util.ArrayList;
//import java.util.List;

//import android.database.Cursor;

public class Comentarios implements Serializable {

	private static final long serialVersionUID = 9179267868462004746L;
	// Database creation sql statement
	public static final String TABELA_COMENTARIOS = "Comentarios";
	public static final String COLUNA_COMENTARIO = "Comentario";
	public static final String COLUNA_AUTOR = "Autor";
	public static final String COLUNA_ID_LUGAR = "id_lugar";
	public static final String CREATE_COMENTARIOS = "CREATE TABLE "
			+ TABELA_COMENTARIOS + "(" + MySQLiteHelper.COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_AUTOR
			+ " TEXT NOT NULL," + COLUNA_COMENTARIO + " TEXT NOT NULL,"
			+ COLUNA_ID_LUGAR + " INTEGER" + ");";

	private String autor;
	private Lugar lugar;
	private long id;
	private String comentario;
	
	public Comentarios(){ 
		lugar = new Lugar();
	}

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
