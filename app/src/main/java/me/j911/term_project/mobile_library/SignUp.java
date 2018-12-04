package me.j911.term_project.mobile_library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.j911.term_project.mobile_library.controller.AccountController;

public class SignUp extends AppCompatActivity {

    private AccountController accountController;
    private EditText stdIdInput;
    private EditText stdNameInput;
    private EditText stdPwInput;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupscreen);

        accountController = AccountController.getInstance(getApplicationContext());
        stdIdInput = (EditText) findViewById(R.id.JoinStdId);
        stdNameInput = (EditText) findViewById(R.id.JoinStdName);
        stdPwInput = (EditText) findViewById(R.id.JoinStdPw);
        signUpButton = (Button) findViewById(R.id.JoinSignup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    private void signup() {
        int id = Integer.parseInt(stdIdInput.getText().toString(), 10);
        String name = stdNameInput.getText().toString();
        String password = stdPwInput.getText().toString();

        int result = accountController.signup(name, id, password);
        switch (result) {
            case 201:
                Toast.makeText(getApplicationContext(), "Success Signup", Toast.LENGTH_LONG).show();
                SignUp.this.finish();
                break;
            case 409:
                Toast.makeText(getApplicationContext(), "Account Conflict", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
    }
}
