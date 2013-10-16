package activities;

import java.util.List;

import model.Comentarios;
import model.Usuario;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import dao.DAOComentario;
import dao.DAOLugar;
import dimap.ufrn.dm.R;

public class UpdateComentario extends Activity implements
		OnItemSelectedListener {
	private DAOComentario datasource;
	private CheckBox comentario_anonimo;
	private EditText descricao;
	private Usuario usuario;
	private Spinner spinner;
	private Button btnAdd;
	private EditText inputLabel;
	DAOLugar db;
	String label;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comentario);
		setTitle("UFRN ON TOUCH");
		descricao = (EditText) findViewById(R.id.launch_codes);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		comentario_anonimo = (CheckBox) findViewById(R.id.checkBox1);
		spinner = (Spinner) findViewById(R.id.spinner);
		btnAdd = (Button) findViewById(R.id.btn_add);
		inputLabel = (EditText) findViewById(R.id.input_label);
		spinner.setOnItemSelectedListener(this);
		datasource = new DAOComentario(this);
		datasource.open();
		loadSpinnerData();
		setButtons();
	}

	Button comentario;

	private void setButtons() {
		db = new DAOLugar(getApplicationContext());
		comentario = (Button) findViewById(R.id.button_comentar);
		comentario.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				label = inputLabel.getText().toString();
				Builder builder = new AlertDialog.Builder(UpdateComentario.this);
				builder.setTitle("Sucesso");
				builder.setMessage("Comentario atualizado com sucesso");

				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent();
								Comentarios comentario = new Comentarios();
								if (comentario_anonimo.isChecked()) {
									comentario.setAutor("Anonimo");
								} else {
									comentario.setAutor(usuario.getNome()
											.toString());
								}
								comentario.setComment(descricao.getText()
										.toString());
								comentario.getLugar().setId_local(
										(db.idLugar(label)));
								usuario.getComentarios().add(comentario);
								// ContentValues values =
								// toContentValue(comentario);
								datasource.open();
								datasource.updateComentario(comentario);
								datasource.close();
								intent.putExtra("usuario", usuario);
								intent.setClass(UpdateComentario.this,
										TelaComentarios.class);
								startActivity(intent);
								finish();
							}

						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				label = inputLabel.getText().toString();
				if (label.trim().length() > 0) {
					db.open();
					db.salvarLugar(label);
					inputLabel.setText("");
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);
					loadSpinnerData();
					db.close();
				} else {
					Toast.makeText(getApplicationContext(),
							"Por favor, insira um nome para lugar", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentario_novo, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(UpdateComentario.this, TelaComentarios.class);
		startActivity(voltaIntent);
		finish();
	}

	private void loadSpinnerData() {
		DAOLugar db = new DAOLugar(getApplicationContext());
		db.open();
		List<String> lables = db.listarLugares();

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(dataAdapter);
		db.close();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String label = parent.getItemAtPosition(position).toString();

		Toast.makeText(parent.getContext(), "Voce selecionou: " + label,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

}
