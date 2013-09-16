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

public class NovoComentario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comentario);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}
	Button comentario;
	private void setButtons() {
		
		comentario = (Button) findViewById(R.id.button2);
		comentario.setOnClickListener(new View.OnClickListener() {
		
				@Override
				public void onClick(View view) {
					Builder builder = new AlertDialog.Builder(NovoComentario.this);  
			        builder.setTitle("Sucesso");  
			        builder.setMessage("Comentario adicionado com sucesso");  
			        
			        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			        	public void onClick(DialogInterface arg0, int arg1) {
			        		Intent intent = new Intent();
			        		intent.setClass(NovoComentario.this, Comentarios.class);
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

}
