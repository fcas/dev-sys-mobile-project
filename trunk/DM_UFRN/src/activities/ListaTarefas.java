package activities;

//O bot�o voltar est� voltando para a tela principal

import java.util.List;

import model.Tarefas;
import model.Usuario;
import adapters.TarefasListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import dao.DAOTarefa;
import dimap.ufrn.dm.R;

public class ListaTarefas extends Activity {
	private Button button;
	Usuario usuario;
	DAOTarefa daoTarefa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefas);
		setTitle("UFRN ON TOUCH");
		setButtons();
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		daoTarefa = new DAOTarefa(this);
		daoTarefa.open();

	    List<Tarefas> listaTarefas = daoTarefa.getTasksByUser(usuario.getLogin());
	    //List<Tarefas> listaTarefas = daoTarefa.getAllTasks();
	    daoTarefa.close();
	    
		ListView lv = (ListView)findViewById(R.id.list_tarefas);
		TarefasListAdapter adapter = new TarefasListAdapter(this, listaTarefas);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
	}
	
	//Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.setClass(ListaTarefas.this, MainActivity.class);
		voltaIntent.putExtra("usuario", usuario);
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
				novaTarefaIntent.putExtra("usuario", usuario);
				novaTarefaIntent.setClass(ListaTarefas.this, NovaTarefa.class);
				startActivity(novaTarefaIntent);
				finish();
			}
		});
	}
}
