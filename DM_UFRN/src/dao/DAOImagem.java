package dao;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

public class DAOImagem {

  public File getSavePath() {
      File path;
      if (hasSDCard()) { // SD card
          path = new File(getSDCardPath() + "/myapp/");
          path.mkdir();
      } else { 
          path = Environment.getDataDirectory();
      }
      return path;
  }
  public String getCacheFilename() {
      File f = getSavePath();
      return f.getAbsolutePath() + "/cache.png";
  }

  public Bitmap getImagem(String filename) {
      try {
          //File f = new File("/mnt/sdcard/"+filename);
          File f = new File("/mnt/sdcard/a.jpeg");
          if (!f.exists()) { 
        	  Log.d("Nao existe", "Nao");
        	  return null; 
          }
          Bitmap tmp = BitmapFactory.decodeFile(filename);
          
          
          Log.d(String.valueOf(tmp.getWidth()), String.valueOf(tmp.getPixel(36, 21)));
          return tmp;
      } catch (Exception e) {
    	  Log.d("Deu erro", "Erro");
          return null;
      }
  }
  public Bitmap loadFromCacheFile() {
      return getImagem(getCacheFilename());
  }
  public void saveToCacheFile(Bitmap bmp) {
      putImagem(getCacheFilename(),bmp);
  }
  public void putImagem(String filename,Bitmap bmp) {
      try {
          FileOutputStream out = new FileOutputStream(filename);
          bmp.compress(CompressFormat.PNG, 100, out);
          out.flush();
          out.close();
      } catch(Exception e) {}
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

   