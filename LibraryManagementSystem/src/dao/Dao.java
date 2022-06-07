package dao;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {
	
	<T> T get(String id);
    Collection<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);

}
