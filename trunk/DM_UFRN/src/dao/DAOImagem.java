package dao;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class DAOImagem {

  public Bitmap getImagem(String filename) {
      try {
          //File f = new File("/mnt/sdcard/"+filename);
          File f = new File("/mnt/sdcard/dm_ufrn/"+filename);
          if (!f.exists()) { 
        	  Log.d("Nao existe", "Nao");
        	  return null; 
          }
          Bitmap tmp = BitmapFactory.decodeFile("/mnt/sdcard/dm_ufrn/"+filename);
          
          return tmp;
      } catch (Exception e) {
    	  Log.d("Deu erro", "Erro");
          return null;
      }
  }

  public boolean putImagem(String filename,Bitmap bmp) {
      try {
          FileOutputStream out = new FileOutputStream("/mnt/sdcard/dm_ufrn/"+filename);
          bmp.compress(CompressFormat.PNG, 100, out);
          out.flush();
          out.close();
          return true;
      } catch(Exception e) {
    	  return false;
      }
  }

  public boolean hasSDCard() { // SD????????
      String status = Environment.getExternalStorageState();
      return status.equals(Environment.MEDIA_MOUNTED);
  }
  public static String getSDCardPath() {
      File path = Environment.getExternalStorageDirectory();
      return path.getAbsolutePath();
  }

}

   