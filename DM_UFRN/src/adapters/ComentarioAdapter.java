package adapters;

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Comentarios;
import model.Usuario;
import activities.ListaComentarios;
import activities.UpdateComentario;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
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
	Usuario usuario;

	public ComentarioAdapter(Context context, List<Comentarios> itens, Usuario u, ServicoConexao service) {
		this.itens = itens;
		dao = new DAOComentario(context);
		this.context = context;
		this.usuario = u;
		mService = service;
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
			comentario.setComentario("Nenhum comentario");
			v.setTag(comentario);

		}

		final Comentarios p = itens.get(position);

		if (p != null) {
			TextView autor = (TextView) v.findViewById(R.id.id);
			TextView comentario = (TextView) v.findViewById(R.id.description);
			final ImageView img = (ImageView) v
					.findViewById(R.id.button_apagar_c);
			TextView lugar = (TextView) v.findViewById(R.id.lugar);		
			Log.d("Usuario " +usuario.getNome(), "Autor "+p.getAutor());
			if(usuario.getNome().equals(p.getAutor())){
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
									//dao.deleteComentarios(itens.get(position));
									try {
										((ListaComentarios)context).mService.deleteComentario(itens.get(position).getId());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dao.close();
									itens.remove(position);
									notifyDataSetChanged();
									dialog.dismiss();
								}
							});
					builder.setNegativeButton("Nao",
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
			}else{
				img.setVisibility(View.INVISIBLE);
			}
			//p.getAutor()
			
			final ImageView update = (ImageView) v
					.findViewById(R.id.button_editar_c);
			if(usuario.getNome().equals(p.getAutor())){
			update.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent updateIntent = new Intent();
					updateIntent.putExtra("comentario", p);
					updateIntent.putExtra("usuario", ((Activity) context)
							.getIntent().getSerializableExtra("usuario"));
					updateIntent.putExtra("comentarios", itens.get(position));
					updateIntent.setClass(context, UpdateComentario.class);
					context.startActivity(updateIntent);
					((Activity) context).finish();
				}
			});
			}else{
				update.setVisibility(View.INVISIBLE);
			}

			if (autor != null) {
				autor.setText(p.getAutor());
			}
			if (comentario != null) {
				comentario.setText(p.getComentario());
			}
			if (lugar != null) {
				if(p.getLugar()!= null){
					lugar.setText(p.getLugar().getNome());
				}
			}
		}

		return v;
	}

	private Context getContext() {
		return context;
	}
	
    ServicoConexao mService;
    boolean mBound = false;
    
}
