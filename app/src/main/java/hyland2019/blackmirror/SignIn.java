package hyland2019.blackmirror;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class SignIn extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPass;
    public static int ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.GRAY);
    }

    public void signIn(View v){
        String email = edtEmail.getText().toString();
        String pass = edtEmail.getText().toString();
        boolean temp = isValid(email, pass);
        ID = 123;
        if (temp) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid sign in", Toast.LENGTH_LONG).show();
            edtPass.setText("");
        }
    }

    public boolean isValid(String email, String pass){
        //TODO: Call server to see if this is a valid sign in
        return true;
    }
}
