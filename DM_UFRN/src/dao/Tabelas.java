package dao;

import model.Comentarios;
import model.Lugar;
import model.Tarefas;
import model.Usuario;

public class Tabelas {

	public static final String DATABASE_NAME = "xdproductions";
	public static final String[] SQL_DATABASE_CREATE = {
			Comentarios.CREATE_COMENTARIOS, Lugar.LUGAR_TABLE_CREATE,
			Tarefas.TAREFA_TABLE_CREATE, Usuario.USUARIO_TABLE_CREATE };
	public static final String[] TABLES = { "Comentarios", "LUGAR", "USUARIO",
			"TAREFA" };
}
