package activities;

//O bot�o voltar est� voltando para a tela principal

import model.Usuario;
import activities.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TelaComentarios extends Activity {
	private Button button;
	Usuario usuario;
	private Button comentario_novo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_comentarios);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setButtons();

		};

	
	//Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(TelaComentarios.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentarios, menu);
		return true;
	}
	
	private void setButtons() {
		button = (Button) findViewById(R.id.button_comentario_listar);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novoComentarioIntent = new Intent();
				novoComentarioIntent.putExtra("usuario", usuario);
				novoComentarioIntent.setClass(TelaComentarios.this, ListaComentarios.class);
				startActivity(novoComentarioIntent);
				finish();
			}
		});
		
		comentario_novo = (Button) findViewById(R.id.button_comentario_novo);
		comentario_novo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent comentariosIntent = new Intent();
				comentariosIntent.putExtra("usuario", usuario);
				comentariosIntent.setClass(TelaComentarios.this,
						NovoComentario.class);
				startActivity(comentariosIntent);
				finish();
			}

		});
	}

}
