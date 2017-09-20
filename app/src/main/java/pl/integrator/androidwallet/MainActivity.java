package pl.integrator.androidwallet;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private OperationDao operationDao = new InmemoryOperationDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.save);

        final EditText amountEdit = (EditText) findViewById(R.id.amount);
        final EditText descriptionEdit = (EditText) findViewById(R.id.description);

        button.setOnClickListener( (View view) -> {

            double amount = Double.valueOf(amountEdit.getText().toString());
            String description = descriptionEdit.getText().toString();
            String snackText = String.format("Adding operation '%s' with amount of %.2f", description, amount);
            Snackbar.make(view, snackText, Snackbar.LENGTH_INDEFINITE)
                    .show();
            operationDao.saveOperation(new Operation(amount, description));
        });

    }

}
