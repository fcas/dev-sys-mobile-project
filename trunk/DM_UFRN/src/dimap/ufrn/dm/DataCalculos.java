package dimap.ufrn.dm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class DataCalculos {
	public static String normalizarData(int dia, int mes, int ano){
		return String.format("%04d", ano)+"-"+String.format("%02d", mes)+"-"+String.format("%02d", dia); 
	}
	
	public static String dataAtual(){
		Calendar c = Calendar.getInstance();
		String result = normalizarData(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		Log.d("DataAtual", result);
		return result;
	}
	public static String normalizarHora(int hora, int minuto){
		return String.format("%02d", hora)+":"+String.format("%02d", minuto);
	}
	
	public static String visaoToBanco(String data){
		String[] array = data.split("/");
		String result = array[2] + "-" + array[1] + "-" + array[0];
		return result;
	}
	
	public static String bancoToVisao(String data){
		String[] array = data.split("-");
		String result = array[2] + "/" + array[1] + "/" + array[0];
		return result;
	}
	
	
}
