package pl.integrator.androidwallet;

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

    private OperationDao operationDao = new InmemoryOperationDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                this,R.layout.support_simple_spinner_dropdown_item,tagList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


        button.setOnClickListener( (View view) -> {

            double amount = Double.valueOf(amountEdit.getText().toString());
            String tag = spinner.getSelectedItem().toString();
            String snackText = String.format("Adding operation '%s' with amount of %.2f", tag, amount);
            Snackbar.make(view, snackText, Snackbar.LENGTH_INDEFINITE)
                    .show();
            operationDao.saveOperation(new Operation(amount, tag));
        });

    }

}
