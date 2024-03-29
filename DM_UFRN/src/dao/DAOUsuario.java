package dao;

import java.util.ArrayList;
import java.util.List;

import model.Comentarios;
import model.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import excecoes.DadosIncompletosException;
import excecoes.UsuarioJaExisteException;

public class DAOUsuario {

	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { Usuario.COLUNA_LOGIN, Usuario.COLUNA_SENHA,
			  Usuario.COLUNA_NOME , Usuario.COLUNA_CURSO, Usuario.COLUNA_SOBRE};
	  @SuppressWarnings("unused")
	private DAOImagem daoImagem;
	  public DAOUsuario(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	    daoImagem = new DAOImagem();
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Usuario createUsuario(Usuario usuario) throws DadosIncompletosException, UsuarioJaExisteException {
		  if(!usuario.getNome().equals("") && !usuario.getSenha().trim().equals("") && !usuario.getLogin().trim().equals("")){
		    if(getUsuarioByLogin(usuario.getLogin()) == null){
				ContentValues values = new ContentValues();
			    values.put(Usuario.COLUNA_LOGIN, usuario.getLogin());
			    values.put(Usuario.COLUNA_SENHA, usuario.getSenha());
			    values.put(Usuario.COLUNA_NOME, usuario.getNome());
			    values.put(Usuario.COLUNA_CURSO, usuario.getCurso());
			    values.put(Usuario.COLUNA_SOBRE , usuario.getSobreMim());
				@SuppressWarnings("unused")
				long insertId = database.insert(Usuario.TABELA_USUARIO, null,
			        values);
			}
		    else{
		    	throw new UsuarioJaExisteException();
		    }
		  }else{
			  throw new DadosIncompletosException();
		  }
		  return usuario;
	  }
	  
	  public Usuario updateUsuario(String login, Usuario usuario) throws DadosIncompletosException {
		  if(!usuario.getNome().trim().equals("") && !usuario.getSenha().trim().equals("")){
			  
		    @SuppressWarnings("unused")
			ContentValues values = new ContentValues();
		    String strFilter = "login='" + login+"'";
		    ContentValues args = new ContentValues();
		    args.put(Usuario.COLUNA_NOME, usuario.getNome());
		    args.put(Usuario.COLUNA_CURSO, usuario.getCurso());
		    args.put(Usuario.COLUNA_SENHA, usuario.getSenha());
		    args.put(Usuario.COLUNA_SOBRE, usuario.getSobreMim());
		    database.update(Usuario.TABELA_USUARIO, args, strFilter, null);
		  }else{
			  throw new DadosIncompletosException();
		  }
		  return usuario;
	  }

	  public void deleteUsuarios(Usuario usuario) {
	    String login= usuario.getLogin();
	    System.out.println("Usuario deleted with login: " + login);
	    database.delete(Usuario.TABELA_USUARIO, Usuario.COLUNA_LOGIN
	        + " = " + login, null);
	  }

	  public List<Usuario> getAllUsers() {
	    List<Usuario> usuarios = new ArrayList<Usuario>();

	    Cursor cursor = database.query(Usuario.TABELA_USUARIO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Usuario usuario = cursorToUsuario(cursor);
	      usuarios.add(usuario);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return usuarios;
	  }
	  
	  public Usuario autenticar(String login, String senha){
		  Cursor result = database.rawQuery("Select * from Usuario where login = ? and senha = ?",  new String[] {login, senha});
		  Usuario user = new Usuario();
		  if(result.moveToNext()){
			  user.setLogin(result.getString(0));
			  user.setSenha(result.getString(1));
			  user.setNome(result.getString(2));
			  user.setCurso(result.getString(3));
			  user.setSobreMim(result.getString(4));
				//Log.d("Imagem", String.valueOf(img.getWidth()));				user.setImagemPerfil(img);
			
		  }else{
			  user = null;
		  }
		return user;
	  }
	  
	  public Usuario getUsuarioByLogin(String login){
		  Cursor result = database.rawQuery("Select * from Usuario where login = ?",  new String[] {login});
		  Usuario user = new Usuario();
		  if(result.moveToNext()){
			  user.setLogin(result.getString(0));
			  user.setSenha(result.getString(1));
			  user.setNome(result.getString(2));
			  user.setCurso(result.getString(3));
			  user.setSobreMim(result.getString(4));
		  }else{
			  user = null;
		  }
		return user;
	  }

		public void atualizarUsuario(List<Usuario> usuario){
			open();
			database.delete("Usuario", null, null);
			for(int i = 0; i < usuario.size(); i++){
				try {
					createUsuario(usuario.get(i));
				} catch (DadosIncompletosException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UsuarioJaExisteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	  

	  private Usuario cursorToUsuario(Cursor cursor) {
	    Usuario usuario = new Usuario();
	    usuario.setLogin(cursor.getString(0));
	    usuario.setSenha(cursor.getString(1));
	    usuario.setNome(cursor.getString(2));
	    usuario.setCurso(cursor.getString(3));
	    usuario.setSobreMim(cursor.getString(4));
	    return usuario;
	  }

}
