package activities;

//O bot�o voltar est� voltando para a tela de login...

import java.io.IOException;

import servicos.ServicoConexao;

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
import excecoes.UsuarioJaExisteException;

public class Cadastro extends Activity {
	Button cadastro;
	Usuario usuario;
	ImageButton trocaImagem;
	DAOUsuario daoUsuario;
	DAOImagem daoImagem;
	EditText edit_nome, edit_login, edit_senha, edit_confirmasenha, edit_sobre, edit_curso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		edit_nome = (EditText)findViewById(R.id.edit_usuario);
		edit_login = (EditText)findViewById(R.id.edit_login);
		edit_senha = (EditText)findViewById(R.id.edit_senha);
		edit_confirmasenha = (EditText)findViewById(R.id.edit_confirmasenha);
		edit_sobre= (EditText)findViewById(R.id.edit_sobre);
		edit_curso = (EditText)findViewById(R.id.edit_curso);
		daoUsuario = new DAOUsuario(this);
		daoImagem = new DAOImagem();
		daoUsuario.open();
		setButtons();
	}

private void setButtons() {
	
	cadastro = (Button) findViewById(R.id.button_cadastro);
	cadastro.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View view) {
				final boolean senhaValida = edit_senha.getText().toString().equals(edit_confirmasenha.getText().toString());
				final Builder builder = new AlertDialog.Builder(Cadastro.this);  
				Usuario usuario = new Usuario();
        		usuario.setNome(edit_nome.getText().toString());
        		usuario.setCurso(edit_curso.getText().toString());
        		usuario.setSenha(edit_senha.getText().toString());
        		usuario.setSobreMim(edit_sobre.getText().toString());
        		usuario.setLogin(edit_login.getText().toString());
        		//try{
	        		try {
						if(senhaValida){
							String result = mService.insertUsuario(usuario);
							daoImagem.putImagem(usuario.getLogin(), ((BitmapDrawable)trocaImagem.getDrawable()).getBitmap());    		
			        		//daoUsuario.createUsuario(usuario);
							mService.getUsuarios();
							if(result.equals("=") || result.equals("-")){
								builder.setTitle("Erro");
								if(result.equals("=")){
							        builder.setMessage("Usuario ja existe");  
								}else{
									builder.setMessage("Preencha todos os campos");  
								}
								builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
						        	public void onClick(DialogInterface arg0, int arg1) {
												arg0.dismiss();
						        	}
						        });	
							}else{
								builder.setTitle("Sucesso");  
							    builder.setMessage("Cadastro efetuado com sucesso");  
						        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
						        	public void onClick(DialogInterface arg0, int arg1) {
								        		Intent mainIntent = new Intent();
												mainIntent.setClass(Cadastro.this, Login.class);
												daoUsuario.close();
												startActivity(mainIntent);
												finish();
						        	}
						        });
							}
						}
					    else{
		        			builder.setTitle("Erro");  
					        builder.setMessage("Senhas nao se correspondem");  
					        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
					        	public void onClick(DialogInterface arg0, int arg1) {
											arg0.dismiss();
					        	}
					        });
		        		}

					} catch (ConexaoException e) {
						builder.setTitle("Internet Indisponivel");  
						builder.setMessage("Nao foi possivel cadastrar");
						builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				        	public void onClick(DialogInterface arg0, int arg1) {
										arg0.dismiss();
				        	}
				        });
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        						/*}catch(DadosIncompletosException e){
					builder.setTitle("Erro");  
					builder.setMessage("Preencha todos os campos");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			        	public void onClick(DialogInterface arg0, int arg1) {
									arg0.dismiss();
			        	}
			        });
				}
        		catch(UsuarioJaExisteException e){
        			builder.setTitle("Erro");  
					builder.setMessage("Ja existe um usuario com este login");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			        	public void onClick(DialogInterface arg0, int arg1) {
									arg0.dismiss();
			        	}
			        });
        		}*/
		        AlertDialog alert = builder.create();  
		        alert.show();
				
			}
		});
	trocaImagem = (ImageButton)findViewById(R.id.trocaImagem);
	trocaImagem.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			selectImage();
		
			
		}
		
	});
	
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == 1 || requestCode == 100) {
		 Bitmap image = (Bitmap) data.getExtras().get("data");
		 trocaImagem.setImageBitmap(image);	 
		 }
		 
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}
	
	//Bot�o voltar...
		@Override
		public void onBackPressed() {
			Intent voltaIntent = new Intent();
			voltaIntent.setClass(Cadastro.this, Login.class);
			startActivity(voltaIntent);
			finish();
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
