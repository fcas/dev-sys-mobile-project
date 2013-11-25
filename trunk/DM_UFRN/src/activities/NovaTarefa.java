package activities;

//O bot�o voltar est� voltando para a lista de tarefas...

import java.io.IOException;
import java.util.List;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;
import dimap.ufrn.dm.R;
import model.Lugar;
import model.Tarefas;
import model.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import dao.DAOLugar;
import dao.DAOTarefa;
import excecoes.ConexaoException;

public class NovaTarefa extends Activity implements OnItemSelectedListener{
	private DAOTarefa daoTarefa;
	private DAOLugar daoLugar;
	private Button pronto;
	String label;
	private Spinner spinner;
		
	@SuppressWarnings("unused")
	private EditText tarefa_hora, tarefa_data, tarefa_local, tarefa_descricao, input_label;
	Usuario usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_tarefa);
		setTitle("UFRN ON TOUCH");
		//startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		//if (mBound){
			//try {
				//mService.getLugares();
				//mService.getComentarios();
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
		//}
		daoTarefa = new DAOTarefa(this);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(this);
		tarefa_hora = (EditText)findViewById(R.id.tarefa_hora);
		tarefa_data = (EditText)findViewById(R.id.tarefa_data);
		tarefa_descricao = (EditText)findViewById(R.id.tarefa_descricao);
		input_label = (EditText)findViewById(R.id.input_label);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		loadSpinnerData();
		setButtons();	
	}
	
	//bot�o voltar 
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(NovaTarefa.this, ListaTarefas.class);
		startActivity(voltaIntent);
		finish();
	}

	private void setButtons() {

		pronto = (Button) findViewById(R.id.button_tarefa_nova);
		daoLugar = new DAOLugar(getApplicationContext());
		pronto.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View view) {
				Builder builder = new AlertDialog.Builder(NovaTarefa.this);  

				Tarefas tarefa = new Tarefas();
        		tarefa.setDescricao(tarefa_descricao.getText().toString());
        		tarefa.setUsuario(usuario.getLogin());
        		tarefa.setData(tarefa_data.getText().toString());
        		tarefa.setHorario(tarefa_hora.getText().toString());
        		tarefa.setLugar(new Lugar(daoLugar.idLugar(label)));
        		//tarefa.setLocal(tarefa_data.getText().toString());
        		Log.d("Tabela tarefa", Tarefas.CREATE_TAREFA);
        		try {
					String result = mService.insertTarefa(tarefa);
					mService.getTarefas();
					if(!result.equals("0")){
						builder.setTitle("Sucesso");
						builder.setMessage("Tarefa cadastrada com sucesso");
				        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				        	public void onClick(DialogInterface arg0, int arg1) {
				        		
								Intent minhasTarefasIntent = new Intent();
								minhasTarefasIntent.putExtra("usuario", usuario);
								minhasTarefasIntent.setClass(NovaTarefa.this, ListaTarefas.class);
								startActivity(minhasTarefasIntent);
								finish();
				        	}
				        });
					}else{
						builder.setTitle("Erro");
						builder.setMessage("Nao foi possivel cadastrar a tarefa. Verifique se os campos foram preenchidos corretamente");
				        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				        	public void onClick(DialogInterface arg0, int arg1) {
				        		arg0.dismiss();
				        	}
				        });
					}

					
				} catch (ConexaoException e) {
					builder.setTitle("Erro");
					builder.setMessage("Nao foi possivel conectar a internet ou o servidor esta offline");
			        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			        	public void onClick(DialogInterface arg0, int arg1) {
			        		arg0.dismiss();
			        	}
			        });
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        AlertDialog alert = builder.create();  
		        alert.show();
			}
		});
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarefa, menu);
		return true;
	}

	
	public void showTimeDialog(View v) {
		
		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialog_time);
		final TimePicker tp = (TimePicker)dialog.findViewById(R.id.timePicker1);
		final EditText hora = (EditText) findViewById(R.id.tarefa_hora);
		Button confirmar = (Button) dialog.findViewById(R.id.buttonOk);
        Button cancelar = (Button) dialog.findViewById(R.id.buttonCancel);
 
		confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hora.setText(String.valueOf(DataCalculos.normalizarHora(tp.getCurrentHour(), tp.getCurrentMinute())));
             //finaliza o dialog
             dialog.dismiss();
            }
        });
 
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             //finaliza o dialog
                dialog.dismiss();
            }
        });
         
        
		dialog.setTitle("Escolha a data");
		dialog.show();
	}
	
	public void showDateDialog(View v) {
		
		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialog_date);
		final DatePicker dp = (DatePicker)dialog.findViewById(R.id.datePicker1);
		final EditText data = (EditText) findViewById(R.id.tarefa_data);
		Button confirmar = (Button) dialog.findViewById(R.id.buttonOk);
        Button cancelar = (Button) dialog.findViewById(R.id.buttonCancel);
 
		confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	int mes = dp.getMonth() + 1;
            	String dataNormal = DataCalculos.normalizarData(dp.getDayOfMonth(), mes, dp.getYear());
                Log.d("Data-Banco-NovaTarefa", dataNormal);
            	data.setText(DataCalculos.bancoToVisao(dataNormal));
                 
             //finaliza o dialog
             dialog.dismiss();
            }
        });
 
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             //finaliza o dialog
                dialog.dismiss();
            }
        });
         
        
		dialog.setTitle("Escolha a data");
		dialog.show();
		
		
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

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		label = parent.getItemAtPosition(position).toString();

		Toast.makeText(parent.getContext(), "Voce selecionou: " + label,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
