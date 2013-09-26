package dao;

import java.util.List;


public interface IDaoGenerico<T> {
	
	  public void add(T entidade);
	  public void edit(T entidade);
      public void delete(T entidade);
      public List<T> search(T entidade);
}
