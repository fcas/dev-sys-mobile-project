package dao;

import android.content.Context;
import model.Tarefas;

public class DAOTarefa extends DAOGenerico<Tarefas> implements IDAOTarefa {

	public DAOTarefa(Context context) {
		super(context, nomeTabela, nomeTabela);
		// TODO Auto-generated constructor stub
	}
	
}
