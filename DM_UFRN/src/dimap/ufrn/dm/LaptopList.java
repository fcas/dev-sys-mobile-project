package dimap.ufrn.dm;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LaptopList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laptop_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.laptop_list, menu);
		return true;
	}

}
