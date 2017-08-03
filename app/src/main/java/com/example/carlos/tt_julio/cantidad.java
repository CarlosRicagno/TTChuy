package com.example.carlos.tt_julio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class cantidad extends Activity {
    //variable for holding select color;
    private String color = "#ff0000";
    //intent for use when returning to main activity
    Intent selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulo);
        Button button_OK = (Button) findViewById(R.id.button_OK);
        TextView articuloNombre= (TextView) findViewById(R.id.textViewArticuloDef);
        Bundle getData = getIntent().getExtras();
        String getDataS = getData.getString("key");
        articuloNombre.setText(getDataS);
        //set action listener for Ok Button
        button_OK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
    /*set Our selected color value in selectedColor intent with selectedColor key,
     this is using for getting color from result in main activity*/
               //selectedColor.putExtra("selectedColor", color);
                //set result with our selectedColor intent and RESULT_OK request code
               //setResult(RESULT_OK, selectedColor);
                setResult(RESULT_OK);
                //then finish the activity
                finish();
            }
        });
    }
}