package dimap.ufrn.dm;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ComentariosTodos extends ListActivity {

	List<String> comentarios = new ArrayList<String>();
	Usuario usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		// no more this
		// setContentView(R.layout.list_fruit);
		
		for (int i = 0; i < usuario.getComentarios().size(); i++) {
			comentarios.add(usuario.getComentarios().get(i).getComentario());
		}

		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.activity_list_comment,comentarios));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});

	}

}
