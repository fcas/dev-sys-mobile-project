package activities;

//O bot�o voltar est� voltando para a tela principal

import java.io.IOException;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Usuario;
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
import dimap.ufrn.dm.R;
public class TelaComentarios extends Activity {
	private Button button;
	Usuario usuario;
	private Button comentario_novo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_comentarios);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setButtons();

		};

	
	//Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(TelaComentarios.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentarios, menu);
		return true;
	}
	
	private void setButtons() {
		button = (Button) findViewById(R.id.button_comentario_listar);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novoComentarioIntent = new Intent();
				try {
					mService.getComentarios();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				novoComentarioIntent.putExtra("usuario", usuario);
				novoComentarioIntent.setClass(TelaComentarios.this, ListaComentarios.class);
				startActivity(novoComentarioIntent);
				finish();
			}
		});
		
		comentario_novo = (Button) findViewById(R.id.button_comentario_novo);
		comentario_novo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent comentariosIntent = new Intent();
				try {
					mService.getComentarios();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				comentariosIntent.putExtra("usuario", usuario);
				comentariosIntent.setClass(TelaComentarios.this,
						NovoComentario.class);
				startActivity(comentariosIntent);
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
