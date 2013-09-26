package model;

import java.util.List;

public interface IServicoLugar {
	
	public void addLugar(Lugar lugar);
	public void editLugar(Lugar lugar);
	public void deleteLugar (Lugar lugar);
	public List<Lugar> searchLugar(String nome);

}
