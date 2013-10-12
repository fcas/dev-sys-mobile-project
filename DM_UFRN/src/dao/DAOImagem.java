package dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	private String filename;
	public void putImagem(String usuario, Bitmap imagem){
		Log.d("Usuario", usuario);
		filename = usuario;
		File file = new File(Environment.getExternalStorageDirectory(), ""+usuario+".png");
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		imagem.compress(Bitmap.CompressFormat.PNG, 100, stream);
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
		filename = ""+usuario+".png";
		File file = new File(context.getExternalFilesDir("xdproductions"), filename);	
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte b;
		int i = 0;
		byte[] data = new byte[(int) file.getTotalSpace()]; 
		while ((b = (byte) fis.read()) != -1) {
			data[i] = (byte)b;
			i++;
		}
		fis.close();
		Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data.length);
	    return bitmap;
		
	}
}
