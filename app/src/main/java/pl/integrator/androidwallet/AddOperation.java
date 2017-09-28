package pl.integrator.androidwallet;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/**
 * Created by luk on 25.09.17.
 */

public interface AddOperation {

    @LambdaFunction(functionName = "HelloAws")
    public OperationResult addOperation(Operation jsonOperation);

}
