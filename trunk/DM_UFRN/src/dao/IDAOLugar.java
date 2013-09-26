package dao;

import java.util.List;

import model.Lugar;

public interface IDAOLugar extends IDaoGenerico<Lugar> {

	List<Lugar> searchLugar(String nome);

}
