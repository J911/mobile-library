package me.j911.term_project.mobile_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import me.j911.term_project.mobile_library.controller.AccountController;

public class MainActivity extends AppCompatActivity {

    private AccountController accountController;
    private TextView loginStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginStateText = (TextView) findViewById(R.id.loginStateText);

        accountController = AccountController.getInstance(getApplicationContext());
        loggedInCheck();
        setLoginStateText();
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
