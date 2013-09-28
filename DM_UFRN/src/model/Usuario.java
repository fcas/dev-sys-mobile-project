package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String curso;
	private String sobreMim;
	private List<Comentarios> comentarios;
	private List<Tarefas> tarefas;
	private String senha;
	private Bitmap imagemPerfil;
	public static final String DATABASE_TABLE = "USUARIO";
	public final static String USUARIO_TABLE_CREATE = "CREATE TABLE USUARIO(" +
			"login text PRIMARY KEY, " +
			"senha text, " +
			"nome text, " +
			"curso text, "+
			"sobreMim text," +
			"imagem blob)";
	
	public Usuario(){
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
