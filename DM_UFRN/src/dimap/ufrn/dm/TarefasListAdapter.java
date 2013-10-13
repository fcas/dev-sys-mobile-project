package dimap.ufrn.dm;

import java.util.List;

import dao.DAOTarefa;

import model.Tarefas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class TarefasListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
    private List<Tarefas> itens;
    private Context context;
    DAOTarefa dao;
    private Tarefas tarefa;
    
    public TarefasListAdapter(Context context, List<Tarefas> itens) {
    	this.itens = itens;
    	dao = new DAOTarefa(context);
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
			v = vi.inflate(R.layout.item_tarefa, null);

			tarefa = new Tarefas();
			tarefa.setDescricao("Não há nenhuma tarefa");
			tarefa.setHorario("Não há nenhuma tarefa");
			tarefa.setData("Não há nenhuma tarefa");
			v.setTag(tarefa);

		}

		Tarefas p = itens.get(position);

		if (p != null) {

			TextView tarefa = (TextView) v.findViewById(R.id.tarefa);
			TextView data = (TextView) v.findViewById(R.id.data);
			TextView horario = (TextView) v.findViewById(R.id.horario);
			ImageView img = (ImageView)v.findViewById(R.id.button_apagar);
			img.setOnClickListener(new View.OnClickListener() {		
				@Override
				public void onClick(View v) {
					// 1. Instantiate an AlertDialog.Builder with its constructor
					AlertDialog.Builder builder = new AlertDialog.Builder(context);

					// 2. Chain together various setter methods to set the dialog characteristics
					builder.setMessage("Tem certeza que deseja apagar essa tarefa?");
					builder.setTitle("Aviso");
					
					builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   itens.remove(position);
							   dao.deleteTarefa(itens.get(position));
							   notifyDataSetChanged();
							   dialog.dismiss();
				           }
				       });
					builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				              dialog.dismiss();
				           }
				       });
					

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});
			
			if (tarefa != null) {
				tarefa.setText(p.getDescricao());
			}
			if (data != null) {
				data.setText(p.getData());
			}
			if (horario != null) {
				horario.setText(p.getHorario());
			}

		}

		return v;
	}


	private Context getContext() {
		return context;
	}

}
