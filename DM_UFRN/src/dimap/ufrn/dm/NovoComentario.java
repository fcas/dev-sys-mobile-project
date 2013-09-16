package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class NovoComentario extends Activity {
	CheckBox comentario_anonimo;
	EditText descricao;
	Usuario usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comentario);
		setTitle("UFRN ON TOUCH");
		descricao = (EditText) findViewById(R.id.launch_codes);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		comentario_anonimo = (CheckBox) findViewById(R.id.checkBox1);
		setButtons();
	}
	Button comentario;
	private void setButtons() {
		
		comentario = (Button) findViewById(R.id.button_comentar);
		comentario.setOnClickListener(new View.OnClickListener() {
		
				@Override
				public void onClick(View view) {
					Builder builder = new AlertDialog.Builder(NovoComentario.this);  
			        builder.setTitle("Sucesso");  
			        builder.setMessage("Comentario adicionado com sucesso");  
			        
			        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			        	public void onClick(DialogInterface arg0, int arg1) {
			        		Intent intent = new Intent();
			        		Comentarios comentario = new Comentarios();
			        		if(comentario_anonimo.isChecked()){
			        			comentario.setAutor("Anonimo");
			        		}
			        		else{
			        			comentario.setAutor("Aluno 1");
			        		}
			        		comentario.setComentario(descricao.getText().toString());
			        		intent.putExtra("usuario", usuario);
			        		intent.putExtra("comentario", comentario);
			        		intent.setClass(NovoComentario.this, ListaComentarios.class);
							startActivity(intent);
							finish();
			        	}
			        });

			        AlertDialog alert = builder.create();  
			        alert.show();
					
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
		voltaIntent.setClass(NovoComentario.this, ListaComentarios.class);
		startActivity(voltaIntent);
		finish();
	}

}
