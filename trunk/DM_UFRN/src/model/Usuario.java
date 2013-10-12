package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private String login;
	private String curso;
	private String sobreMim;
	private List<Comentarios> comentarios;
	private List<Tarefas> tarefas;
	private String senha;
	private Bitmap imagemPerfil;

	public static final String DATABASE_TABLE = "Usuario";
	public static final String TABELA_USUARIO = "Usuario";
	public static final String COLUNA_LOGIN = "Login";
	public static final String COLUNA_SENHA = "Senha";
	public static final String COLUNA_NOME = "Nome";
	public static final String COLUNA_CURSO = "Curso";
	public static final String COLUNA_SOBRE = "SobreMim";
	
	
	public static final String CREATE_USUARIO = " CREATE TABLE "+TABELA_USUARIO+"("
	+COLUNA_LOGIN+" TEXT PRIMARY KEY NOT NULL, "
	+COLUNA_SENHA+ " TEXT NOT NULL, "
	+COLUNA_NOME +" TEXT NOT NULL, "
	+COLUNA_CURSO +" TEXT, "
	+COLUNA_SOBRE +" TEXT);";

	public Usuario() {
		this.comentarios = new ArrayList<Comentarios>();
		this.tarefas = new ArrayList<Tarefas>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	private String nome;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	public List<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Tarefas> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefas> tarefas) {
		this.tarefas = tarefas;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSobreMim() {
		return sobreMim;
	}

	public void setSobreMim(String sobreMim) {
		this.sobreMim = sobreMim;
	}

	public Bitmap getImagemPerfil() {
		return imagemPerfil;
	}

	public void setImagemPerfil(Bitmap imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}

}
