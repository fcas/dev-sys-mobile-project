package dimap.ufrn.dm;

//O bot�o voltar est� voltando para a lista de tarefas...

import dao.DAOTarefa;
import model.Tarefas;
import model.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class NovaTarefa extends Activity implements OnDateSetListener{
	private DAOTarefa daoTarefa;
	private Button pronto;
	@SuppressWarnings("unused")
	private EditText tarefa_hora, tarefa_data, tarefa_local, tarefa_descricao;
	Usuario usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_tarefa);
		setTitle("UFRN ON TOUCH");
		daoTarefa = new DAOTarefa(this);
		daoTarefa.open();
		tarefa_hora = (EditText)findViewById(R.id.tarefa_hora);
		tarefa_data = (EditText)findViewById(R.id.tarefa_data);
		tarefa_descricao = (EditText)findViewById(R.id.tarefa_descricao);
		tarefa_local = (EditText)findViewById(R.id.tarefa_local);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
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
		pronto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Builder builder = new AlertDialog.Builder(NovaTarefa.this);  
		        builder.setTitle("Sucesso");  
		        builder.setMessage("Tarefa adicionada com sucesso");  
		        
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		        	public void onClick(DialogInterface arg0, int arg1) {
		        		Tarefas tarefa = new Tarefas();
		        		tarefa.setDescricao(tarefa_descricao.getText().toString());
		        		tarefa.setUsuario(usuario.getLogin());
		        		tarefa.setData(tarefa_data.getText().toString());
		        		tarefa.setHorario(tarefa_hora.getText().toString());
		        		tarefa.setLocal(tarefa_data.getText().toString());
		        		Log.d("Tabela tarefa", Tarefas.CREATE_TAREFA);
		        		daoTarefa.createTarefa(tarefa);
		        		
						Intent minhasTarefasIntent = new Intent();
						minhasTarefasIntent.putExtra("usuario", usuario);
						minhasTarefasIntent.setClass(NovaTarefa.this, ListaTarefas.class);
						startActivity(minhasTarefasIntent);
						finish();
		        	}
		        });

		        AlertDialog alert = builder.create();  
		        alert.show();
				
				//minhasTarefasIntent = getIntent();
				//Usuario usuario = (Usuario) minhasTarefasIntent.getSerializableExtra("usuario");

				//EditText editText = (EditText) findViewById(R.id.tarefa_local);
				//tarefas.setLocal(editText.getText().toString());
				//editText = (EditText) findViewById(R.id.tarefa_horario);
				//tarefas.setHorario(editText.getText().toString());
				//editText = (EditText) findViewById(R.id.tarefa_descricao);
				//tarefas.setDescricao(editText.getText().toString());

				//usuario.getTarefas().add(tarefas);

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
                hora.setText(String.valueOf(tp.getCurrentHour()+ ":"+tp.getCurrentMinute()));
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
                data.setText(String.valueOf(dp.getDayOfMonth()+"/"+mes+"/"+dp.getYear()));
                 
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

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		
	}
}	
