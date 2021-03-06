package com.netcabs.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.netcabs.interfacecallback.OnComplete;
import com.netcabs.json.JSONParser;
import com.netcabs.utils.ConstantValues;

public class LocationByName extends AsyncTask<Void, Void, Void> {
	String url;
	private OnComplete callback;
	private JSONObject json;
	private JSONObject jsonResult;
	private JSONObject jsonGeometry;
	private JSONObject jsonLocation;
	
	private double searchLatitude;
	private double searchlongitude;
	
	String browserKey="AIzaSyClsbD9wwIuk_6MtTHxlVP_Ym7GwO3JpJY";
	
	private static final String TAG_RESULT = "result";
	private static final String TAG_RESULT_GEOMETRY = "geometry";
	private static final String TAG_RESULT_GEOMETRY_LOCATION = "location";
	private String refId = null;
	private Activity context;
	private ProgressDialog progressDialog;
	
	public LocationByName(Activity context, OnComplete callback2, String refId) {
		this.callback = (OnComplete) callback2;
		this.refId = refId;
		this.context = context;
			
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "Loading...", true, false);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		url = "https://maps.googleapis.com/maps/api/place/details/json?reference="+refId+"&sensor=true&key="+ConstantValues.browserKey;
		JSONParser jParser = new JSONParser();
		// getting JSON string from URL
		json = jParser.getJSONFromUrl(url.toString());
		Log.e("RESPONSE for lat & lon", "**" + json);
		if (json != null) {
			try {
				// Getting Array of Contacts
				jsonResult = json.getJSONObject(TAG_RESULT);
				jsonGeometry = jsonResult.getJSONObject(TAG_RESULT_GEOMETRY);
				jsonLocation = jsonGeometry.getJSONObject(TAG_RESULT_GEOMETRY_LOCATION);
			
				searchLatitude = jsonLocation.getDouble("lat");
				searchlongitude = jsonLocation.getDouble("lng");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			if ((progressDialog != null) && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		} catch (final IllegalArgumentException e) {
		} catch (final Exception e) {
		} finally {
			progressDialog = null;
		}
		
		
		Log.i("lat :" +searchLatitude, "Lon : "+searchlongitude);
		try {
			callback.onLocationComplete(1, searchLatitude, searchlongitude);
		} catch (Exception e) {
			
		}
		
		
	}
	

}
