package com.example.serviceprovider.SellerActivities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.Order;
import com.example.serviceprovider.Api.OrderResponse;
import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartServiceActivity extends AppCompatActivity {

    TextView timerText;
    TextView serviceCharges;
    TextView totalTime;
    TextView serviceTotalCharges;
    EditText extraCharges;
    EditText extraWork;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    int cost = 0;
    int extraCost = 0;
    int totalCost = 0;
    Order order;
    boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);

        Button startServiceBtn = findViewById(R.id.startServiceBtn);
        totalTime = findViewById(R.id.totalTime);
        serviceCharges = findViewById(R.id.serviceCharges);
        extraCharges = findViewById(R.id.extraCharges);
        extraWork = findViewById(R.id.extraWork);
        serviceTotalCharges = findViewById(R.id.serviceTotalCharges);

        order = (Order) getIntent().getParcelableExtra(ItemContainer.KEY_ITEM);

        startServiceBtn.setOnClickListener(view -> {
            showTimeDialog();
        });

        timer = new Timer();

        Button serviceChargesPaid = findViewById(R.id.serviceChargesPaid);
        serviceChargesPaid.setOnClickListener(view1 -> {

            if(done)
            {
                Log.v("http", order.getId());
                Call<OrderResponse> call = RetrofitClient.getInstance().getApi()
                        .orderDone(order.getId(), String.valueOf(totalCost), extraWork.getText().toString(), String.valueOf(extraCost), totalTime.getText().toString(), "Bearer " + MySharedPreferences.getAPIToken(StartServiceActivity.this));
                call.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(StartServiceActivity.this, "Order Done", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {

                    }
                });

            }
        });

    }

    private void showTimeDialog() {
        final Dialog d = new Dialog(StartServiceActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.timer_dialog);
        d.show();

        timerText = d.findViewById(R.id.timer);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }

        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        Button stopBtn = d.findViewById(R.id.stopTimer);
        timerText.setText(getTimerText());

        stopBtn.setOnClickListener(view -> {

            new AlertDialog.Builder(this)
                    .setTitle("Stop?")
                    .setMessage("Are you sure you want to stop the timer?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            timerTask.cancel();
                            d.dismiss();
                            totalTime.setText(getTimerText());

                            Time t = getTime();
                            int mins = t.minute + t.hour * 60;
                            Service service = MySharedPreferences.getService(StartServiceActivity.this);
                            cost = mins * (Integer.parseInt(service.getHourly_cost()) / 60);
                            serviceCharges.setText(String.valueOf(cost));
                            totalCost = cost;
                            extraCharges.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if(!extraCharges.getText().toString().isEmpty())
                                    {
                                        extraCost = Integer.parseInt(extraCharges.getText().toString());
                                        totalCost = extraCost + cost;
                                        serviceTotalCharges.setText(String.valueOf(totalCost));
                                    }
                                }
                            });

                            done = true;
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();

            serviceTotalCharges.setText(String.valueOf(totalCost));


        });
    }

    @NonNull
    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private Time getTime() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        Time t = new Time();
        t.set(seconds, minutes, hours, 0, 0, 0);
        return t;
    }


    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
}