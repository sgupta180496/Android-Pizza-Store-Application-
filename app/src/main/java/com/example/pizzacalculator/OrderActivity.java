package com.example.pizzacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    TextView basePriceValue, toppingPriceValue, toppingNames, deliveryPriceValue, totalValue;
    double deliveryPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle("Pizza Store");
        basePriceValue = findViewById(R.id.basePriceValue);
        toppingNames = findViewById(R.id.toppingNames);
        toppingPriceValue = findViewById(R.id.toppingPriceValue);
        deliveryPriceValue = findViewById(R.id.deliveryCostValue);
        totalValue = findViewById(R.id.totalValue);

        Intent intent = getIntent();
        if(intent!=null) {
            Pizza pizza = (Pizza) intent.getSerializableExtra(MainActivity.PIZZA_KEY);
            basePriceValue.setText("6.50$");
            if (pizza.isDelivery()) {
                deliveryPrice = 4.0;
                deliveryPriceValue.setText("4.0$");
            } else {
                deliveryPrice = 0.0;
                deliveryPriceValue.setText("0.0$");
            }
            toppingNames.setText(pizza.getToppings());
            double toppingPrice = pizza.getTopping_num() * 1.50;
            toppingPriceValue.setText(toppingPrice + "$");

            double total = toppingPrice + 6.50 + deliveryPrice;
            totalValue.setText(total + "$");
        }
        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent done = new Intent();
                setResult(RESULT_OK,done);
                finish();
            }
        });

    }
}
