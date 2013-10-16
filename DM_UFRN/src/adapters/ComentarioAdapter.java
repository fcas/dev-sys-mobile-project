package adapters;

import java.util.List;

import model.Comentarios;
import activities.UpdateComentario;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dao.DAOComentario;
import dimap.ufrn.dm.R;

public class ComentarioAdapter extends BaseAdapter {

	private List<Comentarios> itens;
	private Context context;
	DAOComentario dao;
	private Comentarios comentario;

	public ComentarioAdapter(Context context, List<Comentarios> itens) {
		this.itens = itens;
		dao = new DAOComentario(context);
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
			v = vi.inflate(R.layout.item_comentario, null);

			comentario = new Comentarios();
			comentario.setAutor("Ninguem comentou");
			comentario.setComment("Nenhum comentario");
			v.setTag(comentario);

		}

		Comentarios p = itens.get(position);

		if (p != null) {

			TextView autor = (TextView) v.findViewById(R.id.id);
			TextView comentario = (TextView) v.findViewById(R.id.description);
			final ImageView img = (ImageView) v
					.findViewById(R.id.button_apagar_c);
			img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);

					builder.setMessage("Tem certeza que deseja apagar esse comentario?");
					builder.setTitle("Aviso");

					builder.setPositiveButton("Sim",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dao.open();
									dao.deleteComentarios(itens.get(position));
									dao.close();
									itens.remove(position);
									notifyDataSetChanged();
									dialog.dismiss();
								}
							});
					builder.setNegativeButton("N�o",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});

			final ImageView update = (ImageView) v
					.findViewById(R.id.button_editar_c);
			update.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent updateIntent = new Intent();
					updateIntent.putExtra("usuario", ((Activity) context)
							.getIntent().getSerializableExtra("usuario"));
					updateIntent.putExtra("comentarios", itens.get(position));
					updateIntent.setClass(context, UpdateComentario.class);
					context.startActivity(updateIntent);
				}
			});

			if (autor != null) {
				autor.setText(p.getAutor());
			}
			if (comentario != null) {
				comentario.setText(p.getComment());
			}
		}

		return v;
	}

	private Context getContext() {
		return context;
	}

}
