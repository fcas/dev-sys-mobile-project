package dimap.ufrn.dm;

import java.util.ArrayList;
import java.util.List;

public class Lugar {
	private String nome;
	private String localizacao;
	private String tipo;
	private int rate;
	private List<Comentarios> listaComentarios;

	public Lugar(){
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

}
