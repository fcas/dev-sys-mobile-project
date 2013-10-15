package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import dimap.ufrn.dm.R;
public class UsuarioPerfil extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_perfil);
		setTitle("UFRN ON TOUCH");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usuario_perfil, menu);
		return true;
	}

}
