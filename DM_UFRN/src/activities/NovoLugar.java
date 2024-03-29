package activities;

//O bot�o voltar est� voltando para a lista de lugares

import java.io.IOException;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;
import model.Lugar;
import model.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dao.DAOLugar;
import dimap.ufrn.dm.R;
import excecoes.ConexaoException;
public class NovoLugar extends Activity {

	Button button;
	Usuario usuario;
	EditText inputLabel;
	Button btnAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_lugar);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");



		//startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		setButtons();
	}

	private void setButtons() {

		button = (Button) findViewById(R.id.button2);
		inputLabel = (EditText) findViewById(R.id.editText1);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Builder builder = new AlertDialog.Builder(NovoLugar.this);


				final Intent intent = new Intent();
				intent.putExtra("usuario", usuario);

				String label = inputLabel.getText().toString();

				if (label.trim().length() > 0) {

					DAOLugar db = new DAOLugar(
							getApplicationContext());
					try {
						Lugar l = new Lugar();
						l.setNome(label);
						String result = mService.insertLugar(l);
						mService.getLugares();
						Log.w("ID RETORNADO", result);
						if(result.equals("0")){
							builder.setTitle("Erro");
							builder.setMessage("Lugar ja existe");
							builder.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0, int arg1) {
											arg0.dismiss();
										}
									});
						}else{
							builder.setTitle("Sucesso");
							builder.setMessage("Lugar adicionado com sucesso");
							builder.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0, int arg1) {
																			intent.setClass(NovoLugar.this,
													ListaLugares.class);
											startActivity(intent);
											finish();
										}
									});
						}
						//db.salvarLugar(label);
					} catch (ConexaoException e) {
						builder.setTitle("Erro");
						builder.setMessage("Nao foi possivel conectar a internet ou o servidor esta offline");
						builder.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0, int arg1) {
										arg0.dismiss();
									}
								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					inputLabel.setText("");

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							inputLabel.getWindowToken(), 0);

				} else {
					Toast.makeText(getApplicationContext(),
							"Por favor, informe um lugar",
							Toast.LENGTH_SHORT).show();
				}




				AlertDialog alert = builder.create();
				alert.show();

			}
		});
	}

	// bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(NovoLugar.this, ListaLugares.class);
		startActivity(voltaIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novo_lugar, menu);
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
