package activities;

//O bot�o voltar est� voltando para a tela principal

import java.util.List;

import model.Usuario;
import adapters.LugarAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import dao.DAOLugar;
import dimap.ufrn.dm.R;
public class ListaLugares extends Activity {
	private Button button;
	private DAOLugar daoLugar;
	Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_lugares);
		setTitle("UFRN ON TOUCH");
		setButtons();
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		// Log.w("NOME", usuario.getNome());
		daoLugar = new DAOLugar(this);
		daoLugar.open();

		List<String> listaLugares = daoLugar.listarLugares();
	    daoLugar.close();
	    
		ListView lv = (ListView)findViewById(R.id.list_lugares);
		LugarAdapter adapter = new LugarAdapter(this, listaLugares);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

	}

	// Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ListaLugares.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_lugares, menu);
		return true;
	}

	private void setButtons() {
		button = (Button) findViewById(R.id.button_lugar_novo);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novoLugarIntent = new Intent();
				novoLugarIntent.putExtra("usuario", usuario);
				novoLugarIntent.setClass(ListaLugares.this, NovoLugar.class);
				startActivity(novoLugarIntent);
				finish();
			}
		});
	}

}
