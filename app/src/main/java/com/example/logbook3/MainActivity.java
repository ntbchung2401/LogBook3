package com.example.logbook3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button backward, forward,add_URL;
    ImageView imageView;
    EditText inputURL;
    MyDatabaseHelper myDB;
    ArrayList<String> arrayList;
    private int index;
    private static final String validateURL = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpeg|jpg|gif|png)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new MyDatabaseHelper(MainActivity.this);
        arrayList = new ArrayList<String>();
        storeDataInArrays();
        imageView = findViewById(R.id.img_view);

        backward = findViewById(R.id.backward);
        forward = findViewById(R.id.forward);
        inputURL = findViewById(R.id.inputURL);
        add_URL = findViewById(R.id.add_URL);


        add_URL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                String url = inputURL.getText().toString().trim();
                if(url.length() !=0 ) {
                    if(url.matches(validateURL)){
                        Glide.with(MainActivity.this)
                                .load(url)
                                .into(imageView);
                        Toast.makeText(MainActivity.this, "Add successfully!",Toast.LENGTH_SHORT).show();
                        myDB.addImgURL(url);
                        inputURL.getText().clear();
                    }
                    Toast.makeText(MainActivity.this, "Invalid URL!",Toast.LENGTH_SHORT).show();
                    }else{
                    Toast.makeText(MainActivity.this, "This field can't be empty!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void nextImage(View view){
        index++;
        if(index>=arrayList.size()){
            index = 0;
        }
        loadImage();
    }
    public void previousImage(View view){
        index--;
        if(index<=-1){
            index = arrayList.size()-1;
        }
        loadImage();
    }
    void storeDataInArrays(){
        Cursor cursor = myDB.readImgURL();
        if(cursor.getCount() >0){
            while (cursor.moveToNext()){
                arrayList.add(cursor.getString(0));
            }
        }
    }
    private void loadImage() {
        Glide.with(MainActivity.this)
                .load(arrayList.get(index))
                .centerCrop()
                .into(imageView);
    }
}