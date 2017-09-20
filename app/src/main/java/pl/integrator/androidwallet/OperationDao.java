package pl.integrator.androidwallet;

/**
 * Created by luk on 20.09.17.
 */

public interface OperationDao {

    LastState saveOperation(Operation transaction);

}
