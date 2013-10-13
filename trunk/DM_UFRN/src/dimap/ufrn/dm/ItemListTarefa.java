package dimap.ufrn.dm;

import model.Tarefas;

public class ItemListTarefa {

	String tarefa, data, horario;
	 public ItemListTarefa() {
			//this("", -1);
	 }
	 
	 public ItemListTarefa(Tarefas t) { 
		 tarefa = t.getDescricao();
		 data = t.getData();
		 horario = t.getHorario();
	 }
		public String getTarefa() {
			return tarefa;
		}

		public void setTarefa(String tarefa) {
			this.tarefa = tarefa;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getHorario() {
			return horario;
		}

		public void setHorario(String horario) {
			this.horario = horario;
		}

	 
}
