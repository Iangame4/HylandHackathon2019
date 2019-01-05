package hyland2019.blackmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class SignIn extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPass;
    private FileOutputStream fileOutputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
    }

    public void signIn(View v){
        String email = edtEmail.getText().toString();
        String pass = edtEmail.getText().toString();
        boolean temp = isValid(email, pass);
        try {
            fileOutputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("123".getBytes()); //Bad Alex
        } catch(Exception e){
            e.printStackTrace();
        }
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
