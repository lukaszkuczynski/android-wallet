package pl.integrator.androidwallet;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by luk on 08.10.17.
 */

public class CategoryInSettingsDao implements CategoryDao {


    private SharedPreferences sharedPreferences;

    private final static String[] DEFAULT_CATEGORIES = new String[]{"gasoline", "shopping"};

    private final static String CATEGORIES_PREFERENCES_KEY = "CATEGORIES";

    public CategoryInSettingsDao(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public List<String> findAll() {
        Set<String> categories = this.sharedPreferences.getStringSet(CATEGORIES_PREFERENCES_KEY, new HashSet<>());
        if (categories.size() == 0) {
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            Set<String> defaultCategoriesSet = new HashSet<>();
            defaultCategoriesSet.addAll(Arrays.asList(DEFAULT_CATEGORIES));
            editor.putStringSet(CATEGORIES_PREFERENCES_KEY, defaultCategoriesSet);
            editor.commit();
            categories = this.sharedPreferences.getStringSet(CATEGORIES_PREFERENCES_KEY, new HashSet<>());
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("new");
        list.addAll(categories);
        return list;
    }

    @Override
    public void removeCategory(String name) {
        Set<String> categories = this.sharedPreferences.getStringSet(CATEGORIES_PREFERENCES_KEY, new HashSet<>());
        if (!categories.contains(name)) {
            throw new RuntimeException("Nothing to delete. There is no category named "+name);
        }
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        categories.remove(name);
        editor.putStringSet(CATEGORIES_PREFERENCES_KEY, categories);
        editor.commit();
    }

    @Override
    public void addCategory(String name) {
        Set<String> categories = this.sharedPreferences.getStringSet(CATEGORIES_PREFERENCES_KEY, new HashSet<>());
        if (categories.contains(name)) {
            throw new RuntimeException("Category "+name+" already exists.");
        }
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        categories.add(name);
        editor.putStringSet(CATEGORIES_PREFERENCES_KEY, categories);
        editor.commit();
    }
}
