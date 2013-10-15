package activities;

import model.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import dao.DAOImagem;
import dao.DAOUsuario;
import dimap.ufrn.dm.R;
public class ProfileEdit extends Activity {

	DAOUsuario daoUsuario;
	DAOImagem daoImagem;
	Usuario usuario;
	Button feito;
	ImageButton trocaImagem;
	EditText edit_nome, edit_sobre, edit_curso; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daoUsuario = new DAOUsuario(this);
		daoImagem = new DAOImagem();
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setContentView(R.layout.activity_profile_edit);
		
		edit_nome = (EditText) findViewById(R.id.edit_nome);
		edit_curso = (EditText) findViewById(R.id.edit_curso);
		edit_sobre = (EditText) findViewById(R.id.edit_sobre);
		
		edit_nome.setText(usuario.getNome());
		edit_curso.setText(usuario.getCurso());
		edit_sobre.setText(usuario.getSobreMim());

		
		setButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_edit, menu);
		setTitle("UFRN ON TOUCH");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		return true;
	}

	// Bot�o voltar...
	@Override
	public void onBackPressed() {
		Intent voltaIntent = new Intent();
		voltaIntent.putExtra("usuario", usuario);
		voltaIntent.setClass(ProfileEdit.this, MainActivity.class);
		startActivity(voltaIntent);
		finish();

	}

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		 if (requestCode == 100 || requestCode == 1) {
			 Bitmap image = (Bitmap) data.getExtras().get("data");
			 trocaImagem.setImageBitmap(image);	 
		 }
		 
	}
	
	private void setButtons() {

		feito = (Button) findViewById(R.id.button_feito);
		feito.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent mainIntent = new Intent();
				daoUsuario.open();
				usuario.setNome(edit_nome.getText().toString());
				usuario.setCurso(edit_curso.getText().toString());
				usuario.setSobreMim(edit_sobre.getText().toString());
				daoImagem.putImagem(usuario.getLogin(), ((BitmapDrawable)trocaImagem.getDrawable()).getBitmap());
				daoUsuario.updateUsuario(usuario.getLogin(), usuario);
				daoUsuario.close();
				
				mainIntent.putExtra("usuario", usuario);
				mainIntent.setClass(ProfileEdit.this, MainActivity.class);
				startActivity(mainIntent);

				finish();
			}
		});
		trocaImagem = (ImageButton)findViewById(R.id.trocaImagem);
		
		trocaImagem = (ImageButton) findViewById(R.id.trocaImagem);
		Bitmap imgPerfil = daoImagem.getImagem(usuario.getLogin());
		if(imgPerfil == null){
			trocaImagem.setImageResource(R.drawable.bomb);
		}else{
			trocaImagem.setImageBitmap(imgPerfil);	 
		}
		
		trocaImagem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				/*Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setType("image/*");
                intent.putExtra("crop", "true");  //op��o de cropar.
                intent.putExtra("outputX", 150);  //poe a resolu��o que vc quiser.
                intent.putExtra("outputY", 150); 
                intent.putExtra("aspectX", 1);  //poe aspect ratio que vc quiser
                intent.putExtra("aspectY", 1);

                intent.putExtra("return-data", true);
			    startActivityForResult(intent, 100);*/
				selectImage();
			}
			
		});
		
		
		
		
		

	}
    private void selectImage() {
    	 
        final CharSequence[] options = { "Camera", "Galeria","Cancelar" };
 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra("outputX", 150);  //poe a resolu��o que vc quiser.
                    intent.putExtra("outputY", 150); 
                    
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Galeria"))
                {
    				Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setType("image/*");
                    intent.putExtra("crop", "true");  //op��o de cropar.
                    intent.putExtra("outputX", 150);  //poe a resolu��o que vc quiser.
                    intent.putExtra("outputY", 150); 
                    intent.putExtra("aspectX", 1);  //poe aspect ratio que vc quiser
                    intent.putExtra("aspectY", 1);

                    intent.putExtra("return-data", true);
    			    startActivityForResult(intent, 100);
 
                }
                else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}
