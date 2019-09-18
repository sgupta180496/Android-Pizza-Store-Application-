package com.example.pizzacalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;

import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    CheckBox checkBox;
    public static String PIZZA_KEY = "pizza";
    public static int REQUEST_CODE;
    GridLayout grid;
    ArrayList<String> addedToppings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Pizza Store");
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);
        progressBar.setProgress(0);
        checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(false);
        grid = findViewById(R.id.gridLayout);


        final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("Bacon", R.drawable.bacon);
        map.put("Cheese", R.drawable.cheese);
        map.put("Garlic", R.drawable.garlic);
        map.put("Green Pepper", R.drawable.green_pepper);
        map.put("Mushroom", R.drawable.mashroom);
        map.put("Olives", R.drawable.olive);
        map.put("Onions", R.drawable.onion);
        map.put("Red Pepper", R.drawable.red_pepper);

        String title = "Choose a Topping";
        final String[] toppings = map.keySet().toArray(new String[map.size()]);
        // Add toppings to the pizza

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#0288D1'>" + title + "</font>")).setItems(toppings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addedToppings.add(toppings[i]);
                ImageView addedToppingImage = new ImageView(MainActivity.this);
                addedToppingImage.setImageDrawable(getDrawable(map.get(toppings[i])));
                addedToppingImage.setContentDescription(toppings[i]);
                addedToppingImage.setId(View.generateViewId());
                addedToppingImage.setTag(toppings[i]);
                addedToppingImage.setAdjustViewBounds(true);


                if (addedToppings.get(addedToppings.size() - 1) == "Bacon") {
                    addedToppingImage.setImageResource(R.drawable.bacon);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Cheese") {
                    addedToppingImage.setImageResource(R.drawable.cheese);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Garlic") {
                    addedToppingImage.setImageResource(R.drawable.garlic);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Green Pepper") {
                    addedToppingImage.setImageResource(R.drawable.green_pepper);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Mushroom") {
                    addedToppingImage.setImageResource(R.drawable.mashroom);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Olives") {
                    addedToppingImage.setImageResource(R.drawable.olive);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Onions") {
                    addedToppingImage.setImageResource(R.drawable.onion);

                } else if (addedToppings.get(addedToppings.size() - 1) == "Red Pepper") {
                    addedToppingImage.setImageResource(R.drawable.red_pepper);
                }

                grid.addView(addedToppingImage);
                progressBar.setProgress(progressBar.getProgress() + 1);
                addedToppingImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grid.removeView(view);
                        progressBar.setProgress(progressBar.getProgress() - 1);
                        addedToppings.remove(view.getTag());
                    }
                });
            }
        });
        final AlertDialog dialog = builder.create();
        ListView listViewToppings = dialog.getListView();
        listViewToppings.setDivider(new ColorDrawable(Color.BLACK));
        listViewToppings.setDividerHeight(1);
        listViewToppings.setFooterDividersEnabled(false);

        findViewById(R.id.addToppingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addedToppings.size() < 10)
                    dialog.show();
                else
                    Toast.makeText(MainActivity.this, "Maximum capacity Reached", Toast.LENGTH_SHORT).show();
            }
        });


        //clear pizza
        findViewById(R.id.clearPizzaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.removeAllViews();
                progressBar.setProgress(0);
                addedToppings.clear();
                checkBox.setChecked(false);
                return;
            }
        });


        //checkout
        findViewById(R.id.checkoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addedToppings.size()!=0) {
                    StringBuilder sb = new StringBuilder();
                    for (String topping : addedToppings) {
                        sb.append(topping).append(", ");
                    }
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    Pizza pizza = new Pizza(checkBox.isChecked(), addedToppings.size(), sb);
                    intent.putExtra(PIZZA_KEY, pizza);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else{
                    Toast.makeText(MainActivity.this, "No details entered", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE ) {
            if(resultCode == RESULT_OK) {
                grid.removeAllViews();
                progressBar.setProgress(0);
                checkBox.setChecked(false);
                addedToppings = new ArrayList<>();

            }

        }
    }
}

