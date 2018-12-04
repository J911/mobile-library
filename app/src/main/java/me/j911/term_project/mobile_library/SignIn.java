package me.j911.term_project.mobile_library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.j911.term_project.mobile_library.controller.AccountController;

public class SignIn extends AppCompatActivity {

    private AccountController accountController;
    private EditText stdIdInput;
    private EditText stdPwInput;
    private Button signInButton;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinscreen);

        accountController = AccountController.getInstance(getApplicationContext());
        stdIdInput = (EditText) findViewById(R.id.loginStdIdInput);
        stdPwInput = (EditText) findViewById(R.id.loginStdPwInput);
        signInButton = (Button) findViewById(R.id.loginSigninBtn);
        signUpButton = (TextView) findViewById(R.id.loginSignupBtn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(SignIn.this, SignUp.class);
                SignIn.this.startActivity(signupIntent);
            }
        });

    }

    private void signin() {
        int id = Integer.parseInt(stdIdInput.getText().toString(), 10);
        String password = stdPwInput.getText().toString();

        int result = accountController.signin(id, password);
        switch (result) {
            case 200:
                Toast.makeText(getApplicationContext(),  R.string.hello + " " + accountController.getAccountName(), Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(SignIn.this, MainActivity.class);
                SignIn.this.startActivity(mainIntent);
                break;
            case 403:
                Toast.makeText(getApplicationContext(), R.string.account_no_mached_password, Toast.LENGTH_LONG).show();
                break;
            case 404:
                Toast.makeText(getApplicationContext(), R.string.account_not_found, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
        }
    }
}
