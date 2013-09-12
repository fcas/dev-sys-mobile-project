package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}

	private void setButtons() {
		login = (Button) findViewById(R.id.button_fazerLogin);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				Usuario usuario = new Usuario();
				Intent mainIntent = new Intent();

				EditText editText = (EditText) findViewById(R.id.usuario_nome);
				usuario.setNome(editText.getText().toString());
				editText = (EditText) findViewById(R.id.usuario_senha);
				usuario.setSenha(editText.getText().toString());
				
				mainIntent.putExtra("usuario", usuario);
				
				mainIntent.setClass(Login.this,
						MainActivity.class);
				startActivity(mainIntent);

				finish();
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
