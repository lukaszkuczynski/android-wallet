package pl.integrator.androidwallet;

import android.util.Log;

/**
 * Created by luk on 20.09.17.
 */

public class InmemoryOperationDao implements OperationDao {

    public static final String TAG = InmemoryOperationDao.class.getCanonicalName();

    @Override
    public OperationResult saveOperation(Operation transaction) {
        Log.i(TAG, String.format("Saving transaction with description %s and amount %s on lat %.2f",transaction.getDescription(), Double.toString(transaction.getAmount()), transaction.getLocation().getLatitude()));
        return new OperationResult();
    }

    @Override
    public void saveOperationAsync(Operation operation, OperationResultListener listener) {
        Log.i(TAG, String.format("Saving transaction with description %s and amount %s on lat %.2f",operation.getDescription(), Double.toString(operation.getAmount()), operation.getLocation().getLatitude()));
    }

}
