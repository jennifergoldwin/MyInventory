package com.jenjenfuture.myinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    String nama;
    String desc;
    int quantity;
    int id;

    EditText editName;
    EditText editDesc;
    EditText editQuantity;
    DBProducts dbProducts;
    Button buttonEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.productNameEdit);
        editDesc = findViewById(R.id.productDescEdit);
        editQuantity = findViewById(R.id.productQuantityEdit);
        buttonEdit = findViewById(R.id.buttonEdit);

        id = getIntent().getIntExtra("id",0);
        nama = getIntent().getStringExtra("name");
        desc = getIntent().getStringExtra("desc");
        quantity = getIntent().getIntExtra("quan",0);


        dbProducts = new DBProducts(this);

        editName.setText(nama);
        editDesc.setText(desc);
        editQuantity.setText(String.valueOf(quantity));

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();

            }
        });




    }

    private void checkValidate() {

        boolean isValid = true;



        if (isEmpty(editName)){
            editName.setError("This input must be filled");
            isValid=false;
        }
        else
        {
            if (editName.getText().toString().length()<5 || editName.getText().toString().length()>15){
                editName.setError("Product name must between 5 and 15 characters");
                isValid=false;
            }
            else editName.setError(null);
        }
        if (isEmpty(editDesc)){
            editDesc.setError("This input must be filled");
            isValid = false;
        }
        else{
            if (editDesc.getText().toString().length()<10 || editDesc.getText().toString().length()>120){
                editDesc.setError("Product description must been 10 and 120 characters");
                isValid = false;
            }
            else editDesc.setError(null);
        }
        if (isEmpty(editQuantity)){
            editQuantity.setError("This input must be filled");
            isValid = false;
        }
        else
        {
            if (Integer.parseInt(editQuantity.getText().toString())>9999){
                editQuantity.setError("Quantity must not more than 9999");
                isValid=false;
            }
            else editQuantity.setError(null);
        }

        if (isValid){
            String name=editName.getText().toString();
            String desc=editDesc.getText().toString();
            int quan = Integer.parseInt(editQuantity.getText().toString());



            Product product = new Product();

            product.id = id;
            product.name = name;
            product.desc = desc;
            product.quantity = quan;

            dbProducts.updateItem(product);
            Toast toast = Toast.makeText(getApplicationContext(),"Edit Product Successfully",Toast.LENGTH_SHORT);
            toast.show();
            finish();


        }

    }

    boolean isEmpty(EditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }
}
