package com.example.serviceprovider.SellerActivities;

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

import com.example.serviceprovider.Api.ServiceProviderRegisterResponse;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView sellerLoginHere ;
    EditText name, email, number, password, confirmPassword ;
    Button registerButton ;
    boolean passwordVisibility ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        sellerLoginHere = findViewById(R.id.loginHere);

        name = findViewById(R.id.sellerName);
        email = findViewById(R.id.sellerEmail);
        number = findViewById(R.id.sellerNumber);
        password = findViewById(R.id.sellerPassword);
        confirmPassword = findViewById(R.id.sellerConfirmPassword);

        registerButton = findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(this);
        sellerLoginHere.setOnClickListener(this);

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
            case R.id.registerBtn:
                registerSeller();
                break;
            case R.id.loginHere:
                switchOnLogin();
                break;
        }
    }

    private void registerSeller()
    {
        String sellerName = name.getText().toString();
        String sellerEmail = email.getText().toString();
        String sellerPassword = password.getText().toString();
        String sellerConfirmPassword = confirmPassword.getText().toString();

        if (sellerName.isEmpty())
        {
            name.requestFocus();
            name.setError("Please Enter Your Name");
            return;
        }
        if (sellerEmail.isEmpty())
        {
            email.requestFocus();
            email.setError("Please Enter Your Email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(sellerEmail).matches())
        {
            email.requestFocus();
            email.setError("Please Enter your Correct Email");
            return;
        }
        if (sellerPassword.isEmpty())
        {
            password.requestFocus();
            password.setError("Please Enter your Password");
            return;
        }
        if (sellerPassword.length()<8)
        {
            password.requestFocus();
            password.setError("Your Password should be 8 ");
            return;
        }

        Call<ServiceProviderRegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .serviceProviderRegister(sellerName,sellerEmail,sellerPassword, sellerConfirmPassword);

        call.enqueue(new Callback<ServiceProviderRegisterResponse>() {
            @Override
            public void onResponse(Call<ServiceProviderRegisterResponse> call, Response<ServiceProviderRegisterResponse> response)
            {
                ServiceProviderRegisterResponse registerResponse = response.body();
                if (response.isSuccessful())
                {
                    Intent loginBtnIntent = new Intent(SellerRegisterActivity.this, SellerLoginActivity.class);
//                    loginBtnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginBtnIntent);
                    finish();
                    Toast.makeText(SellerRegisterActivity.this, "Registered Successfuly!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SellerRegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceProviderRegisterResponse> call, Throwable throwable)
            {
                Toast.makeText(SellerRegisterActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void switchOnLogin()
    {
        Intent sellerLoginIntent = new Intent(SellerRegisterActivity.this, SellerLoginActivity.class);
                startActivity(sellerLoginIntent);
//                finish();
    }

}