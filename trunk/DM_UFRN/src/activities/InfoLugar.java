package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import dimap.ufrn.dm.R;
public class InfoLugar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_lugar);
		setTitle("UFRN ON TOUCH");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info_lugar, menu);
		return true;
	}

}
