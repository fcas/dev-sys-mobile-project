package activities;

import java.util.List;

import model.Comentarios;
import model.Usuario;
import adapters.ListAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import dao.DAOComentario;
import dimap.ufrn.dm.R;
public class ListaComentarios extends ListActivity {

	  private DAOComentario datasource;
	  Usuario usuario;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		datasource = new DAOComentario(this);
	    datasource.open();

	    List<Comentarios> listComentarios = datasource.getAllComments();

		ListView lv = getListView();
		ListAdapter adapter = new ListAdapter(this, R.layout.activity_list_comment, listComentarios);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Retrieve our class object and use index to resolve item
				// tapped

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
