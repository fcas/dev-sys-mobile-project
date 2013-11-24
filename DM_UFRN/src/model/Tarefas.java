package model;

import java.io.Serializable;

import dao.MySQLiteHelper;

public class Tarefas implements Serializable {

	private static final long serialVersionUID = 1L;


	private long id;
	private String usuario;
	@SuppressWarnings("unused")
	//private String local;
	private String data;
	private String horario;
	private String descricao;
	private Lugar lugar;
	@SuppressWarnings("unused")
	//private int idLugar;
	public static final String TABELA_TAREFA = "Tarefa";
	public static final String COLUNA_USUARIO = "Usuario";
	public static final String COLUNA_ID_LUGAR = "IDLocal";
	public static final String COLUNA_DATA = "Data";
	public static final String COLUNA_HORARIO = "Horario";
	public static final String COLUNA_DESCRICAO = "Descricao";
	
	public static final String CREATE_TAREFA = "CREATE TABLE "+TABELA_TAREFA+"("+MySQLiteHelper.COLUNA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
	+COLUNA_USUARIO+" TEXT NOT NULL, "
	+COLUNA_ID_LUGAR+ " INTEGER, "
	+COLUNA_DATA +" TEXT NOT NULL, "
	+COLUNA_HORARIO +" TEXT NOT NULL, "
	+COLUNA_DESCRICAO +" TEXT NOT NULL);";
	
	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar l) {
		this.lugar = l;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
