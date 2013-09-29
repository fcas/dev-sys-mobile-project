package dimap.ufrn.dm;

import java.util.List;

import model.Comentarios;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ComentariosTodos extends ListActivity {

	Comentarios comentarios;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("UFRN ON TOUCH");

		final List<Comentarios> listComentarios = comentarios.getComentarios(this);

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
		voltaIntent.putExtra("usuario", comentarios);
		voltaIntent.setClass(ComentariosTodos.this, ListaComentarios.class);
		startActivity(voltaIntent);
		finish();
	}

}
