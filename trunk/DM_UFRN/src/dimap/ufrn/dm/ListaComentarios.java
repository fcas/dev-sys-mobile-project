package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListaComentarios extends Activity {
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_comentarios);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentarios, menu);
		return true;
	}
	
	private void setButtons() {
		button = (Button) findViewById(R.id.button_comentario_novo);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novoComentarioIntent = new Intent();
				novoComentarioIntent.setClass(ListaComentarios.this, NovoComentario.class);
				startActivity(novoComentarioIntent);
				finish();
			}
		});
	}

}