package com.example.coolapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class BluetoothSample extends Activity {


	private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        //Set the buttons for later
    final Button TurnOnButton = (Button) findViewById(R.id.OnButton);
    final Button Discoverable = (Button) findViewById(R.id.DiscButton);
    final Button TurnOffButton = (Button) findViewById(R.id.OffButton);
    //Get the bluetooth adapter
    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    
    if (mBluetoothAdapter == null) {
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context,"Device not supported", Toast.LENGTH_LONG);
        toast.show();
    }
    
    //Turn on bluetooth
    TurnOnButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            if (!mBluetoothAdapter.isEnabled()) {
            	//Use intents to enable bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    });
    
    //Set device to discoverable
    Discoverable.setOnClickListener(new View.OnClickListener() {
     @Override
        public void onClick(View arg0) {
            if (!mBluetoothAdapter.isDiscovering()) {
            	//inform user using toast
                   Context context = getApplicationContext();
                   CharSequence text = "MAKING YOUR DEVICE DISCOVERABLE";
                   int duration = Toast.LENGTH_SHORT;

                   Toast toast = Toast.makeText(context, text, duration);
                   toast.show();
                   //Use intents to make device discoverable
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(enableBtIntent, REQUEST_DISCOVERABLE_BT);
                  
            }
        }
    });
    //Turn off bluetooth
    TurnOffButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) { 
        	//easy disable
            mBluetoothAdapter.disable();
            //i
            Context context = getApplicationContext();
               CharSequence text = "TURNING OFF BLUETOOTH";
               int duration = Toast.LENGTH_LONG;
               Toast toast = Toast.makeText(context, text, 15);
               toast.show();
            }
    });
}

}
