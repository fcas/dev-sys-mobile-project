package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileEdit extends Activity {

	Usuario usuario;
	Button feito;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setContentView(R.layout.activity_profile_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_edit, menu);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		
		
		
		return true;
	}
	
	//Botão voltar...
		@Override
		public void onBackPressed() {
			Intent voltaIntent = new Intent();
			voltaIntent.putExtra("usuario", usuario);
			voltaIntent.setClass(ProfileEdit.this, MainActivity.class);
			startActivity(voltaIntent);
			finish();
			
			
		}
		
		private void setButtons() {

			
			
			feito = (Button) findViewById(R.id.button1);
			feito.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					
					Usuario usuario = new Usuario();
					Intent mainIntent = new Intent();

					EditText editText = (EditText) findViewById(R.id.editText1);
					usuario.setNome(editText.getText().toString());
					EditText editText2 = (EditText) findViewById(R.id.editText2);
					usuario.setCurso(editText2.getText().toString());
					
					mainIntent.putExtra("usuario", usuario);
					
					mainIntent.setClass(ProfileEdit.this,
							MainActivity.class);
					startActivity(mainIntent);

					finish();
				}
			});
			
		}

}
