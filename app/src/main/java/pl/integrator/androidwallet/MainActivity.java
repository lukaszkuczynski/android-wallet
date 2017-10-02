package pl.integrator.androidwallet;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity implements OperationResultListener {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private OperationDao operationDao;

    public static final String PREFS_NAME = "AwsWalletPrefsFile";
    public static final String COGNITO_POOL = "COGNITO_POOL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.operationDao = new LambdaOperationDao(this.getApplicationContext(), this.getSharedPreferences(PREFS_NAME, 0));
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.save);

        final EditText amountEdit = (EditText) findViewById(R.id.amount);


        String[] tags = new String[]{
                "shopping",
                "gasoline"
        };

//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        hardcode your cognito id
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(COGNITO_POOL, "YOUR_POOL_ID_HERE");
//        editor.commit();

        final List<String> tagList = new ArrayList<>(Arrays.asList(tags));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, tagList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.INTERNET)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.INTERNET)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.INTERNET},
//                        0);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }

        TextView tagsText;
        tagsText = (TextView) findViewById(R.id.tags);
        final List<String> selectedTags = new ArrayList<>();
        tagsText.setOnClickListener((View view) -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setTitle("dialog");
            builderSingle.setItems(tags, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String text = tagList.get(i);
                    selectedTags.add(text);
                    tagsText.setText(text);
                }
            }).create();
            builderSingle.show();
        });

        button.setOnClickListener((View view) -> {

            double amount = Double.valueOf(amountEdit.getText().toString());
            String tag = selectedTags.get(0);
            String snackText = String.format("Adding operation '%s' with amount of %.2f. ", tag, amount);
            Snackbar.make(view, snackText, Snackbar.LENGTH_INDEFINITE);
            operationDao.saveOperationAsync(new Operation(amount, tag, null), this);

        });


    }

    @Override
    public void onResult(OperationResult operationResult) {
        Log.i(TAG, String.format("Result came from Lambda! Amount after %s", operationResult.getAmount_after()));
        String snackText = String.format("After operation the new balance is %s", operationResult.getAmount_after());
//        Snackbar.make(this.findViewById(R.id.save) , snackText, Snackbar.LENGTH_INDEFINITE);

    }
}