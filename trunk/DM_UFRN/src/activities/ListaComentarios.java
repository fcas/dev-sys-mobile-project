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
import android.widget.Button;
import android.widget.ListView;
import dao.DAOComentario;
import dimap.ufrn.dm.R;

public class ListaComentarios extends Activity {

	private DAOComentario datasource;
	Usuario usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_comment);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		startService(new Intent("INICIAR_SERVICO_CONEXAO"));
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
		List<Comentarios> listaComentarios = datasource.getAllComments();
		datasource.close();

		ListView lv = (ListView) findViewById(R.id.list_Comentarios);
		ComentarioAdapter adapter = new ComentarioAdapter(this,
				listaComentarios, usuario);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

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
    ServicoConexao mService;
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
	    

}
