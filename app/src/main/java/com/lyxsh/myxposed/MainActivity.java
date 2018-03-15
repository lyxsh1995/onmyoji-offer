package com.lyxsh.myxposed;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Ceshi ceshi = new Ceshi();
//        ceshi.a = "a";
//        ceshi.b = 3;
//        setnumber(ceshi);

//        TextView textView = (TextView) findViewById(R.id.textview);
//        textView.setText(str);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Sousuo().execute(editText.getText().toString());
            }
        });
    }

//    private void setnumber(Ceshi a)
//    {
//        str = a.a+a.b;
//    }

//    class Ceshi{
//        String a;
//        int b;
//    }
}
