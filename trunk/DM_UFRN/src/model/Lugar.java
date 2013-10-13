package model;

import java.util.ArrayList;
import java.util.List;

import dao.MySQLiteHelper;

public class Lugar {
	private String nome;
	private String localizacao;
	private String tipo;

	private int id_local;
	private int rate;
	private List<Comentarios> listaComentarios;
	public static final String DATABASE_TABLE = "Lugar";
	public static final String COLUNA_NOME_LUGAR = "Nome";
	public static final String LUGAR_TABLE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + "(" + MySQLiteHelper.COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUNA_NOME_LUGAR + " TEXT)";

	public Lugar() {
		this.listaComentarios = new ArrayList<Comentarios>();
	}

	public List<Comentarios> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(List<Comentarios> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getId_local() {
		return id_local;
	}

	public void setId_local(int id_local) {
		this.id_local = id_local;
	}

}
