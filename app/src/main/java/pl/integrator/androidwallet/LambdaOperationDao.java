package pl.integrator.androidwallet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

/**
 * Created by luk on 27.09.17.
 */

public class LambdaOperationDao implements OperationDao {

    private static final String TAG = LambdaOperationDao.class.getCanonicalName();
    private final Context applicationContext;

    private static final String COGNITO_ID = "COGNITO_ID_HERE";
    private final String cognitoPoolId;

    public LambdaOperationDao(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.cognitoPoolId = COGNITO_ID;
    }

    @Override
    public LastState saveOperation(Operation transaction) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                this.cognitoPoolId,
                Regions.EU_CENTRAL_1 // Region
        );
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                this.getApplicationContext(),
                Regions.EU_CENTRAL_1,
                credentialsProvider);

        callSynchronously(factory, transaction);
        return null;

    }

    private String callSynchronously(LambdaInvokerFactory invokerFactory, Operation operation) {
        AddOperation myInterface = invokerFactory.build(AddOperation.class);
        String json = "{" +
                "\"op_type\": \"spending\","+
                "\"description\": \""+operation.getDescription()+"\","+
                "\"amount\": "+operation.getAmount()+
                "}";

        return myInterface.addOperation(json);
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
