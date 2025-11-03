package com.example.serviceprovider.BuyerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.UserLoginResponse;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.example.serviceprovider.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerLoginActivity extends AppCompatActivity
{
    Button bugerLoginBtn;
    TextView buyerRegisterHere ;
    EditText email ;
    EditText password ;
    boolean passwordVisibility ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login);

        bugerLoginBtn = findViewById(R.id.userloginBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        buyerRegisterHere = findViewById(R.id.registerHere);

        bugerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if (userEmail.isEmpty())
                {
                    email.requestFocus();
                    email.setError("Please Enter Your Email");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
                {
                    email.requestFocus();
                    email.setError("Please Enter your Correct Email");
                    return;
                }
                if (userPassword.isEmpty())
                {
                    password.requestFocus();
                    password.setError("Please Enter your Password");
                    return;
                }
                if (userPassword.length()<8)
                {
                    password.requestFocus();
                    password.setError("Your Password should be 8 ");
                    return;
                }


                Call<UserLoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .userLogin(userEmail,userPassword);


                call.enqueue(new Callback<UserLoginResponse>() {
                    @Override
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response)
                    {

                        UserLoginResponse loginResponse = response.body();
                        if (response.isSuccessful())
                        {
                            Toast.makeText(BuyerLoginActivity.this, "Logged In Successfuly!", Toast.LENGTH_SHORT).show();

                            MySharedPreferences.setBooleanValue(BuyerLoginActivity.this,SharedPref.LOGGED_IN,true);
                            MySharedPreferences.setStringValue(BuyerLoginActivity.this,SharedPref.LOGIN_TYPE,"USER");
                            MySharedPreferences.setAPIToken(BuyerLoginActivity.this, loginResponse.getToken());
                            MySharedPreferences.setUser(BuyerLoginActivity.this, loginResponse.getUser());
                            MySharedPreferences.setUserType(BuyerLoginActivity.this, MySharedPreferences.USER_TYPE.Service_Comsumer);

                            Intent loginBtnIntent = new Intent(BuyerLoginActivity.this, MainActivity.class);
//                            loginBtnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(loginBtnIntent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(BuyerLoginActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable throwable)
                    {
                        Toast.makeText(BuyerLoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        buyerRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userRegisterIntent = new Intent(BuyerLoginActivity.this , BuyerRegisterActivity.class);
                startActivity(userRegisterIntent);
//                finish();
            }
        });

        // Password Visibility / In-Visible
        password.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection = password.getSelectionEnd();
                        if (passwordVisibility)
                        {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_visibility_off,0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisibility = false ;
                        }
                        else
                        {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_visibility,0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisibility = true ;
                        }
                        password.setSelection(selection);
                        return true ;
                    }
                }
                return false ;
            }
        });


    }
}