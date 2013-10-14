package adapters;

import java.util.List;

import model.Lugar;
import activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dao.DAOLugar;

public class LugarAdapter extends BaseAdapter {

	private List<String> itens;
	private Context context;
	DAOLugar dao;
	private Lugar lugar;

	public LugarAdapter(Context context, List<String> itens) {
		this.itens = itens;
		dao = new DAOLugar(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.item_lugar, null);

			lugar = new Lugar();
			lugar.setNome("Não há nenhum lugar");
			v.setTag(lugar);

		}

		String p = itens.get(position);

		if (p != null) {

			TextView lugar = (TextView) v.findViewById(R.id.textView1);

			if (lugar != null) {
				lugar.setText(p);
			}
		}

		return v;
	}

	private Context getContext() {
		return context;
	}

}
