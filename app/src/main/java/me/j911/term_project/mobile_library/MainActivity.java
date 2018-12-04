package me.j911.term_project.mobile_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.j911.term_project.mobile_library.controller.AccountController;

public class MainActivity extends AppCompatActivity {

    private AccountController accountController;
    private TextView loginStateText;
    private Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginStateText = (TextView) findViewById(R.id.loginStateText);
        signoutBtn = (Button) findViewById(R.id.signoutBtn);

        accountController = AccountController.getInstance(getApplicationContext());
        loggedInCheck();
        setLoginStateText();

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountController.signout();
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                MainActivity.this.finish();
            }
        });
    }

    private void loggedInCheck() {
        if (!accountController.isLoggedIn()) {
            Toast.makeText(getApplicationContext(), "please signin", Toast.LENGTH_LONG).show();
            MainActivity.this.finish();
        }
    }

    private void setLoginStateText() {
        loginStateText.setText("Session: " + accountController.getAccountName() +"("+accountController.getAccountId()+")");
    }
}
