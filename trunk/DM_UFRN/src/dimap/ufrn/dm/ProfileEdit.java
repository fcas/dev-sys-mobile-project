package dimap.ufrn.dm;

import model.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileEdit extends Activity {

	Usuario usuario;
	Button feito;
	ImageButton trocaImagem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setContentView(R.layout.activity_profile_edit);
		setButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_edit, menu);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		return true;
	}

	// Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ProfileEdit.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();

	}

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		 if (requestCode == 100) {
		 Bitmap image = (Bitmap) data.getExtras().get("data");
		 trocaImagem.setImageBitmap(image);	 
		 }
		 
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
				mainIntent.setClass(ProfileEdit.this, MainActivity.class);
				startActivity(mainIntent);

				finish();
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

}
