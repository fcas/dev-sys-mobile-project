package activities;

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Comentarios;
import model.Usuario;
import adapters.ComentarioAdapter;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import dao.DAOComentario;
import dao.DAOLugar;
import dimap.ufrn.dm.R;

public class ListaComentarios extends Activity implements OnItemSelectedListener {

	private DAOComentario datasource;
	Usuario usuario;
	private Spinner spinner;
	String label;
	ComentarioAdapter adapter;
	ListView lv;
	List<Comentarios> listaComentarios;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_comment);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		//startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		Log.w("INiciou", "Servico");
		if (mBound){
			try {
				//Log.w("Vai Chamar", "Lugares");
				mService.getLugares();
				
				mService.getComentarios();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		datasource = new DAOComentario(this);
		datasource.open();
		listaComentarios = datasource.getAllComments();
		
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(this);
		loadSpinnerData();
		lv = (ListView) findViewById(R.id.list_Comentarios);
		adapter = new ComentarioAdapter(this,
				listaComentarios, usuario, mService);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

		datasource.close();
		Button button = (Button) findViewById(R.id.button_novo_comentario);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novaTarefaIntent = new Intent();
				novaTarefaIntent.putExtra("usuario", usuario);
				novaTarefaIntent.setClass(ListaComentarios.this,
						NovoComentario.class);
				startActivity(novaTarefaIntent);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ListaComentarios.this, TelaComentarios.class);
		startActivity(voltaIntent);
		finish();
	}
    public ServicoConexao mService;
    boolean mBound = false;
    
	   @Override
	    protected void onStart() {
	        super.onStart();
	        // Bind to LocalService
	        Intent intent = new Intent(this, ServicoConexao.class);
	        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			//try {
				//mService.getLugares();
				//mService.getComentarios();
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			
			
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
	    
		private void loadSpinnerData() {
			DAOLugar db = new DAOLugar(getApplicationContext());
			db.open();
			List<String> lables = db.listarLugares();
			lables.add(0, "Qualquer");
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, lables);

			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			spinner.setAdapter(dataAdapter);
			db.close();
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			label = parent.getItemAtPosition(position).toString();
			Toast.makeText(parent.getContext(), "Voce selecionou: " + label,
					Toast.LENGTH_LONG).show();
			datasource.open();
			if(label.equals("Qualquer")){
				listaComentarios = datasource.getAllComments();
				adapter.atualizarLista(listaComentarios);
				adapter.notifyDataSetChanged();
				//lv.setAdapter(adapter);
			}else{

				listaComentarios = datasource.getComentariosByLugar(label);
				adapter.atualizarLista(listaComentarios);
				adapter.notifyDataSetChanged();
				//lv.setAdapter(adapter);
			}
			datasource.close();

			

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}


}
