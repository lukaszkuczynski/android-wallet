package pl.integrator.androidwallet;

/**
 * Created by luk on 20.09.17.
 */

public interface OperationDao {

    OperationResult saveOperation(Operation transaction);

    void saveOperationAsync(Operation operation, OperationResultListener listener);
}
