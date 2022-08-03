package demo;

import java.util.List;

public interface Crudable<T> {

    int save(T T);

    int update(T T);

    int delete(int id);

    T getById(int id);

    List<T> getAll();

}
