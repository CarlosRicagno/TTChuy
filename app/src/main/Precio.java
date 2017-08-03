package com.example.carlos.tt_mayo.tt_julio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Carlos on 28/03/2017.
 */
public class Precio extends Activity {
    Intent i;
    articulo item;
    Button ButtonCancel;
    Button ButtonOk;
    TextView nombreArticulo;
    TextView numeroCodigo;
    TextView precioUnit;
    TextView precioTotal;
    EditText numeroPiezas;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);

        setContentView(R.layout.articulo);

        i = getIntent();
        item = i.getExtras().getParcelable("articulo");
        ButtonCancel = (Button) findViewById(R.id.button_Cancel);
        ButtonOk = (Button) findViewById(R.id.button_OK);
        nombreArticulo = (TextView) findViewById(R.id.textViewArticuloDef);
        numeroCodigo = (TextView) findViewById(R.id.textViewCodigoDef);
        precioUnit = (TextView) findViewById(R.id.textView_Precio_u);
        precioTotal = (TextView) findViewById(R.id.textViewTotal);
        numeroPiezas = (EditText) findViewById(R.id.editTextPiezas);



       /*Intent i = getIntent();
        articulo item = i.getExtras().getParcelable("articulo");
        Button ButtonCancel = (Button) findViewById(R.id.button_Cancel);
        Button ButtonOk = (Button) findViewById(R.id.button_OK);
        TextView nombreArticulo = (TextView) findViewById(R.id.textViewArticuloDef);
        TextView numeroCodigo = (TextView) findViewById(R.id.textViewCodigoDef);
        TextView precioUnit = (TextView) findViewById(R.id.textView_Precio_u);
        TextView precioTotal = (TextView) findViewById(R.id.textViewTotal);
        final EditText numeroPiezas = (EditText) findViewById(R.id.editTextPiezas);
*/
        nombreArticulo.setText(item.getNombreArticulo());
        numeroCodigo.setText(String.valueOf(item.getCodigoBarras()));
        precioUnit.append(" $" + String.valueOf(item.getPrecioUnit()));
        numeroPiezas.setText(String.valueOf(item.getNumeroArticulos()));
        precioTotal.append(" $" + String.valueOf(item.getPrecioUnit() * item.getNumeroArticulos()));

        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        ButtonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i.putExtra("articulo",item);
                setResult(Activity.RESULT_OK,i);
                finish();

            }

        });

        numeroPiezas.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE ) {
                            precioTotal.setText(" ");
                            precioTotal.append(" $" + String.valueOf(item.getPrecioUnit() * Float.parseFloat(numeroPiezas.getText().toString())));
                            item.setNumeroArticulos(Integer.parseInt(numeroPiezas.getText().toString()));
                        }
                        return false;
                    }
                });


    }
}
