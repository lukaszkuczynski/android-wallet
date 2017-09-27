package pl.integrator.androidwallet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by luk on 27.09.17.
 */

@RunWith(AndroidJUnit4.class)
public class LambdaTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("pl.integrator.androidwallet", appContext.getPackageName());
    }

    @Test
    public void lambdaCanBeExecuted() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        LambdaOperationDao dao = new LambdaOperationDao(appContext);
        Operation transaction = new Operation(1.2, "Desc", null);
        dao.saveOperation(transaction);
    }

}
