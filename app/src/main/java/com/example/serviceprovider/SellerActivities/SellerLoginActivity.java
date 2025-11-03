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

import com.example.serviceprovider.Api.ServiceProviderLogInResponse;
import com.example.serviceprovider.BuyerActivities.SettingsActivity;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.example.serviceprovider.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerLoginActivity extends AppCompatActivity {

    Button sellerLoginBtn;
    TextView sellerRegisterHere ;
    EditText email;
    EditText password;
    boolean passwordVisibility ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        sellerLoginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sellerEmail = email.getText().toString();
                String sellerPassword = password.getText().toString();

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


                Call<ServiceProviderLogInResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .sellerLogin(sellerEmail,sellerPassword);


                call.enqueue(new Callback<ServiceProviderLogInResponse>() {
                    @Override
                    public void onResponse(Call<ServiceProviderLogInResponse> call, Response<ServiceProviderLogInResponse> response)
                    {

                        ServiceProviderLogInResponse loginResponse = response.body();
                        if (response.isSuccessful())
                        {
                            Toast.makeText(SellerLoginActivity.this, "Logged In Successfuly!", Toast.LENGTH_SHORT).show();

                            MySharedPreferences.setBooleanValue(SellerLoginActivity.this,SharedPref.LOGGED_IN,true);
                            MySharedPreferences.setStringValue(SellerLoginActivity.this,SharedPref.LOGIN_TYPE,"SERVICE");
                            MySharedPreferences.setAPIToken(SellerLoginActivity.this, loginResponse.getToken());
                            MySharedPreferences.setServiceProvider(SellerLoginActivity.this, loginResponse.getServiceProvider());
                            MySharedPreferences.setService(SellerLoginActivity.this, loginResponse.getService());
                            MySharedPreferences.setUserType(SellerLoginActivity.this, MySharedPreferences.USER_TYPE.Service_Provider);


                            Intent loginBtnIntent = new Intent(SellerLoginActivity.this, SellerActivity.class);
//                            loginBtnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(loginBtnIntent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SellerLoginActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ServiceProviderLogInResponse> call, Throwable throwable)
                    {
                        Toast.makeText(SellerLoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        sellerRegisterHere = findViewById(R.id.registerHere);
        sellerRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(SellerLoginActivity.this, SellerRegisterActivity.class);
                startActivity(registerIntent);
//                finish();
            }
        });

        // Password Visibility / In-Visible
        password.setOnTouchListener(new View.OnTouchListener() {
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