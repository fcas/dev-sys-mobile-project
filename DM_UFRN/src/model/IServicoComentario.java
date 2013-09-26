package model;

import java.util.List;

public interface IServicoComentario {
	
	public void addComentario(Comentarios comentario);
	public void editComentario(Comentarios comentario);
	public void deleteComentario (Comentarios comentario);
	public List<Comentarios> searchComentario(String Lugar);
}
