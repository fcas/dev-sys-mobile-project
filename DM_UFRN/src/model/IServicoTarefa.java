package model;

import java.util.List;

public interface IServicoTarefa {
	
	public void addTarefas(Tarefas tarefa);
	public void editTarefas(Tarefas tarefas);
	public void deleteTarefas (Tarefas tarefas);
	public List<Tarefas> searchTarefas(String Lugar);

}
