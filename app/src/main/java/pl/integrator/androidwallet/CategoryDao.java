package pl.integrator.androidwallet;

import java.util.List;

/**
 * Created by luk on 08.10.17.
 */

public interface CategoryDao {

    List<String> findAll();

    void removeCategory(String name);

    void addCategory(String name);

}
