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
        this.cognitoPoolId = settings.getString(COGNITO_POOL, "nocognito");
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

    private OperationResult callSynchronously(LambdaInvokerFactory invokerFactory, Operation operation) {
        try {
            Log.i(TAG, String.format("Calling with operation %s", operation.toString()));
            AddOperation myInterface = invokerFactory.build(AddOperation.class);
            OperationResult operationResult = myInterface.addOperation(operation);
            Log.i(TAG, String.format("Call result %s", operationResult.toString()));
            return operationResult;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String callASynchronously(LambdaInvokerFactory invokerFactory, Operation operation) {
        AddOperation myInterface = invokerFactory.build(AddOperation.class);

        AsyncTask<Double, Void, String> asyncTask = new AsyncTask<Double, Void, String>() {
            @Override
            protected String doInBackground(Double... params) {
                try {
//                    return myInterface.addOperation(params[0],params[1],params[2]);
                    throw new RuntimeException("not yet implemented");
                } catch (LambdaFunctionException lfe) {
                    Log.e(TAG, "Failed to invoke echo", lfe);
                    return null;
                }
            }
        };

        return "";
    }


    public Context getApplicationContext() {
        return applicationContext;
    }
}
