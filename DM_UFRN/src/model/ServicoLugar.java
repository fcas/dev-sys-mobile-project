package model;

import java.util.List;
import dao.DAOLugar;
import dao.IDAOLugar;

public class ServicoLugar implements IServicoLugar {

	private static ServicoLugar singleton = null;
	private IDAOLugar daoLugar;

	public ServicoLugar() {
		this.daoLugar = new DAOLugar();
	}

	public static ServicoLugar getInstance() {
		if (singleton == null) {
			singleton = new ServicoLugar();
		}
		return singleton;
	}

	public void addLugar(Lugar lugar) {
		daoLugar.add(lugar);
	}

	public void editLugar(Lugar lugar) {
		daoLugar.edit(lugar);
	}

	public void deleteLugar(Lugar lugar) {
		daoLugar.delete(lugar);
	}

	public List<Lugar> searchLugar(String nome) {
		return daoLugar.searchLugar(nome);
	}

}
