package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ComentariosTodos extends ListActivity {

	String[] comentarios = new String[] {};
	Usuario usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		
		// no more this
		// setContentView(R.layout.list_fruit);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_list_comment,
				usuario.getComentarios().toArray(comentarios)));

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
