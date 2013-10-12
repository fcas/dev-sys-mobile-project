package dimap.ufrn.dm;

//O bot�o voltar est� voltando para a tela de login...

import dao.DAOImagem;
import dao.DAOUsuario;
import model.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Cadastro extends Activity {
	Button cadastro;
	Usuario usuario;
	ImageButton trocaImagem;
	DAOUsuario daoUsuario;
	DAOImagem daoImagem;
	EditText edit_nome, edit_login, edit_senha, edit_confirmasenha, edit_sobre, edit_curso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		edit_nome = (EditText)findViewById(R.id.edit_usuario);
		edit_login = (EditText)findViewById(R.id.edit_login);
		edit_senha = (EditText)findViewById(R.id.edit_senha);
		edit_confirmasenha = (EditText)findViewById(R.id.edit_confirmasenha);
		edit_sobre= (EditText)findViewById(R.id.edit_sobre);
		edit_curso = (EditText)findViewById(R.id.edit_curso);
		daoUsuario = new DAOUsuario(this);
		daoImagem = new DAOImagem(this);
		daoUsuario.open();
		setButtons();
	}

private void setButtons() {
	
	cadastro = (Button) findViewById(R.id.button_cadastro);
	cadastro.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View view) {
				final boolean senhaValida = edit_senha.getText().toString().equals(edit_confirmasenha.getText().toString());
				final Builder builder = new AlertDialog.Builder(Cadastro.this);  
				if(senhaValida){
					builder.setTitle("Sucesso");  
				    builder.setMessage("Cadastro efetuado com sucesso");  
				}
			    else{
        			builder.setTitle("Erro");  
			        builder.setMessage("Senhas n�o se correspondem");  
        		}
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		        	public void onClick(DialogInterface arg0, int arg1) {
		        		if(senhaValida){
			        		Usuario usuario = new Usuario();
			        		usuario.setNome(edit_nome.getText().toString());
			        		usuario.setCurso(edit_curso.getText().toString());
			        		usuario.setSenha(edit_senha.getText().toString());
			        		usuario.setSobreMim(edit_sobre.getText().toString());
			        		usuario.setLogin(edit_login.getText().toString());
			        		usuario.setImagemPerfil(((BitmapDrawable)trocaImagem.getDrawable()).getBitmap());		        		
			        		daoUsuario.createUsuario(usuario);
			        		daoUsuario.close();
			        	//	IServicoUsuario sUsuario = new ServicoUsuario();
			        		//sUsuario.addUsuario(usuario);
			        		Intent mainIntent = new Intent();
							mainIntent.setClass(Cadastro.this, Login.class);
							startActivity(mainIntent);
							finish();
		        		}
		        	}
		        });

		        AlertDialog alert = builder.create();  
		        alert.show();
				
			}
		});
	trocaImagem = (ImageButton)findViewById(R.id.trocaImagem);
	trocaImagem.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setType("image/*");
            intent.putExtra("crop", "true");  //op��o de cropar.
            intent.putExtra("outputX", 150);  //poe a resolu��o que vc quiser.
            intent.putExtra("outputY", 150); 
            intent.putExtra("aspectX", 1);  //poe aspect ratio que vc quiser
            intent.putExtra("aspectY", 1);

            intent.putExtra("return-data", true);
		    startActivityForResult(intent, 100);
		
			
		}
		
	});
	
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == 100) {
		 Bitmap image = (Bitmap) data.getExtras().get("data");
		 trocaImagem.setImageBitmap(image);	 
		 }
		 
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}
	
	//Bot�o voltar...
		@Override
		public void onBackPressed() {
			Intent voltaIntent = new Intent();
			voltaIntent.setClass(Cadastro.this, Login.class);
			startActivity(voltaIntent);
			finish();
		}

}
