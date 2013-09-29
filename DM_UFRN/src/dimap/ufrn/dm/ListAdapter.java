package dimap.ufrn.dm;

import java.util.List;

import model.Comentarios;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private List<Comentarios> items;
	private Context context;
	private Comentarios comentario;

	public ListAdapter(Context context, int resource, List<Comentarios> items) {
		super();
		this.context = context;
		this.items = items;
	}

	public int getCount() {
		return items.size();
	}

	public String getItem(int position) {
		return items.get(position).getComment();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.activity_list_comment, null);

			comentario = new Comentarios();
			comentario.setAutor("Ninguem comentou");
			comentario.setComment("Nenhum comentario");

			v.setTag(comentario);

		}

		Comentarios p = items.get(position);

		if (p != null) {

			TextView tt = (TextView) v.findViewById(R.id.id);
			TextView tt1 = (TextView) v.findViewById(R.id.description);

			if (tt != null) {
				tt.setText(p.getAutor());
			}
			if (tt1 != null) {

				tt1.setText(p.getComment());
			}

		}

		return v;

	}

	private Context getContext() {
		return this.context;
	}

}
