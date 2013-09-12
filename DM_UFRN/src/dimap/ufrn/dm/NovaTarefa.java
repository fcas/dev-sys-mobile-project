package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NovaTarefa extends Activity {

	private Button pronto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_tarefa);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}

	private void setButtons() {

		pronto = (Button) findViewById(R.id.button_tarefa_nova);
		pronto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Tarefas tarefas = new Tarefas();
				Intent minhasTarefasIntent = new Intent();

				EditText editText = (EditText) findViewById(R.id.tarefa_local);
				tarefas.setLocal(editText.getText().toString());
				editText = (EditText) findViewById(R.id.tarefa_horario);
				tarefas.setHorario(editText.getText().toString());
				editText = (EditText) findViewById(R.id.tarefa_descricao);
				tarefas.setDescricao(editText.getText().toString());

				minhasTarefasIntent.setClass(NovaTarefa.this,
						ListaTarefas.class);
				startActivity(minhasTarefasIntent);

				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarefa, menu);
		return true;
	}

}
