package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOGenerico;

public class ServicoComentario implements IServicoComentario {

	private static ServicoComentario singleton = null;
	private DAOGenerico dao;
	private static final String DATABASE_TABLE = "COMENTARIO";
	static Context context;

	public ServicoComentario(Context contexto) {
		this.dao = DAOGenerico.getInstance(contexto);
	}

	public static ServicoComentario getInstance() {
		if (singleton == null) {
			singleton = new ServicoComentario(context);
		}
		return singleton;
	}

	@Override
	public void addComentario(ContentValues values) {
		dao.add(DATABASE_TABLE, values);
	}

	@Override
	public void editComentario(ContentValues values, long id) {
		dao.edit(DATABASE_TABLE, id, values);
	}

	@Override
	public void deleteComentario(long id) {
		dao.delete(DATABASE_TABLE, id);
	}

	@Override
	public Cursor listComentarios(String[] colunas) {
		return dao.list(DATABASE_TABLE, colunas);
	}

	@Override
	public Cursor listComentariosWhere(String tabela, String where,
			String[] colunas) {
		return dao.listWhere(tabela, colunas, where);
	}

}
