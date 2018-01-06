package com.wisn.mainmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(this,TestRNActivity.class));
        TextView textView=findViewById(R.id.text);
        textView.setText(getResources().getString(R.string.host)+"\n packageName"+this.getApplication().getPackageName());

    }
}
