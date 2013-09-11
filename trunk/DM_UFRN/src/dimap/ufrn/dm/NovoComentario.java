package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NovoComentario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comentario);
		setTitle("UFRN ON TOUCH");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentario_novo, menu);
		return true;
	}

}
