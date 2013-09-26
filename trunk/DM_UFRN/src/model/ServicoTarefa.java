package model;

import java.util.List;

import dao.DAOTarefa;

public class ServicoTarefa implements IServicoTarefa {

	private static ServicoTarefa singleton = null;
	private DAOTarefa daoTarefa;

	public ServicoTarefa() {
		this.daoTarefa = new DAOTarefa();
	}

	public static ServicoTarefa getInstance() {
		if (singleton == null) {
			singleton = new ServicoTarefa();
		}
		return singleton;
	}

	public void addTarefas(Tarefas tarefa) {
		daoTarefa.add(tarefa);
	}

	public void editTarefas(Tarefas tarefas) {
		daoTarefa.edit(tarefas);
	}

	public void deleteTarefas(Tarefas tarefas) {
		daoTarefa.delete(tarefas);
	}

	public List<Tarefas> searchTarefas(String Lugar) {
		return daoTarefa.searchTarefas(Lugar);
	}

}
