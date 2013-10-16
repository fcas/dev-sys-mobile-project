package activities;

import model.Usuario;
import dao.DAOImagem;
import dao.DAOUsuario;
import dimap.ufrn.dm.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RedefinirSenha extends Activity {
	EditText edit_senhaatual, edit_novasenha, edit_confirmasenha;
	Button pronto;
	Usuario usuario;
	DAOUsuario daoUsuario;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daoUsuario = new DAOUsuario(this);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setContentView(R.layout.activity_redefinirsenha);
		
		edit_senhaatual = (EditText) findViewById(R.id.edit_senhaatual);
		edit_novasenha = (EditText) findViewById(R.id.edit_novasenha);
		edit_confirmasenha = (EditText) findViewById(R.id.edit_confirmasenha);		
		setButtons();
	}
	
	public void setButtons(){
		pronto = (Button) findViewById(R.id.pronto);		
		pronto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				final boolean senhaValida = edit_confirmasenha.getText().toString().equals(edit_novasenha.getText().toString());
				final Builder builder = new AlertDialog.Builder(RedefinirSenha.this); 
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		        	public void onClick(DialogInterface arg0, int arg1) {
		        		arg0.dismiss();
		        	}
		        });
				if(senhaValida){
					daoUsuario.open();
					Usuario user = daoUsuario.autenticar(usuario.getLogin(), edit_senhaatual.getText().toString());
					daoUsuario.close();
				    if(user != null){
				    	usuario.setSenha(edit_novasenha.getText().toString());
				    	Intent voltaTelaProfile = new Intent();
				    	voltaTelaProfile.setClass(RedefinirSenha.this, ProfileEdit.class);
				    	voltaTelaProfile.putExtra("usuario", usuario);
				    	startActivity(voltaTelaProfile);
						finish();
				    	
				    }else{
				    	builder.setTitle("Erro");  
					    builder.setMessage("Voce digitou uma senha invalida!");  
					    AlertDialog alert = builder.create();  
				        alert.show();
				    }
				}
			    else{
        			builder.setTitle("Erro");  
			        builder.setMessage("Senhas nao se correspondem");  
			        AlertDialog alert = builder.create();  
			        alert.show();
        		}	        
			}
		});
	}
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(RedefinirSenha.this, ProfileEdit.class);
		startActivity(voltaIntent);
		finish();
	}

}
