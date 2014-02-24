package com.example.coolapp;

import java.util.List;

import org.ndeftools.Message;
import org.ndeftools.Record;
import org.ndeftools.externaltype.AndroidApplicationRecord;

import android.R.string;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class NFCSample extends Activity {

	
	protected NfcAdapter nfcAdapter;
	protected PendingIntent nfcPendingIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//The choose the xml layout file (res/layouts)
		setContentView(R.layout.activity_nfc);
		
		// initialize NFC
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}
//called on resume. Let's this app take over NFC tag detecting
	public void enableForegroundMode() {

		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED); // filter for all
		IntentFilter[] writeTagFilters = new IntentFilter[] {tagDetected};
		nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, writeTagFilters, null);
	}

	public void disableForegroundMode(){

		nfcAdapter.disableForegroundDispatch(this);
	}

	//Will be called when the phone senses an NFC tag
	@Override
	public void onNewIntent(Intent intent) {
		View myView = this.findViewById(android.R.id.content);
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {			
			TextView textView = (TextView) findViewById(R.id.title);

			

			Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			//if there exists data on the NFC tag
			if (messages != null) {

				vibrate(); // signal found messages

				// parse to records
					try {
						List<Record> records = new Message((NdefMessage)messages[0]);
						
						//Get the first payload (the actual message)
							byte[] payload =records.get(0).getNdefRecord().getPayload();
							/*The payload contains the message and a header which contains the
							* text encoding and the language. We'll find those and remove them to 
							*see the actual message
							*/
									 //Get the Text Encoding
						    String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
						        	
						        	//Get the Language Code
						        	int languageCodeLength = payload[0] & 0077;
						        	String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
						        	
						        	//Get the Text
						        	String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
						        	//Set the on screen text to the message text
						        	textView.setText(text);
						        	//Set the background color to the given message (cause why not)
						        	if(text.contentEquals("yellow")){
						        		myView.setBackgroundColor(Color.YELLOW);
						        	}
						        	else if(text.contentEquals("green")){
							        	myView.setBackgroundColor(Color.GREEN);
							        	}
						        	else if(text.contentEquals("red")){
							        	myView.setBackgroundColor(Color.RED);
							        	}
						        	else if(text.contentEquals("blue")){
							        	myView.setBackgroundColor(Color.BLUE);
							        	}
					} catch (Exception e) {
						textView.setText("Error :(");
						}

			}else{ //NFC tag sensed, but no message found
				textView.setText("No message on that tag");
				myView.setBackgroundColor(Color.WHITE);
			}
		} 
	}

	@Override
	protected void onResume() {
		

		super.onResume();

		enableForegroundMode();
	}

	@Override
	protected void onPause() {
		

		super.onPause();

		disableForegroundMode();
	}
	
	private void vibrate() {
		
		//Method to vibrate the phone (need to get permissions in the manifest)
		Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
		vibe.vibrate(500);
	}


}