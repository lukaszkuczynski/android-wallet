package pl.integrator.androidwallet;

import android.util.Log;

/**
 * Created by luk on 20.09.17.
 */

public class InmemoryOperationDao implements OperationDao {

    public static final String TAG = InmemoryOperationDao.class.getCanonicalName();

    @Override
    public LastState saveOperation(Operation transaction) {
        Log.i(TAG, String.format("Saving transaction with description %s and amount %s on lat %.2f",transaction.getDescription(), Double.toString(transaction.getAmount()), transaction.getLocation().getLatitude()));
        return new LastState(0d);
    }
}
