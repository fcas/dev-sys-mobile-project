package activities;
//O bot�o voltar est� voltando para a tela principal

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Tarefas;
import model.Usuario;
import adapters.TarefasListAdapter;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import dao.DAOTarefa;
import dimap.ufrn.dm.R;

public class ListaTarefas extends Activity {
	private Button button;
	Usuario usuario;
	DAOTarefa daoTarefa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefas);
		setTitle("UFRN ON TOUCH");
		setButtons();
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		//startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		if (mBound){
			try {
				Log.w("Lista Tarefa", "Servico Bound");
				mService.getLugares();
				mService.getTarefas();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		daoTarefa = new DAOTarefa(this);
		daoTarefa.open();

	    List<Tarefas> listaTarefas = daoTarefa.getFutureTasksByUser(usuario.getLogin());
	    //List<Tarefas> listaTarefas = daoTarefa.getAllTasks();
	    daoTarefa.close();
	    
		ListView lv = (ListView)findViewById(R.id.list_tarefas);
		final TarefasListAdapter adapter = new TarefasListAdapter(this, listaTarefas);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		
		final CheckBox checkTarefas = (CheckBox)findViewById(R.id.checkTarefas);
		checkTarefas.setSelected(true);
		checkTarefas.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
	            if (isChecked){
	                adapter.mostrarFuturas(usuario.getLogin());
	            }else{
	            	adapter.mostrarTudo(usuario.getLogin());
	            }
				
			}



	    });
	}
	
	//Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.setClass(ListaTarefas.this, MainActivity.class);
		voltaIntent.putExtra("usuario", usuario);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarefas, menu);
		return true;
	}
	
	private void setButtons() {
		button = (Button) findViewById(R.id.button_tarefa_nova);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novaTarefaIntent = new Intent();
				novaTarefaIntent.putExtra("usuario", usuario);
				novaTarefaIntent.setClass(ListaTarefas.this, NovaTarefa.class);
				startActivity(novaTarefaIntent);
				finish();
			}
		});
	}
    public ServicoConexao mService;
    boolean mBound = false;
    
	   @Override
	    protected void onStart() {
	        super.onStart();
	        // Bind to LocalService
	        Intent intent = new Intent(this, ServicoConexao.class);
	        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	    }

	    @Override
	    protected void onStop() {
	        super.onStop();
	        // Unbind from the service
	        if (mBound) {
	            unbindService(mConnection);
	            mBound = false;
	        }
	    }
	    /** Defines callbacks for service binding, passed to bindService() */
	    private ServiceConnection mConnection = new ServiceConnection() {

	        @Override
	        public void onServiceConnected(ComponentName className,
	                IBinder service) {
	            // We've bound to LocalService, cast the IBinder and get LocalService instance
	            LocalBinder binder = (LocalBinder) service;
	            mService = binder.getService();
	            mBound = true;
	        }

	        @Override
	        public void onServiceDisconnected(ComponentName arg0) {
	            mBound = false;
	           
	        }
	    };	    
	
}
