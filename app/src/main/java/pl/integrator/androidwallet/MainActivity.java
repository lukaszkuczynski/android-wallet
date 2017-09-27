package pl.integrator.androidwallet;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.StructuredPostal.REGION;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private OperationDao operationDao;

//    private Location location() throws SecurityException {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        Criteria criteria = new Criteria();
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//
//
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//
//            criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
//            criteria.setAccuracy(Criteria.ACCURACY_FINE);
//            criteria.setAltitudeRequired(true);
//            criteria.setBearingRequired(true);
//            criteria.setSpeedRequired(true);
//
//        }
//
//        String provider = locationManager.getBestProvider(criteria, true);
//        Location location = locationManager.getLastKnownLocation(provider);
//        return location;
//
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.operationDao = new LambdaOperationDao(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.save);

        final EditText amountEdit = (EditText) findViewById(R.id.amount);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);


        String[] tags = new String[]{
                "shopping",
                "gasoline"
        };

        final List<String> tagList = new ArrayList<>(Arrays.asList(tags));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, tagList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);






        button.setOnClickListener((View view) -> {

            double amount = Double.valueOf(amountEdit.getText().toString());
            String tag = spinner.getSelectedItem().toString();
            String snackText = String.format("Adding operation '%s' with amount of %.2f", tag, amount);
            Snackbar.make(view, snackText, Snackbar.LENGTH_INDEFINITE)
                    .show();
            operationDao.saveOperation(new Operation(amount, tag, null));



        });


    }
}