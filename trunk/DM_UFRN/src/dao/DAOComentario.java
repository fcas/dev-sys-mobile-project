package dao;

import android.content.Context;
import model.Comentarios;

public class DAOComentario extends DAOGenerico<Comentarios> implements
		IDAOComentario {
	
	public DAOComentario(Context context) {
		super(context, nomeTabela, nomeTabela);
	}
	
}
