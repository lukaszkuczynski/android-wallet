package pl.integrator.androidwallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

import static pl.integrator.androidwallet.MainActivity.COGNITO_POOL;

/**
 * Created by luk on 27.09.17.
 */

public class LambdaOperationDao implements OperationDao {

    private static final String TAG = LambdaOperationDao.class.getCanonicalName();
    private final Context applicationContext;

    private final String cognitoPoolId;

    public LambdaOperationDao(Context applicationContext, SharedPreferences settings) {
        this.applicationContext = applicationContext;
        this.cognitoPoolId = settings.getString(COGNITO_POOL, "no_cognito_id");
    }

    @Override
    public OperationResult saveOperation(Operation transaction) {
        Log.i(TAG, "Will call lambda with cognitopoolid="+this.cognitoPoolId);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                this.cognitoPoolId,
                Regions.EU_CENTRAL_1 // Region
        );
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                this.getApplicationContext(),
                Regions.EU_CENTRAL_1,
                credentialsProvider);

        return callSynchronously(factory, transaction);
    }

    @Override
    public void saveOperationAsync(Operation operation, OperationResultListener listener) {
        Log.i(TAG, "Will call lambda with cognitopoolid="+this.cognitoPoolId);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                this.cognitoPoolId,
                Regions.EU_CENTRAL_1 // Region
        );
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                this.getApplicationContext(),
                Regions.EU_CENTRAL_1,
                credentialsProvider);

        callASynchronously(factory, operation, listener);

    }

    private OperationResult callSynchronously(LambdaInvokerFactory invokerFactory, Operation operation) {
        Log.i(TAG, String.format("Calling with operation %s", operation.toString()));
        AddOperation myInterface = invokerFactory.build(AddOperation.class);
        OperationResult operationResult = myInterface.addOperation(operation);
        Log.i(TAG, String.format("Call result %s", operationResult.toString()));
        return operationResult;
    }

    private void callASynchronously(LambdaInvokerFactory invokerFactory, Operation operation, OperationResultListener listener) {
        AddOperation myInterface = invokerFactory.build(AddOperation.class);

        AsyncTask<Operation, Void, OperationResult> asyncTask = new AsyncTask<Operation, Void, OperationResult>() {
            @Override
            protected OperationResult doInBackground(Operation... params) {
                try {
                    return myInterface.addOperation(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e(TAG, "Failed to invoke echo", lfe);
                }
                return null;
            }
            @Override
            protected void onPostExecute(OperationResult result) {
                if (result == null) {
                    return;
                }
                listener.onResult(result);
            }
        }.execute(operation);
    }


    public Context getApplicationContext() {
        return applicationContext;
    }
}
