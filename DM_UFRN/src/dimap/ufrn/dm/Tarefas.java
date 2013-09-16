package dimap.ufrn.dm;

import java.io.Serializable;

public class Tarefas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String local;
	private String horario;
	private String descricao;

	public String getLocal() {
		return local;
	}

	public void setLocal(String nome) {
		this.local = nome;
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

}
