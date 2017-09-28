package pl.integrator.androidwallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity {

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

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);


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
        spinner.setAdapter(spinnerArrayAdapter);

        button.setOnClickListener((View view) -> {

            double amount = Double.valueOf(amountEdit.getText().toString());
            String tag = spinner.getSelectedItem().toString();
            OperationResult operationResult = operationDao.saveOperation(new Operation(amount, tag, null));
            String snackText = String.format("Adding operation '%s' with amount of %.2f. After that new balance is %s", tag, amount, operationResult.getAmount_after());
            Snackbar.make(view, snackText, Snackbar.LENGTH_INDEFINITE)
                    .show();



        });


    }
}