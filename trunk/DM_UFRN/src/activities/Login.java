package activities;
import java.io.IOException;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;
import dimap.ufrn.dm.R;
import model.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import dao.DAOUsuario;

public class Login extends Activity {
	
	Button login;
	Button cadastro; 
	EditText user, senha;
	CheckBox manter_autenticado;
	private DAOUsuario daoUsuario;
	ServicoConexao mService;
    boolean mBound = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("UFRN ON TOUCH");
		setButtons();
		user = (EditText)findViewById(R.id.usuario_nome);
		senha = (EditText)findViewById(R.id.usuario_senha);
		manter_autenticado = (CheckBox)findViewById(R.id.manter_autenticado);
		daoUsuario = new DAOUsuario(this);

		SharedPreferences settings = getSharedPreferences("login", Activity.MODE_PRIVATE);
		boolean autoSave = settings.getBoolean("ManterAutenticado", false);
		Log.d("ManterAutenticado-Login", String.valueOf(autoSave));
		String login = settings.getString("Login", "");
		//autoSave = false;
		
		startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		
		if (mBound){
			try {
				mService.getUsuarios();

				
			} catch (IOException e) {
				Log.w("Excecao","Excecao");
				e.printStackTrace();
			}
		}else{
			Log.w("Servico not bound","Servico not bound");
		}
		
		if(autoSave == true){
			manter_autenticado.setChecked(true);
			daoUsuario.open();
			Usuario usuario = daoUsuario.getUsuarioByLogin(login);
			daoUsuario.close();
			Intent mainIntent = new Intent();
			mainIntent.putExtra("usuario", usuario);
			mainIntent.setClass(Login.this,
					MainActivity.class);
			startActivity(mainIntent);
			finish();
		}
	}

	private void setButtons() {

		
		cadastro = (Button)findViewById(R.id.button_cadastrar);
		cadastro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent cadIntent = new Intent();
				cadIntent.setClass(Login.this,
						Cadastro.class);
				startActivity(cadIntent);

				finish();
			}
		});
		
		
		login = (Button) findViewById(R.id.button_fazerLogin);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
	
				Usuario usuario = new Usuario();
				Intent mainIntent = new Intent();

				daoUsuario.open();
				usuario = daoUsuario.autenticar(user.getText().toString(), (senha.getText().toString()));
				daoUsuario.close();
				
				if(usuario != null && login.getText() != null){
					
					SharedPreferences settings = getSharedPreferences("login", Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("ManterAutenticado", manter_autenticado.isChecked());
					editor.putString("Login", usuario.getLogin());
					editor.commit();
					
					mainIntent.putExtra("usuario", usuario);
					
					mainIntent.setClass(Login.this,
							MainActivity.class);
					startActivity(mainIntent);
					
					finish();
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
					builder.setTitle("Login ou Senha incorretos");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               dialog.dismiss();
					           }
					       });
					AlertDialog dialog = builder.create();
					dialog.show();
					
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		finish();
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
	    
		@Override
	    protected void onStart() {
	        super.onStart();
	        // Bind to LocalService
	        Intent intent = new Intent(this, ServicoConexao.class);
	        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	    }


}
