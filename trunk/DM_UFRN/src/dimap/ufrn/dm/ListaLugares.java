package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListaLugares extends Activity {
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_lugares);
		setTitle("UFRN ON TOUCH");
		setButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_lugares, menu);
		return true;
	}

	private void setButtons() {
		button = (Button) findViewById(R.id.button_lugar_novo);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent novoLugarIntent = new Intent();
				novoLugarIntent.setClass(ListaLugares.this, NovoLugar.class);
				startActivity(novoLugarIntent);
				finish();
			}
		});
	}

}
