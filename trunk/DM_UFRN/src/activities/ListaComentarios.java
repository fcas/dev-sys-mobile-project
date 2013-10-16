package activities;

import java.util.List;

import model.Comentarios;
import model.Usuario;
import adapters.ComentarioAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import dao.DAOComentario;
import dimap.ufrn.dm.R;

public class ListaComentarios extends Activity {

	private DAOComentario datasource;
	Usuario usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_comment);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		datasource = new DAOComentario(this);
		datasource.open();
		List<Comentarios> listaComentarios = datasource.getAllComments();
		datasource.close();

		ListView lv = (ListView) findViewById(R.id.list_Comentarios);
		ComentarioAdapter adapter = new ComentarioAdapter(this,
				listaComentarios, usuario);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

		Button button = (Button) findViewById(R.id.button_novo_comentario);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novaTarefaIntent = new Intent();
				novaTarefaIntent.putExtra("usuario", usuario);
				novaTarefaIntent.setClass(ListaComentarios.this,
						NovoComentario.class);
				startActivity(novaTarefaIntent);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ListaComentarios.this, TelaComentarios.class);
		startActivity(voltaIntent);
		finish();
	}

}
