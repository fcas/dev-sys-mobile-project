package dimap.ufrn.dm;

import java.util.List;

import model.Tarefas;

import android.content.Context;
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
    private Tarefas tarefa;

    public TarefasListAdapter(Context context, List<Tarefas> itens) {
    	this.itens = itens;
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
					itens.remove(position);
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
