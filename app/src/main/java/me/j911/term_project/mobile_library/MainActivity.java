package me.j911.term_project.mobile_library;

import android.content.Intent;
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
                Toast.makeText(getApplicationContext(), R.string.done, Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });
    }

    public void move(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.reserveMenu:
                intent = new Intent(MainActivity.this, Reserve.class);
                break;
            case R.id.requestMenu:
                intent = new Intent(MainActivity.this, Request.class);
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                return ;
        }
        MainActivity.this.startActivity(intent);
    }

    private void loggedInCheck() {
        if (!accountController.isLoggedIn()) {
            Toast.makeText(getApplicationContext(), R.string.login_require, Toast.LENGTH_SHORT).show();
            MainActivity.this.finish();
        }
    }

    private void setLoginStateText() {
        loginStateText.setText(R.string.session + ": " + accountController.getAccountName() +"("+accountController.getAccountId()+")");
    }
}
