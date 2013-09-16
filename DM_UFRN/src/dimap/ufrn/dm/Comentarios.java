package dimap.ufrn.dm;

import java.io.Serializable;

public class Comentarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9179267868462004746L;
	private String comentario;

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	private String autor;

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
