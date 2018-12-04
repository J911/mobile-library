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
    private Button cancleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupscreen);

        accountController = AccountController.getInstance(getApplicationContext());
        stdIdInput = (EditText) findViewById(R.id.JoinStdId);
        stdNameInput = (EditText) findViewById(R.id.JoinStdName);
        stdPwInput = (EditText) findViewById(R.id.JoinStdPw);
        signUpButton = (Button) findViewById(R.id.JoinSignup);
        cancleButton = (Button) findViewById(R.id.JoinCancle);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.this.finish();
            }
        });

    }

    private void signup() {
        String rawId = stdIdInput.getText().toString();
        String name = stdNameInput.getText().toString();
        String password = stdPwInput.getText().toString();

        if (rawId.equals("") || name.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.bad_inputs, Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(rawId, 10);

        int result = accountController.signup(name, id, password);
        switch (result) {
            case 201:
                Toast.makeText(getApplicationContext(), R.string.successed_signup, Toast.LENGTH_SHORT).show();
                SignUp.this.finish();
                break;
            case 409:
                Toast.makeText(getApplicationContext(), R.string.account_conflict, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }
}
