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

import com.example.serviceprovider.Api.UserRegisterResponse;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView buyerLoginHere ;
    EditText name, email, number, password, confirmPassword ;
    Button registerButton;

    boolean passwordVisibility ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);

        buyerLoginHere = findViewById(R.id.userloginHere);

        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        number = findViewById(R.id.userNumber);
        password = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.userConfirmPassword);

        registerButton = findViewById(R.id.userRegisterBtn);
        registerButton.setOnClickListener(this);
        buyerLoginHere.setOnClickListener(this);


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

        // Password Visibility / In-Visible
        confirmPassword.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if (motionEvent.getRawX() >= confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection = confirmPassword.getSelectionEnd();
                        if (passwordVisibility)
                        {
                            confirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_visibility_off,0);
                            confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisibility = false ;
                        }
                        else
                        {
                            confirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_visibility,0);
                            confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisibility = true ;
                        }
                        confirmPassword.setSelection(selection);
                        return true ;
                    }
                }
                return false ;
            }
        });

    }


    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.userRegisterBtn:
                registerUser();
                break;
            case R.id.userloginHere:
                switchOnLogin();
                break;
        }
    }

    private void registerUser()
    {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPassword = confirmPassword.getText().toString();

        if (userName.isEmpty())
        {
            name.requestFocus();
            name.setError("Please Enter Your Name");
            return;
        }
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
        if (userConfirmPassword.length()<8)
        {
            password.requestFocus();
            password.setError("Your Password should be 8 ");
            return;
        }

        Call<UserRegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userRegister(userName,userEmail,userPassword, userConfirmPassword);

        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response)
            {
                UserRegisterResponse registerResponse = response.body();
                if (response.isSuccessful())
                {
                    Intent loginBtnIntent = new Intent(BuyerRegisterActivity.this, BuyerLoginActivity.class);
//                    loginBtnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginBtnIntent);
                    finish();
                    Toast.makeText(BuyerRegisterActivity.this, "Registered Successfuly!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(BuyerRegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable throwable)
            {
                Toast.makeText(BuyerRegisterActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchOnLogin()
    {
        Intent userLoginIntent = new Intent(BuyerRegisterActivity.this, BuyerLoginActivity.class);
        startActivity(userLoginIntent);
//                finish();
    }



}