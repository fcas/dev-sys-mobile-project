package activities;
//O bot�o voltar est� voltando para a tela de login

import java.io.IOException;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;
import dao.DAOImagem;
import dimap.ufrn.dm.R;
import model.Usuario;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button lugares;
	private Button comentarios;
	private Button tarefas;
	private Button button_logoff;
	private Button button_sair;
	private ImageButton profile;
	private DAOImagem daoImagem;
	Usuario usuario;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("UFRN ON TOUCH");
		TextView nome = (TextView) findViewById(R.id.textView1);
		TextView curso = (TextView) findViewById(R.id.textView2);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		nome.setText(usuario.getNome());
		curso.setText(usuario.getCurso());
		daoImagem = new DAOImagem();
		setButtons();
	
	}
	
	@Override
	public void onBackPressed() {
		SharedPreferences settings = getSharedPreferences("login", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("ManterAutenticado", false);
		editor.commit();
		Log.d("ManterAutenticado-Main", String.valueOf(settings.getBoolean("ManterAutenticado", false)));
		Intent voltaIntent = new Intent();
		voltaIntent.setClass(MainActivity.this, Login.class);
		startActivity(voltaIntent);
		finish();
		
	}

	private void setButtons() {
		
		profile = (ImageButton) findViewById(R.id.trocaImagem);
		Bitmap imgPerfil = daoImagem.getImagem(usuario.getLogin());
		if(imgPerfil == null){
			profile.setImageResource(R.drawable.bomb);
		}else{
			profile.setImageBitmap(imgPerfil);	 
		}
		profile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent ProfileIntent = new Intent();
				ProfileIntent.putExtra("usuario", usuario);
				ProfileIntent.setClass(MainActivity.this, ProfileEdit.class);
				startActivity(ProfileIntent);
				finish();
			}
		});
		
		
		lugares = (Button) findViewById(R.id.button_lugares);
		lugares.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				try {
					mService.getLugares();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent lugaresIntent = new Intent();
				lugaresIntent.putExtra("usuario", usuario);
				lugaresIntent.setClass(MainActivity.this, ListaLugares.class);
				startActivity(lugaresIntent);
				finish();
			}
		});
		
		comentarios = (Button) findViewById(R.id.button_comentarios);
		comentarios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent comentariosIntent = new Intent();
				comentariosIntent.putExtra("usuario", usuario);
				comentariosIntent.setClass(MainActivity.this, TelaComentarios.class);
				startActivity(comentariosIntent);
				finish();
			}
		});
		
		tarefas = (Button) findViewById(R.id.button_tarefas);
		tarefas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				try {
					mService.getTarefas();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent tarefasIntent = new Intent();
				tarefasIntent.putExtra("usuario", usuario);
				tarefasIntent.setClass(MainActivity.this, ListaTarefas.class);
				startActivity(tarefasIntent);
				finish();
			}
		});
		
		button_logoff = (Button) findViewById(R.id.button_logoff);
		button_logoff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent loginIntent = new Intent();
				SharedPreferences settings = getSharedPreferences("login", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("ManterAutenticado", false);
				editor.commit();
				loginIntent.setClass(MainActivity.this, Login.class);
				startActivity(loginIntent);
				finish();
			}
		});
		
		button_sair = (Button) findViewById(R.id.button_sair);
		button_sair.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences settings = getSharedPreferences("login", Activity.MODE_PRIVATE);
				Log.d("ManterAutenticado-Main-Sair", String.valueOf(settings.getBoolean("ManterAutenticado", false)));
				finish();
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
