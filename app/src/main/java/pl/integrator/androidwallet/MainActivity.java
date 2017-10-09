package pl.integrator.androidwallet;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.input;


public class MainActivity extends AppCompatActivity implements OperationResultListener {

    private static final String TAG = MainActivity.class.getCanonicalName();

    public static final String PREFS_NAME = "AwsWalletPrefsFile";
    private OperationDao operationDao;
    private CategoryDao categoryDao;
    private String newCategoryText;

    public static final String COGNITO_POOL = "COGNITO_POOL_ID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.operationDao = new LambdaOperationDao(this.getApplicationContext(), this.getSharedPreferences(PREFS_NAME, 0));
        this.categoryDao = new CategoryInSettingsDao(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.save);

        final EditText amountEdit = (EditText) findViewById(R.id.amount);


//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        hardcode your cognito id
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(COGNITO_POOL, "YOUR_POOL_ID_HERE");
//        editor.commit();

        final List<String> tagList = getCategoriesAndRefreshSpinner();


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

            AlertDialog.Builder builderNewCategory = new AlertDialog.Builder(MainActivity.this);
            builderNewCategory.setTitle("new category");
            final EditText newCategoryInput = new EditText(this);
            newCategoryInput.setInputType(InputType.TYPE_CLASS_TEXT);
            builderNewCategory.setView(newCategoryInput);
            builderNewCategory.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newCategoryText = newCategoryInput.getText().toString();
                    categoryDao.addCategory(newCategoryText);
                    tagsText.setText(newCategoryText);
                    getCategoriesAndRefreshSpinner();
                }
            });
            builderNewCategory.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog newCategoryDialog = builderNewCategory.create();

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setTitle("dialog");
            builderSingle.setItems(tagList.toArray(new CharSequence[tagList.size()]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String text = tagList.get(i);
                    if (text.equals("new")) {
                        newCategoryDialog.show();
                    } else {
                        selectedTags.add(text);
                        tagsText.setText(text);
                    }
                }
            }).create();
            builderSingle.show();
        });

        TextView messageTextView = (TextView) findViewById(R.id.messageTextView);

        button.setOnClickListener((View view) -> {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
            progressBar.setVisibility(View.VISIBLE);
            messageTextView.setText("");

            double amount = Double.valueOf(amountEdit.getText().toString());
            String tag = tagsText.getText().toString();
            String snackText = String.format("Adding operation '%s' with amount of %.2f. ", tag, amount);
            Snackbar.make(view, snackText, Snackbar.LENGTH_INDEFINITE);
            operationDao.saveOperationAsync(new Operation(amount, tag, null), this);

        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

    }

    @NonNull
    private List<String> getCategoriesAndRefreshSpinner() {
        final List<String> tagList = categoryDao.findAll();
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, tagList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return tagList;
    }

    @Override
    public void onResult(OperationResult operationResult) {
        Log.i(TAG, String.format("Result came from Lambda! Amount after %s", operationResult.getAmount_after()));
        String snackText = String.format("After operation the new balance is %s", operationResult.getAmount_after());
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        TextView messageTextView = (TextView) findViewById(R.id.messageTextView);
        messageTextView.setText(snackText);

//        Snackbar.make(this.findViewById(R.id.save) , snackText, Snackbar.LENGTH_INDEFINITE);

    }
}