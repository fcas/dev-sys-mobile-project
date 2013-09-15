package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cadastro extends Activity {
	Button cadastro;
	EditText edit_nome, edit_login, edit_senha, edit_confirmasenha, edit_descricao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		edit_nome = (EditText)findViewById(R.id.edit_usuario);
		edit_login = (EditText)findViewById(R.id.edit_login);
		edit_senha = (EditText)findViewById(R.id.edit_senha);
		edit_confirmasenha = (EditText)findViewById(R.id.edit_confirmasenha);
		edit_descricao = (EditText)findViewById(R.id.edit_descricao);
		setButtons();
	}

private void setButtons() {
	
	cadastro = (Button) findViewById(R.id.button_cadastro);
	cadastro.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View view) {
				Builder builder = new AlertDialog.Builder(Cadastro.this);  
		        builder.setTitle("Sucesso");  
		        builder.setMessage("Cadastro efetuado com sucesso");  
		        
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		        	public void onClick(DialogInterface arg0, int arg1) {
		        		Intent mainIntent = new Intent();
						mainIntent.setClass(Cadastro.this, MainActivity.class);
						startActivity(mainIntent);
						finish();
		        	}
		        });

		        AlertDialog alert = builder.create();  
		        alert.show();
				
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}

}
