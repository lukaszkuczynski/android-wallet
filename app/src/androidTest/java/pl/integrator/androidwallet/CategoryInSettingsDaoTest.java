package pl.integrator.androidwallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.amazonaws.util.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by luk on 08.10.17.
 */

@RunWith(AndroidJUnit4.class)
public class CategoryInSettingsDaoTest {

    CategoryInSettingsDao sut;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        sut = new CategoryInSettingsDao(appContext);
    }

    @Test
    public void categoriesAreNotEmpty() {
        List<String> categories = sut.findAll();
        Assert.assertFalse(categories.isEmpty());
    }

    @Test
    public void daoCanAddCategory() {
        List<String> categories = sut.findAll();
        int sizeBefore = categories.size();
        String newCategory = "newCategory";
        if (categories.contains(newCategory)) {
            return;
        }
        sut.addCategory(newCategory);
        List<String> categoriesAfter = sut.findAll();
        Assert.assertTrue(categoriesAfter.contains(newCategory));
        int sizeAfter = categoriesAfter.size();
        Assert.assertEquals(sizeAfter, sizeBefore+1);
        sut.removeCategory(newCategory);
    }


}
