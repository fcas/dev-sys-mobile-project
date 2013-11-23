package activities;

//O bot�o voltar est� voltando para a tela principal

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Usuario;
import adapters.LugarAdapter;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import dao.DAOLugar;
import dimap.ufrn.dm.R;
public class ListaLugares extends Activity {
	private Button button;
	private DAOLugar daoLugar;
	Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_lugares);
		setTitle("UFRN ON TOUCH");
		setButtons();
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		// Log.w("NOME", usuario.getNome());
		startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		if (mBound){
			
				try {
					mService.getLugares();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		} 
		daoLugar = new DAOLugar(this);
		daoLugar.open();

		List<String> listaLugares = daoLugar.listarLugares();
	    daoLugar.close();
	    
		ListView lv = (ListView)findViewById(R.id.list_lugares);
		LugarAdapter adapter = new LugarAdapter(this, listaLugares);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

	}

	// Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ListaLugares.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_lugares, menu);
		return true;
	}

	private void setButtons() {
		button = (Button) findViewById(R.id.button_lugar_novo);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novoLugarIntent = new Intent();
				novoLugarIntent.putExtra("usuario", usuario);
				novoLugarIntent.setClass(ListaLugares.this, NovoLugar.class);
				startActivity(novoLugarIntent);
				finish();
			}
		});
	}
    ServicoConexao mService;
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
