package activities;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Menu;
import dimap.ufrn.dm.R;
public class SplashActivity extends Activity implements Runnable {
	

    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Handler handler = new Handler();
		handler.postDelayed(this, 3000);
		
		//mService.insertUsuario(u);
	}

	public void run() {
		startActivity(new Intent(this, Login.class));
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	

}