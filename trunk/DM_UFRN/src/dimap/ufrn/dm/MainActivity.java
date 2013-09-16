package dimap.ufrn.dm;

//O botão voltar está voltando para a tela de login

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button lugares;
	private Button comentarios;
	private Button tarefas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}
	
	//Botão voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.setClass(MainActivity.this, Login.class);
		startActivity(voltaIntent);
		finish();
	}

	private void setButtons() {
		
		lugares = (Button) findViewById(R.id.button_lugares);
		lugares.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent lugaresIntent = new Intent();
				lugaresIntent.setClass(MainActivity.this, ListaLugares.class);
				startActivity(lugaresIntent);
				finish();
			}
		});
		
		comentarios = (Button) findViewById(R.id.button_comentarios);
		comentarios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent comentariosIntent = new Intent();
				comentariosIntent.setClass(MainActivity.this, ListaComentarios.class);
				startActivity(comentariosIntent);
				finish();
			}
		});
		
		tarefas = (Button) findViewById(R.id.button_tarefas);
		tarefas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent tarefasIntent = new Intent();
				tarefasIntent.setClass(MainActivity.this, ListaTarefas.class);
				startActivity(tarefasIntent);
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
