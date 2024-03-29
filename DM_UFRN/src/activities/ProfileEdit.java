package activities;

import java.io.IOException;

import servicos.ServicoConexao;
import servicos.ServicoConexao.LocalBinder;
import model.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import dao.DAOImagem;
import dao.DAOUsuario;
import dimap.ufrn.dm.R;
import excecoes.ConexaoException;
import excecoes.DadosIncompletosException;
public class ProfileEdit extends Activity {

	DAOUsuario daoUsuario;
	DAOImagem daoImagem;
	Usuario usuario;
	Button feito, button_redefine_senha;
	ImageButton trocaImagem;
	
	EditText edit_nome, edit_sobre, edit_curso; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daoUsuario = new DAOUsuario(this);
		daoImagem = new DAOImagem();
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		setContentView(R.layout.activity_profile_edit);
		//startService(new Intent("INICIAR_SERVICO_CONEXAO"));
		if (mBound){

		} 
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
				final Builder builder = new AlertDialog.Builder(ProfileEdit.this);  
		        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		        	public void onClick(DialogInterface arg0, int arg1) {
		        		arg0.dismiss();
	        		}
	        	
		        });
				usuario.setNome(edit_nome.getText().toString());
				usuario.setCurso(edit_curso.getText().toString());
				usuario.setSobreMim(edit_sobre.getText().toString());
				
				if(!usuario.getNome().equals("") && !usuario.getSenha().equals("")){
					//daoUsuario.updateUsuario(usuario.getLogin(), usuario);
					try {
						mService.updateUsuario(usuario);
						mService.getUsuarios();
						daoImagem.putImagem(usuario.getLogin(), ((BitmapDrawable)trocaImagem.getDrawable()).getBitmap());	
						builder.setTitle("Sucesso");  
						builder.setMessage("Dados Editados com sucesso");  
				        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				        	public void onClick(DialogInterface arg0, int arg1) {
				        		Intent mainIntent = new Intent();
								mainIntent.putExtra("usuario", usuario);
								mainIntent.setClass(ProfileEdit.this, MainActivity.class);
								startActivity(mainIntent);
//								/this.dismiss();
				        		finish();
			        		}
			        	
				        });
					} catch (ConexaoException e) {
						// TODO Auto-generated catch block
						builder.setTitle("Erro");  
						builder.setMessage("Nao foi possivel conectar a internet ou o servidor se encontra offline");  
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					builder.setTitle("Erro");  
					builder.setMessage("Preencha todos os campos corretamente");  
				}
				
				AlertDialog alert = builder.create();  
		        alert.show();
			}
		});
		
		button_redefine_senha = (Button) findViewById(R.id.button_redefine_senha);
		button_redefine_senha.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent redefineSenha = new Intent();
				redefineSenha.putExtra("usuario", usuario);
				redefineSenha.setClass(ProfileEdit.this, RedefinirSenha.class);
				startActivity(redefineSenha);
//				/this.dismiss();
        		finish();
			}
		});
		
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
    ServicoConexao mService;
    boolean mBound = false;
    
	   @Override
	    protected void onStart() {
	        super.onStart();
	        // Bind to LocalService
	        Intent intent = new Intent(this, ServicoConexao.class);
	        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	    }

	    @Override
	    protected void onStop() {
	        super.onStop();
	        // Unbind from the service
	        if (mBound) {
	            unbindService(mConnection);
	            mBound = false;
	        }
	    }
	    /** Defines callbacks for service binding, passed to bindService() */
	    private ServiceConnection mConnection = new ServiceConnection() {

	        @Override
	        public void onServiceConnected(ComponentName className,
	                IBinder service) {
	            // We've bound to LocalService, cast the IBinder and get LocalService instance
	            LocalBinder binder = (LocalBinder) service;
	            mService = binder.getService();
	            mBound = true;
	        }

	        @Override
	        public void onServiceDisconnected(ComponentName arg0) {
	            mBound = false;
	           
	        }
	    };	
    
}
