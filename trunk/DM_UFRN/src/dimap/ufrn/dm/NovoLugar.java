package dimap.ufrn.dm;

//O botão voltar está voltando para a lista de lugares

import android.os.Bundle;
import android.app.Activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class NovoLugar extends Activity {

	Button button;
	Usuario usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_lugar);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setButtons();
	}

	
	private void setButtons() {
		
		button = (Button) findViewById(R.id.button2);
		button .setOnClickListener(new View.OnClickListener() {
		
				@Override
				public void onClick(View view) {
					Builder builder = new AlertDialog.Builder(NovoLugar.this);  
			        builder.setTitle("Sucesso");  
			        builder.setMessage("Lugar adicionado com sucesso");  
			        
			        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			        	public void onClick(DialogInterface arg0, int arg1) {
			        		Intent intent = new Intent();
			        		intent.putExtra("usuario", usuario);
			        		intent.setClass(NovoLugar.this, ListaLugares.class);
							startActivity(intent);
							finish();
			        	}
			        });

			        AlertDialog alert = builder.create();  
			        alert.show();
					
				}
			});
		}

	
	//botão voltar...
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

}
