package com.jenjenfuture.myinventory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    EditText productName;
    EditText productDesc;
    EditText productQuantity;

    Button button;

    DBProducts dbProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productName = findViewById(R.id.productName);
        productDesc = findViewById(R.id.productDesc);
        productQuantity = findViewById(R.id.productQuantity);

        button = findViewById(R.id.buttonSubmit);

        dbProducts = new DBProducts(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();
            }
        });


    }

    private void checkValidate() {

        boolean isValid = true;



        if (isEmpty(productName)){
            productName.setError("This input must be filled");
            isValid=false;
        }
        else
        {
            if (productName.getText().toString().length()<5 || productName.getText().toString().length()>15){
                productName.setError("Product name must between 5 and 15 characters");
                isValid=false;
            }
            else productName.setError(null);
        }
        if (isEmpty(productDesc)){
            productDesc.setError("This input must be filled");
            isValid = false;
        }
        else{
            if (productDesc.getText().toString().length()<10 || productDesc.getText().toString().length()>120){
                productDesc.setError("Product description must been 10 and 120 characters");
                isValid = false;
            }
            else productDesc.setError(null);
        }
        if (isEmpty(productQuantity)){
            productQuantity.setError("This input must be filled");
            isValid = false;
        }
        else
        {
            if (Integer.parseInt(productQuantity.getText().toString())>9999){
                productQuantity.setError("Quantity must not more than 9999");
                isValid=false;
            }
            else productQuantity.setError(null);
        }

        if (isValid){
            String name=productName.getText().toString();
            String desc=productDesc.getText().toString();
            int quan = Integer.parseInt(productQuantity.getText().toString());

            List<Product> list = new ArrayList<>();
            list.addAll(dbProducts.getAllProducts());

            boolean validName = true;
            for(int i=0;i<list.size();i++){
                if (name.matches(list.get(i).name)){
                    productName.setError("Product Name already exists");
                    validName = false;
                }
            }

            if (validName){
                Product product = new Product();
                product.name = name;
                product.desc = desc;
                product.quantity = quan;

                dbProducts.insertItem(product);

                Toast toast = Toast.makeText(getApplicationContext(),"Add Product Successfully",Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

        }

    }

    boolean isEmpty(EditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }
}
