package model;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import dao.DAOUsuario;

public class ServicoUsuario implements IServicoUsuario{
	
	private static ServicoUsuario singleton = null;
	private DAOUsuario daoUsuario;
	private static final String DATABASE_TABLE = "USUARIOS";
	static Context context; 

	public ServicoUsuario() {
		this.daoUsuario = new DAOUsuario(context);
	}

	public static ServicoUsuario getInstance() {
		if (singleton == null) {
			singleton = new ServicoUsuario();
		}
		return singleton;
	}

	@Override
	public void addUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();		
		values.put("login", usuario.getLogin());
		values.put("senha", usuario.getSenha());
		values.put("nome", usuario.getNome());
		values.put("curso", usuario.getCurso());
		values.put("sobreMim", usuario.getSobreMim());		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		usuario.getImagemPerfil().compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] b = stream.toByteArray();
		values.put("imagem", b);
		daoUsuario.add(DATABASE_TABLE, values);
	}

	@Override
	public void editUsuario(long id, ContentValues values) {
		daoUsuario.edit(DATABASE_TABLE, id, values);
	}

	@Override
	public void deleteUsuario(Usuario usuario, long id) {
		daoUsuario.delete(DATABASE_TABLE, id);
	}

	@Override
	public Cursor listUsuarios(String where, String[] colunas) {
		return daoUsuario.list(DATABASE_TABLE, colunas, where);
	}

	@Override
	public Cursor listUsuariosCurso(String where, String[] colunas) {
		return daoUsuario.list(DATABASE_TABLE, colunas, where);
	}

	@Override
	public boolean logar(String usuario, String senha) {
		return daoUsuario.logar(usuario, senha);
	}

}
