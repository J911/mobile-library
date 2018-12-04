package me.j911.term_project.mobile_library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.j911.term_project.mobile_library.controller.AccountController;

public class SignIn extends AppCompatActivity {

    private AccountController accountController;
    private EditText stdIdInput;
    private EditText stdPwInput;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinscreen);

        accountController = AccountController.getInstance(getApplicationContext());
        stdIdInput = (EditText) findViewById(R.id.stdId);
        stdPwInput = (EditText) findViewById(R.id.stdPw);
        signInButton = (Button) findViewById(R.id.signin);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

    }

    private void signin() {
        int id = Integer.parseInt(stdIdInput.getText().toString(), 10);
        String password = stdPwInput.getText().toString();

        int result = accountController.signin(id, password);
        switch (result) {
            case 200:
                Toast.makeText(getApplicationContext(), "Hello " + accountController.getAccountName(), Toast.LENGTH_LONG).show();
                break;
            case 403:
                Toast.makeText(getApplicationContext(), "No Matched Password", Toast.LENGTH_LONG).show();
                break;
            case 404:
                Toast.makeText(getApplicationContext(), "No Matched Account", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
    }
}
