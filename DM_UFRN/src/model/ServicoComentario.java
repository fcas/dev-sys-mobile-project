package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOComentario;

public class ServicoComentario implements IServicoComentario {

	private static ServicoComentario singleton = null;
	private DAOComentario daoComentario;
	private static final String DATABASE_TABLE = "COMENTARIOS";
	static Context context; 

	public ServicoComentario(Context context) {
		this.daoComentario = new DAOComentario(context);
	}

	public static ServicoComentario getInstance() {
		if (singleton == null) {
			singleton = new ServicoComentario(context);
		}
		return singleton;
	}

	@Override
	public void addComentario(ContentValues values) {
		daoComentario.add(DATABASE_TABLE, values);
	}

	@Override
	public void editComentario(ContentValues values, long id) {
		daoComentario.edit(DATABASE_TABLE, id, values);
	}

	@Override
	public void deleteComentario(long id) {
		daoComentario.delete(DATABASE_TABLE, id);
	}

	@Override
	public Cursor listComentarios(String[] colunas) {
		return daoComentario.list(DATABASE_TABLE, colunas);
	}
	
	@Override
	public Cursor listComentariosWhere(String tabela, String where, String[] colunas) {
		return daoComentario.listWhere(tabela, colunas, where);
	}

}
