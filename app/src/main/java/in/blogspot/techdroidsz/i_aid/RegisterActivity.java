package in.blogspot.techdroidsz.i_aid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreateBtn;

    private FirebaseAuth mAuth;

    private Button mLog_page_btn;


    private ProgressDialog mRegProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mRegProgress=new ProgressDialog(this);



        mAuth=FirebaseAuth.getInstance();

        mDisplayName=(EditText) findViewById(R.id.reg_name);
        mEmail=(EditText)findViewById(R.id.reg_email);
        mPassword=(EditText) findViewById(R.id.reg_pass);
        mCreateBtn=(Button) findViewById(R.id.reg_create_btn);
        mLog_page_btn=(Button) findViewById(R.id.log_page_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String display_name=mDisplayName.getText().toString();
                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();

                if(!TextUtils.isEmpty(display_name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){

                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(display_name,email,password);
                }



            }
        });

        mLog_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);


            }
        });
    }
    private void register_user(final String display_name, String email, String password){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult>task){
                if (task.isSuccessful()) {

                    mRegProgress.dismiss();

                    Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else{

                    mRegProgress.hide();

                    Toast.makeText(RegisterActivity.this,"Cannot Sign in.Please check the form and try again.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}