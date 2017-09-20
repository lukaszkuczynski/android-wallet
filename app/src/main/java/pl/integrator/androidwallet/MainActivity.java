package pl.integrator.androidwallet;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.save);

        button.setOnClickListener( (View view) -> {
            Snackbar.make(view, "You clicked me, Wallet Navigator!", Snackbar.LENGTH_INDEFINITE)
                    .show();
        });

    }

}
