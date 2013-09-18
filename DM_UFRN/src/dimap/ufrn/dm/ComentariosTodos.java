package dimap.ufrn.dm;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ComentariosTodos extends ListActivity {

	Usuario usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("UFRN ON TOUCH");

		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		final List<Comentarios> comentarios = usuario.getComentarios();

		ListView lv = getListView();
		ListAdapterComentarios adapter = new ListAdapterComentarios(this, R.layout.activity_list_comment, comentarios);

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

}
