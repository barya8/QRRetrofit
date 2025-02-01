package com.example.qrretrofit;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrretrofit.api.GenericController;
import com.example.qrretrofit.interfaces.GenericCallback;
import com.example.qrretrofit.utilities.Constants;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton main_BTN_getAll;
    private MaterialButton main_BTN_getOne;
    private MaterialButton main_BTN_getPlural;
    private MaterialButton main_BTN_post;

    private GenericController genericController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        init();


    }

    private void init() {
        genericController = new GenericController(
                Constants.BASE_URL,
                new GenericCallback() {
                    @Override
                    public void success(String data) {
                        Log.d("Success!", "Data:" + data);
                    }

                    @Override
                    public void error(String error) {
                        Log.d("ERROR!!!", "Error:" + error);
                    }
                });


        main_BTN_getAll.setOnClickListener(v ->  genericController.getAllObjectsImpl() );
        main_BTN_getOne.setOnClickListener(v -> genericController.getSingleObjectImpl() );
        main_BTN_getPlural.setOnClickListener(v -> genericController.getPluralObjectImpl() );
        main_BTN_post.setOnClickListener(v -> genericController.createItemImpl() );
    }

    private void findViews() {
        main_BTN_getAll = findViewById(R.id.main_BTN_getAll);
        main_BTN_getOne = findViewById(R.id.main_BTN_getOne);
        main_BTN_getPlural = findViewById(R.id.main_BTN_getPlural);
        main_BTN_post = findViewById(R.id.main_BTN_post);
    }
}
