package dimap.ufrn.dm;

//O botão voltar está voltando para a lista de lugares

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class NovoLugar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_lugar);
		setTitle("UFRN ON TOUCH");
	}
	
	//botão voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
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
