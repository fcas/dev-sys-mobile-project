package activities;

//O bot�o voltar est� voltando para a lista de lugares

import model.Usuario;
import activities.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dao.DAOLugar;

public class NovoLugar extends Activity {

	Button button;
	Usuario usuario;
	EditText inputLabel;
	Button btnAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_lugar);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setButtons();
	}

	private void setButtons() {

		button = (Button) findViewById(R.id.button2);
		inputLabel = (EditText) findViewById(R.id.editText1);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Builder builder = new AlertDialog.Builder(NovoLugar.this);
				builder.setTitle("Sucesso");
				builder.setMessage("Lugar adicionado com sucesso");

				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent();
								intent.putExtra("usuario", usuario);

								String label = inputLabel.getText().toString();

								if (label.trim().length() > 0) {

									DAOLugar db = new DAOLugar(
											getApplicationContext());

									db.salvarLugar(label);
									;

									inputLabel.setText("");

									InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.hideSoftInputFromWindow(
											inputLabel.getWindowToken(), 0);

								} else {
									Toast.makeText(getApplicationContext(),
											"Por favor, informe um lugar",
											Toast.LENGTH_SHORT).show();
								}

								intent.setClass(NovoLugar.this,
										ListaLugares.class);
								startActivity(intent);
								finish();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});
	}

	// bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
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
