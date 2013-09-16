package dimap.ufrn.dm;

//O botão voltar está voltando para a tela principal

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListaComentarios extends ListActivity {
	private Button button;
	Usuario usuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_comentarios);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setButtons();

		};

	
	//Botão voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ListaComentarios.this, MainActivity.class);
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
				novoComentarioIntent.setClass(ListaComentarios.this, ComentariosTodos.class);
				startActivity(novoComentarioIntent);
				finish();
			}
		});
	}

}
