package com.example.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test.R;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CheckBox kameez,tops,gown;

    ArrayList<String> arr = new ArrayList<>();

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private TextView quantityTextView;
    private TextView priceTextView;
    private TextView ratingText;
    private TextView dress;
    private Button increment, decrement, placeOrder;
    private int quantity = 0;
    private int price = 0;

    private AlertDialog.Builder builder;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        kameez = findViewById(R.id.kamiz);
        tops = findViewById(R.id.tops);
        gown = findViewById(R.id.gown);
        dress = findViewById(R.id.dress);


        radioGroup = findViewById(R.id.radioGroup);


        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);

        placeOrder = findViewById(R.id.order_btn);
        builder = new AlertDialog.Builder(this);


        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.rating);

        kameez.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView,
                    isChecked);
        });
        tops.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });
        gown.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });




        increment.setOnClickListener(v -> {
            quantity++;
            price = quantity * 500;
            quantityTextView.setText("" + quantity);
            priceTextView.setText("$" + price);
        });

        decrement.setOnClickListener(v -> {
            if(quantity>0){
                quantity--;
                price = quantity * 500;
                quantityTextView.setText("" + quantity);
                priceTextView.setText("$" + price);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText("Rating: " + rating);
            }
        });

        placeOrder.setOnClickListener(v -> {
            try {
                if (arr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Any Dress!!", Toast.LENGTH_SHORT).show();
                }
                String radioValue = radioButton.getText().toString();
                if (quantity == 0) {
                    Toast.makeText(getApplicationContext(), "Please add quantity!!", Toast.LENGTH_SHORT).show();
                } else {
                    builder.setTitle("Order Placed Successfully!!")
                            .setMessage("Order Summary:\n" + "Dress: " + arr + "\nDress Size: " + radioValue + "\nQuantity: " + quantity + "\nTotal Price: BDT " + price + "\nRating: " + ratingBar.getRating() +"\nThank you!!")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Order Placed!!", Toast.LENGTH_SHORT).show();
                                    quantity = 0;
                                    price = 0;
                                    quantityTextView.setText("0");
                                    priceTextView.setText("BDT 0");
                                    dress.setText("");
                                    kameez.setChecked(false);
                                    tops.setChecked(false);
                                    gown.setChecked(false);

                                    radioGroup.clearCheck();
                                    ratingBar.setRating(0);
                                }
                            }).show();
                }

            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Please Select Dress Size!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void check(CompoundButton buttonView, Boolean isChecked){
        if (isChecked) {
            arr.add(buttonView.getText().toString());
            Log.d("array", String.valueOf(arr));
        } else{
            arr.remove(buttonView.getText().toString());
        }

        dress.setText(String.valueOf(arr));
    }
}