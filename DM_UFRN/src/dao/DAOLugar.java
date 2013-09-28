package dao;

import android.content.Context;
import model.Lugar;

public class DAOLugar extends DAOGenerico<Lugar> implements IDAOLugar {
	
	public DAOLugar(Context context) {
		super(context, nomeTabela, nomeTabela);
	}

}
