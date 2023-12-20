package com.example.foodtracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;*/

/*import com.example.foodtracker.databinding.ActivityAddProductBinding;*/

public class addProduct extends AppCompatActivity {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    EditText name, date;
    Button add;
    String tag = "SECOND_TAG";
    ProductRepository productRepository;
    String dateString;
    ArrayList<String> target = new ArrayList<>();
    String nameText;
    ArrayList<String> ingredientsList;
    ArrayList<String> allergens;
    ArrayList<String> unwanteds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);

        name = findViewById(R.id.nameAdd);
        date = findViewById(R.id.dateAdd);
        add = findViewById(R.id.add);
        productRepository = new ProductRepository(addProduct.this);
        target = productRepository.retrieveUserList();
        Pair<ArrayList<String>, ArrayList<String>> userAllergensAndUnwanteds = productRepository.retrieveUserAllergensAndUnwanteds();
        allergens = userAllergensAndUnwanteds.first;
        unwanteds = userAllergensAndUnwanteds.second;
        Intent i = getIntent();
        ingredientsList = i.getStringArrayListExtra("IngredientsList");

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nameText = name.getText().toString();
                        String dateText = date.getText().toString();
                        Log.d(tag, target.toString());
                        Log.d(tag, ingredientsList.toString());
                        String freshness = determineFreshness(date.getText().toString());

                        if (dateText != null) {
                            if (checkContains(target)) {
                                ArrayList<String> allergicOver = containedElementAllergic(target);
                                ArrayList<String> unwantedOver = containedElementUnwanted(target);
                                showErrorDialog(addProduct.this, "ALERT! Unwanted-Alergic ingredients found.\nAlergics: " + allergicOver + "\nUnwanteds: " + unwantedOver);
                            } else {
                                if (isValidDate(dateText)) {

                                    Product product = new Product(nameText, dateText, freshness, ingredientsList);
                                    productRepository.insertOrUpdateProductData(product, addProduct.this);
                                } else {

                                    Toast.makeText(addProduct.this, "Invalid date format. Please enter a date in the format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(addProduct.this, "Date is null", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }


    private String determineFreshness(String expirationDate) {
        java.sql.Date expiration = parseSqlDate1(expirationDate);

        if (expiration == null) {

            return "Unknown";
        }

        long oneDayMillis = 24 * 60 * 60 * 1000;
        long fiveDaysMillis = 5 * oneDayMillis;

        long remaining = expiration.getTime() - System.currentTimeMillis();
        if (remaining < oneDayMillis && remaining > 0) {
            return "Expiring";
        } else if (oneDayMillis <= remaining && remaining <= fiveDaysMillis) {
            return "Good";
        } else {
            if (remaining <= 0){
                return "Expired";
            }
            return "Fresh";
        }
    }


    private java.sql.Date parseSqlDate1(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            java.util.Date utilDate = inputFormat.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isValidDate(String date) {

        String DATE_FORMAT_REGEX = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/(\\d{4})$";

        Pattern pattern = Pattern.compile(DATE_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    public boolean checkContains(ArrayList<String> unwanted){
        for(int i = 0; i < unwanted.size();i++){
            if(ingredientsList.contains(unwanted.get(i))){
                return true;
            }
        }
        return false;
    }
    public ArrayList<String> containedElementAllergic(ArrayList<String> target){
        ArrayList<String> containedElementsAlergic = new ArrayList<>();
        for(int i = 0; i < target.size();i++){
            if(ingredientsList.contains(target.get(i))){
                if(allergens.contains(target.get(i))){
                    containedElementsAlergic.add(target.get(i));
                }
            }
        }
        return containedElementsAlergic;
    }
    public ArrayList<String> containedElementUnwanted(ArrayList<String> target){
        ArrayList<String> containedElementsUnwanted = new ArrayList<>();
        for(int i = 0; i < target.size();i++){
            if(ingredientsList.contains(target.get(i))){
                if(unwanteds.contains(target.get(i))){
                    containedElementsUnwanted.add(target.get(i));
                }
            }
        }
        return containedElementsUnwanted;
    }
    public static void showErrorDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private Date parseSqlDate(String dateString) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            java.util.Date utilDate = inputFormat.parse(dateString);


            return new Date(utilDate.getTime());
        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }



}