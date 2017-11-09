package com.example.carlos.tt_julio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.location.Location;
//import com.google.android.gms.common.api.GoogleApiClient;
import java.net.MalformedURLException;
import java.util.logging.Handler;

import android.widget.Toast;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MainActivity extends AppCompatActivity {
    //Se definen variables
    EditText editText;
    static String store_location = "";
    public String retunnumfromAsyncTask;
    private Context mContext;

    protected static final String TAG = "MainActivity";
    protected Location mLastLocation;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    /////////variables de posicion
    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    //  private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 10;
    private static final int PLACE_PICKER_REQUEST = 100;
    ////////////////////
    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    //ArrayAdapter<String> adapter;
    String moneda = "MXN";
    private View view1;
    ArticuloAdapter adapter;
    static private RecyclerView lv;
    static ArrayList<articulo> list = new ArrayList<articulo>();

    static final int SCANNER_REQUEST_CODE = 0x0000c0de;
    static final int MOD_ITEMS = 1;
    private static final int COLOR_SELECTOR = 0;
    //recycler

    private LinearLayoutManager mLinearLayoutManager;
    AlertDialog alert;
    articulo[] data;
    int id_lista_aux;
    EditText textTotal;
    TextView textView_Total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alert Dialog With EditText"); //Set Alert dialog title here
        alert.setMessage("No se encontro el articulo"); //Message here
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Esta es mi aplicaciòn");
        setSupportActionBar(toolbar);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        adapter = new ArticuloAdapter(this, list);

        editText = (EditText) findViewById(R.id.editTextArticulo);

        //lista de elementos
        lv = (RecyclerView) findViewById(R.id.listView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(mLinearLayoutManager);
        lv.setAdapter(adapter);
        Button agregarBton = (Button) findViewById(R.id.buttonAgregar);
        textView_Total = (TextView) findViewById(R.id.textViewTotal);
        //set swipe to recylcerview
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(lv);

        ////////////////////////////////////////////////
        ///////////////////////////////////////////////
        //Administrador de la vista
        lv.addOnItemTouchListener(new RecyclerTouchListener(this,
                        lv, new ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        //int position = lv.getChildAdapterPosition(view);
                        articulo item = list.get(position);
                        id_lista_aux = position;
                        Intent intent = null;
                        intent = new Intent(getBaseContext(), Precio.class);

                        if (intent != null) {
                            intent.putExtra("articulo", item);
                            startActivityForResult(intent, MOD_ITEMS);
                            //Log.d("myTag", "This is my message");
                        }
                    }
                    @Override
                    public void onLongClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    }
                }
                )
        );
        ////agregar nuevo elemento
        agregarBton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                if (store_location=="")
                {
                    Toast.makeText(getApplicationContext(), "Elige una tienda", Toast.LENGTH_LONG).show();
                }
                else
                {
                    integrator.initiateScan();
                }
                //integrator.initiateScan();
                //new IntentIntegrator(MainActivity).initiateScan();
            }
        });
    }
    //Se crean los objetos menù u

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Context context = getApplicationContext();
        CharSequence text;
        int duration;
        Toast toast;
        //noinspection SimplifiableIfStatement)ç
        switch (id) {
            //case R.id.action_settings:
               //return true;
            case R.id.change_location:
                text = "Cambia tienda!";
                duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, text, duration);
                toast.show();
                //Aqui le ponemos la parte que dispara el mapa
                try{
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    // builder.setLatLngBounds(new LatLng(-33.8523341, 151.2106085));
                    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
                }catch(GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();

                }
                //Aqui le ponemos la parte que dispara el mapa
                /*try{
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    // builder.setLatLngBounds(new LatLng(-33.8523341, 151.2106085));
                    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
                }catch(GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }*/

           /* case R.id.add_item:
                text = "Nuevo item!";
                duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition(); //get position which is swipe

            if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //alert for confirm to delete
                builder.setMessage("Are you sure to delete?");    //set message

                builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemRemoved(position);    //item removed from recylcerview
                        list.remove(position);  //then remove item
                        textView_Total.setText("");
                        textView_Total.append("Total:"+Float.toString(adapter.cuentaTotal()));
                        return;
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                        return;
                    }
                }).show();  //show alert dialog
            }
        }
    };
    ////Sync
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case( SCANNER_REQUEST_CODE):{
                if(result != null){
                    if (result.getContents() == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("MainActivity", "Scanned");
                        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        new GetDataTask().execute(result.getContents());
                        //list.add(result.getContents()+"      PRecio///");
                        //edit.setText("");
                        //adapter.notifyDataSetChanged();
                    }
                }
                else {
                    // This is important, otherwise the result will not be passed to the fragment
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;
            }
            case (MOD_ITEMS):{
                if (resultCode == Activity.RESULT_OK) {

                    articulo regreso = data.getExtras().getParcelable("articulo");
                    adapter.getArticulo(id_lista_aux).setNumeroArticulos(regreso.getNumeroArticulos());
                    float precio = adapter.getArticulo(id_lista_aux).getNumeroArticulos()*adapter.getArticulo(id_lista_aux).getPrecioUnit();
                    adapter.getArticulo(id_lista_aux).setPrecioTotal(precio);
                    adapter.notifyDataSetChanged();
                    textView_Total.setText("");
                    textView_Total.append("Total:"+Float.toString(adapter.cuentaTotal()));
                    //textTotal.setText(Float.toString(adapter.cuentaTotal()));
                }
                break;
            }
            case(PLACE_PICKER_REQUEST):{
                if (resultCode == RESULT_OK) {
                    Place selectedPlace = PlacePicker.getPlace(data, this);
                    String toastMsg = String.format("Place: %s", selectedPlace.getId());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                    store_location = String.format("%s", selectedPlace.getId());
                }
                else{
                    store_location = "";
                    return;
                }

            }
        }
    }
    ///fin
    //Esto se necesita para proecesa la imagen del codigo de barras
    public class GetDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                //URL url = new URL("http://192.168.33.10/index_prueba_rest_TT.php/countries/" + params[0]);
                //URL url = new URL("http://192.168.1.76/index_prueba_rest_TT.php/countries/" + params[0]);
              //  URL url = new URL("http://192.241.228.193/index.php/countries/"+ params[0]);//este es el mero bueno,
                //URL url = new URL("http://192.241.228.193/index_gio.php/countries/"+ params[0]+"/"+store_location);//aquì podria cambai el numero de index apuntado,
                //http://192.241.228.193/indexG.php/countries/3605521172525/ChIJYawEyFiKzYUR7D5z7OKATbY
               URL url = new URL ("http://192.241.228.193/indexG.php/countries/"+ params[0]+"/"+store_location);
                //URL url = new URL ("http://192.241.228.193/indexG.php/countries/"+ params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();


            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }finally{
                if(connection != null){
                    connection.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
            return  null;
        }
        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (result.equals("false")) {
                editText.setText("");
                Toast.makeText(getBaseContext(), "Elemento no encontrado", Toast.LENGTH_LONG).show();

            }
            else {
                AuthMsg msg = new Gson().fromJson(result, AuthMsg.class);
                String articulo_item = msg.getNombre();
                float precio = msg.getPrecio();
                articulo articulo_temp = new articulo();
                articulo_temp.setNombreArticulo(articulo_item);
                articulo_temp.setPrecioUnit(precio);
                list.add(0, articulo_temp);
                adapter.notifyItemInserted(0);
                editText.setText("");
                textView_Total.setText("");
                textView_Total.append("Total:" + Float.toString(adapter.cuentaTotal()));


            }

        }


    }
    ///sync



}
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
//FIN de CLASE PRINCIPAL

interface ClickListener{
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}

//////////////////////////////////////////
/////////////////////////////////////////
/////////////////////////////////////////
//Recycler window
class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

        this.clicklistener=clicklistener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}