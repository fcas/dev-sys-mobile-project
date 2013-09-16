package dimap.ufrn.dm;

//O botão voltar está voltando para a tela principal

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListaTarefas extends Activity {
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefas);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}
	
	//Botão voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.setClass(ListaTarefas.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarefas, menu);
		return true;
	}
	
	private void setButtons() {
		button = (Button) findViewById(R.id.button_tarefa_nova);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novaTarefaIntent = new Intent();
				novaTarefaIntent.setClass(ListaTarefas.this, NovaTarefa.class);
				startActivity(novaTarefaIntent);
				finish();
			}
		});
	}
}
