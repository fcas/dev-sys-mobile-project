package model;

import java.util.List;
import dao.DAOComentario;

public class ServicoComentario implements IServicoComentario {

	private static ServicoComentario singleton = null;
	private DAOComentario daoComentario;

	public ServicoComentario() {
		this.daoComentario = new DAOComentario();
	}

	public static ServicoComentario getInstance() {
		if (singleton == null) {
			singleton = new ServicoComentario();
		}
		return singleton;
	}

	@Override
	public void addComentario(Comentarios comentario) {
		daoComentario.add(comentario);
	}

	@Override
	public void editComentario(Comentarios comentario) {
		daoComentario.edit(comentario);
	}

	@Override
	public void deleteComentario(Comentarios comentario) {
		daoComentario.delete(comentario);
	}

	@Override
	public List<Comentarios> searchComentario(String Lugar) {
		return daoComentario.searchComentario(Lugar);
	}

}
