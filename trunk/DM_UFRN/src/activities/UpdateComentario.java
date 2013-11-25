package activities;

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;

import model.Comentarios;
import model.Tarefas;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import dao.DAOComentario;
import dao.DAOLugar;
import dimap.ufrn.dm.R;
import excecoes.ConexaoException;

public class UpdateComentario extends Activity implements
		OnItemSelectedListener {
	private DAOComentario datasource;
	private CheckBox comentario_anonimo;
	private EditText descricao;
	private Usuario usuario;
	private Spinner spinner;
	private Button btnAdd;
	private EditText inputLabel;
	private Comentarios com;
	DAOLugar db;
	String label;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comentario);
		setTitle("UFRN ON TOUCH");
		descricao = (EditText) findViewById(R.id.launch_codes);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		comentario_anonimo = (CheckBox) findViewById(R.id.checkBox1);
		spinner = (Spinner) findViewById(R.id.spinner);
		btnAdd = (Button) findViewById(R.id.btnadd);
		inputLabel = (EditText) findViewById(R.id.input_label);
		
		com = (Comentarios) getIntent().getSerializableExtra("comentario");
		descricao.setText(com.getComentario());
		//usuario.setText(com.getAutor());
		//tarefa_descricao.setText(t.getDescricao());
		
		spinner.setOnItemSelectedListener(this);
		datasource = new DAOComentario(this);
		datasource.open();
		loadSpinnerData();
		setButtons();
	}

	Button comentario;

	private void setButtons() {
		db = new DAOLugar(getApplicationContext());
		comentario = (Button) findViewById(R.id.button_comentar);
		comentario.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				//label = inputLabel.getText().toString();
				Builder builder = new AlertDialog.Builder(UpdateComentario.this);
				

				if (comentario_anonimo.isChecked()) {
					com.setAutor("Anonimo");
				} else {
					com.setAutor(usuario.getNome()
							.toString());
				}
				com.setComentario(descricao.getText()
						.toString());
				Log.w("ID_LOCAL - LOCAL", String.valueOf(db.idLugar(label))+"-"+label);
				com.getLugar().setId_local(
						(db.idLugar(label)));
				try {
					mService.updateComentario(com);
					builder.setTitle("Sucesso");
					builder.setMessage("Comentario atualizado com sucesso");
					builder.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									Intent intent = new Intent();
																	intent.putExtra("usuario", usuario);
									intent.setClass(UpdateComentario.this,
											TelaComentarios.class);
									startActivity(intent);
									finish();
								}

							});
				} catch (ConexaoException e) {
					builder.setTitle("Erro");
					builder.setMessage("Nao foi possivel se conectar com a internet ou o servidor esta offline");
					e.printStackTrace();
					builder.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									arg0.dismiss();
								}

							});
				}
				//datasource.updateComentario(comentario);
				//datasource.close();

				




				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				label = inputLabel.getText().toString();
				if (label.trim().length() > 0) {
					db.open();
					//db.salvarLugar(label);
					inputLabel.setText("");
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);
					loadSpinnerData();
					db.close();
				} else {
					Toast.makeText(getApplicationContext(),
							"Por favor, insira um nome para lugar", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentario_novo, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(UpdateComentario.this, TelaComentarios.class);
		startActivity(voltaIntent);
		finish();
	}

	private void loadSpinnerData() {
		DAOLugar db = new DAOLugar(getApplicationContext());
		db.open();
		List<String> lables = db.listarLugares();

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

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
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
