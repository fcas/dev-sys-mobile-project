package dao;

import java.util.List;

import model.Tarefas;

public interface IDAOTarefa  extends IDaoGenerico<Tarefas> {
	
	public List<Tarefas> searchTarefas(String lugar);

}
