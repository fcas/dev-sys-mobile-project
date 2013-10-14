package dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class DAOImagem {
	Context context;
	public DAOImagem(Context context){
		this.context = context;
	}
	@SuppressWarnings("unused")
	private String filename;
	public void putImagem(String usuario, Bitmap imagem){
		Log.d("Usuario", usuario);
		filename = usuario;
		File file = new File(Environment.getExternalStorageDirectory(), ""+usuario+".jpeg");
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		try {
			fos.write(byteArray);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	@SuppressLint("NewApi")
	public Bitmap getImagem(String usuario) throws IOException{
		filename = ""+usuario+".jpeg";
		Bitmap bitmap = null;
		File file = new File(Environment.getExternalStorageDirectory(), ""+usuario+".jpeg");
		if(file.exists()){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				Log.d("ERRO", "ERRO");
			}
			Log.d("Decodificando", "Decodificando");
			bitmap = BitmapFactory.decodeStream(fis, null, null);
			Log.d("Pronto", String.valueOf(bitmap.getHeight()));
		}
		
		/*
		byte b;
		int i = 0;
		byte[] data = new byte[(int) file.getTotalSpace()]; 
		while ((b = (byte) fis.read()) != -1) {
			data[i] = (byte)b;
			i++;
		}
		Log.d("Byte", String.valueOf(data[0]));
		Log.d("Byte", String.valueOf(data[3]));
		Log.d("Byte", String.valueOf(data[7]));
		Log.d("Byte", String.valueOf(data[10]));
		Log.d("Byte", String.valueOf(data[44]));
		fis.close();
		Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data.length);*/
	    
		return bitmap;
		
	}
}
