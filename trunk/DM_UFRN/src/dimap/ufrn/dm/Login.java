package dimap.ufrn.dm;

import dao.DAOUsuario;
import model.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	
	Button login;
	Button cadastro; 
	EditText user, senha;
	private DAOUsuario daoUsuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("UFRN ON TOUCH");
		setButtons();
		user = (EditText)findViewById(R.id.usuario_nome);
		senha = (EditText)findViewById(R.id.usuario_senha);
		daoUsuario = new DAOUsuario(this);
	}

	private void setButtons() {

		
		cadastro = (Button)findViewById(R.id.button_cadastrar);
		cadastro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent cadIntent = new Intent();
				cadIntent.setClass(Login.this,
						Cadastro.class);
				startActivity(cadIntent);

				finish();
			}
		});
		
		
		login = (Button) findViewById(R.id.button_fazerLogin);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				Usuario usuario = new Usuario();
				Intent mainIntent = new Intent();

				daoUsuario.open();
				usuario = daoUsuario.autenticar(user.getText().toString(), (senha.getText().toString()));
				daoUsuario.close();
				
				if(usuario != null){
					mainIntent.putExtra("usuario", usuario);
					
					mainIntent.setClass(Login.this,
							MainActivity.class);
					startActivity(mainIntent);
					
					finish();
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
