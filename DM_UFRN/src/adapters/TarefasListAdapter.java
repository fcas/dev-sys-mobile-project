package adapters;

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Lugar;
import model.Tarefas;
import activities.DataCalculos;
import activities.ListaTarefas;
import activities.UpdateTarefa;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dao.DAOTarefa;
import dimap.ufrn.dm.R;
import excecoes.ConexaoException;


public class TarefasListAdapter extends BaseAdapter{
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


	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.item_tarefa, null);

			tarefa = new Tarefas();
			tarefa.setDescricao("N�o h� nenhuma tarefa");
			tarefa.setHorario("N�o h� nenhuma tarefa");
			tarefa.setData("N�o h� nenhuma tarefa");
			tarefa.setLugar(new Lugar());
			v.setTag(tarefa);

		}

		Tarefas p = itens.get(position);

		if (p != null) {
			if(p.getData().length() < 5){
				v.setBackgroundColor(Color.TRANSPARENT);
			}else if(DataCalculos.taAtrasada(p.getData()) < 0){
				v.setBackgroundColor(Color.RED);
			}else if(DataCalculos.taAtrasada(p.getData()) == 0){
				v.setBackgroundColor(Color.YELLOW);
			}else{
				v.setBackgroundColor(Color.TRANSPARENT);
			}
			TextView tarefa = (TextView) v.findViewById(R.id.tarefa);
			TextView data = (TextView) v.findViewById(R.id.data);
			TextView horario = (TextView) v.findViewById(R.id.horario);
			TextView lugar = (TextView) v.findViewById(R.id.lugar);
			final ImageView img = (ImageView)v.findViewById(R.id.button_apagar);
			img.setOnClickListener(new View.OnClickListener() {		
				@Override
				public void onClick(View v) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(context);

					builder.setMessage("Tem certeza que deseja apagar essa tarefa?");
					builder.setTitle("Aviso");
					
					builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   Log.d("IDTAREFA-Delete", String.valueOf(itens.get(position).getId()));
				        	    
							   try {
								   
								   ((ListaTarefas)context).mService.deleteTarefa(itens.get(position).getId());
									itens.remove(position);
								    notifyDataSetChanged();
								} catch (ConexaoException e) {
									AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
									builder2.setTitle("Erro");  
									builder2.setMessage("Nao foi possivel conectar a internet ou o servidor esta offline");  
									builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
							        	public void onClick(DialogInterface arg0, int arg1) {
													arg0.dismiss();
							        	}
							        });
									AlertDialog dialog2 = builder2.create();
									dialog2.show();
									   
								}
							  
							   dialog.dismiss();
				           }
				       });
					builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				              dialog.dismiss();
				           }
				       });
					

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});
			
			
			final ImageView update = (ImageView)v.findViewById(R.id.button_editar);
			update.setOnClickListener(new View.OnClickListener() {		
				@Override
				public void onClick(View v) {
					Intent updateIntent = new Intent();
	        	    updateIntent.putExtra("usuario", ((Activity)context).getIntent().getSerializableExtra("usuario"));
	        	    Log.d("IDTAREFA-Update", String.valueOf(itens.get(position).getId()));
	        	    updateIntent.putExtra("tarefa", itens.get(position));
	        	    updateIntent.setClass(context, UpdateTarefa.class);
		   			context.startActivity(updateIntent);
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
			if (lugar != null) {
				if(p.getLugar() != null){
					lugar.setText(p.getLugar().getNome());
				}
			}

		}

		return v;
	}


	private Context getContext() {
		return context;
	}


	public void mostrarTudo(String userLogin) {
		dao.open();
		itens = dao.getTasksByUser(userLogin);
		dao.close();
		notifyDataSetChanged();		
	}
	
	public void mostrarFuturas(String userLogin) {
		dao.open();
		itens = dao.getFutureTasksByUser(userLogin);
		dao.close();
		notifyDataSetChanged();
		
	}
}
