package dimap.ufrn.dm;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private List<Comentarios> items;
	private Context context;
	private int numItems = 0;

	public ListAdapter(Context context, int resource, List<Comentarios> items) {

		super();

		this.items = items;

	}

	public int getCount() {
		return numItems;
	}

	public Comentarios getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.activity_list_comment, null);

		}

		Comentarios p = items.get(position);

		if (p != null) {

			TextView tt = (TextView) v.findViewById(R.id.id);
			TextView tt1 = (TextView) v.findViewById(R.id.categoryId);

			if (tt != null) {
				tt.setText(p.getAutor());
			}
			if (tt1 != null) {

				tt1.setText(p.getComentario());
			}

		}

		return v;

	}

	private Context getContext() {
		return this.context;
	}

}
