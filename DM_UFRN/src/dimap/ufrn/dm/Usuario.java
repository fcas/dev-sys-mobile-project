package dimap.ufrn.dm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String curso;
	private List<Comentarios> comentarios;
	private List<Tarefas> tarefas;
	private String senha;
	private Drawable imagem;
	
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

}
