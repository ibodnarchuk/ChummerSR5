package com.iwan_b.chummersr5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iwan_b.chummersr5.utility.ChummerConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newCharButton = (Button) findViewById(R.id.new_char_button);
        Button loadCharButton = (Button) findViewById(R.id.load_char_button);
        Button exitButton = (Button) findViewById(R.id.exit_button);

        newCharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChummerConstants.TAG, "newCharButton was clicked()");
                Intent i = new Intent(MainActivity.this, NewCharacterPriorityTable.class);
                startActivity(i);

            }
        });

        loadCharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChummerConstants.TAG, "loadCharButton was clicked()");
                Toast.makeText(v.getContext(), "Load Character Button Clicked", Toast.LENGTH_LONG).show();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(ChummerConstants.TAG, "exitButton was clicked()");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
