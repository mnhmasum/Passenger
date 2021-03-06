package com.netcabs.passenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.netcabs.asynctask.LoginWithPinAsyncTask;
import com.netcabs.customview.CustomEditText;
import com.netcabs.database.PreferenceUtil;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

public class LoginWithPinActivity extends Activity implements OnClickListener {

	private TextView txtViewUserName;
	private TextView txtViewUserMsg;
	private TextView txtViewLostPin;
	private CustomEditText edTxtPinOne;
	private CustomEditText edTxtPinTwo;
	private CustomEditText edTxtPinThree;
	private CustomEditText edTxtPinFour;
	private RelativeLayout relativeLayout;
	private int ediTxtFocusedPosition = 0;
	private PreferenceUtil preferenceUtil;
	
	private boolean hasValue = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
		
		setContentView(R.layout.activity_login_with_pin);
		initViews();
		setListener();
		loadData();
	}
	
	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		txtViewUserName = (TextView) findViewById(R.id.txt_view_user_name);
		txtViewLostPin = (TextView) findViewById(R.id.txt_view_lost_click);
		txtViewUserMsg = (TextView) findViewById(R.id.txt_view_lost);
		
		edTxtPinOne = (CustomEditText) findViewById(R.id.ed_txt_pin_one);
		edTxtPinTwo = (CustomEditText) findViewById(R.id.ed_txt_pin_two);
		edTxtPinThree = (CustomEditText) findViewById(R.id.ed_txt_pin_three);
		edTxtPinFour = (CustomEditText) findViewById(R.id.ed_txt_pin_four);
		
	/*	edTxtPinOne.setImeActionLabel("Del", KeyEvent.KEYCODE_ENTER);
		edTxtPinTwo.setImeActionLabel("Del", KeyEvent.KEYCODE_ENTER);
		edTxtPinThree.setImeActionLabel("Del", KeyEvent.KEYCODE_ENTER);
		edTxtPinFour.setImeActionLabel("Del", KeyEvent.KEYCODE_ENTER);*/
		
		preferenceUtil = new PreferenceUtil(LoginWithPinActivity.this);
	}

	private void setListener() {
		txtViewLostPin.setOnClickListener(this);
		relativeLayout.setOnClickListener(this);
		
		
		edTxtPinOne.setOnKeyListener(new OnKeyListener() {                 
		        @Override
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
		             if(keyCode == KeyEvent.KEYCODE_DEL){  
		            		edTxtPinOne.clearFocus();
							edTxtPinFour.setText("");
							edTxtPinFour.requestFocus();
							ediTxtFocusedPosition = 3;
							edTxtPinFour.setCursorVisible(true);
		                 }
		        return false;       
		            }
		    });
		
		edTxtPinTwo.setOnKeyListener(new OnKeyListener() {                 
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
	             if(keyCode == KeyEvent.KEYCODE_DEL){  
	            		edTxtPinTwo.clearFocus();
						edTxtPinOne.setText("");
						edTxtPinOne.requestFocus();
						ediTxtFocusedPosition = 0;
						edTxtPinOne.setCursorVisible(true);
	                 }
	        return false;       
	            }
	    });
		edTxtPinThree.setOnKeyListener(new OnKeyListener() {                 
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
	             if(keyCode == KeyEvent.KEYCODE_DEL){  
	            	 	edTxtPinThree.clearFocus();
						edTxtPinTwo.setText("");
						edTxtPinTwo.requestFocus();
						ediTxtFocusedPosition = 1;
						edTxtPinTwo.setCursorVisible(true);
	                 }
	        return false;       
	            }
	    });
		edTxtPinFour.setOnKeyListener(new OnKeyListener() {                 
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
	             if(keyCode == KeyEvent.KEYCODE_DEL){  
	            		edTxtPinFour.clearFocus();
						edTxtPinThree.setText("");
						edTxtPinThree.requestFocus();
						ediTxtFocusedPosition = 2;
						edTxtPinThree.setCursorVisible(true);
	                 }
	        return false;       
	            }
	    });
		
		
		edTxtPinOne.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			//	Log.e("action id is", actionId+"&"+event.getKeyCode());
				Log.e("key action id is", ""+actionId);
				//if(actionId== EditorInfo.IME_ACTION_PREVIOUS) {
					edTxtPinOne.clearFocus();
					edTxtPinFour.setText("");
					edTxtPinFour.requestFocus();
					ediTxtFocusedPosition = 3;
					edTxtPinFour.setCursorVisible(true);
				return false;
			}
	    });
		
		
		edTxtPinTwo.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			//if(actionId== EditorInfo.IME_ACTION_PREVIOUS) {
					edTxtPinTwo.clearFocus();
					edTxtPinOne.setText("");
					edTxtPinOne.requestFocus();
					ediTxtFocusedPosition = 0;
					edTxtPinOne.setCursorVisible(true);
				return false;
			}
	    });
		
		edTxtPinThree.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				//if(actionId == EditorInfo.IME_ACTION_PREVIOUS) {
					edTxtPinThree.clearFocus();
					edTxtPinTwo.setText("");
					edTxtPinTwo.requestFocus();
					ediTxtFocusedPosition = 1;
					edTxtPinTwo.setCursorVisible(true);
				return false;
			}
	    });
		
		edTxtPinFour.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				//if(actionId == EditorInfo.IME_ACTION_PREVIOUS) {
					edTxtPinFour.clearFocus();
					edTxtPinThree.setText("");
					edTxtPinThree.requestFocus();
					ediTxtFocusedPosition = 2;
					edTxtPinThree.setCursorVisible(true);
				return false;
			}
	    });
		
		edTxtPinOne.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if( edTxtPinOne.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					edTxtPinOne.clearFocus();
					edTxtPinTwo.requestFocus();
					ediTxtFocusedPosition = 1;
					edTxtPinTwo.setCursorVisible(true);
					sendToChoosePinCodeActivity();
				} else {
					edTxtPinOne.requestFocus();
					edTxtPinOne.setCursorVisible(true);
				}
			}
		});
		
		edTxtPinTwo.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(edTxtPinTwo.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					edTxtPinTwo.clearFocus();
					edTxtPinThree.requestFocus();
					edTxtPinThree.setCursorVisible(true);
					ediTxtFocusedPosition = 2;
					sendToChoosePinCodeActivity();
				} else {
					edTxtPinTwo.requestFocus();
					edTxtPinTwo.setCursorVisible(true);
				}
			}
		});
		edTxtPinThree.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(edTxtPinThree.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					edTxtPinThree.clearFocus();
					edTxtPinFour.requestFocus();
					edTxtPinFour.setCursorVisible(true);
					ediTxtFocusedPosition = 3;
					sendToChoosePinCodeActivity();
				} else {
					edTxtPinThree.requestFocus();
					edTxtPinThree.setCursorVisible(true);
				}
			}
		});
		edTxtPinFour.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(edTxtPinFour.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					edTxtPinFour.requestFocus();
					edTxtPinFour.setCursorVisible(true);
					sendToChoosePinCodeActivity();
				} else {
//					edTxtPinFour.requestFocus();
//					edTxtPinFour.setCursorVisible(true);
				}
				
			}
		});
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edTxtPinFour, InputMethodManager.SHOW_IMPLICIT);
		imm.showSoftInput(edTxtPinThree, InputMethodManager.SHOW_IMPLICIT);	
		imm.showSoftInput(edTxtPinTwo, InputMethodManager.SHOW_IMPLICIT);
		imm.showSoftInput(edTxtPinOne, InputMethodManager.SHOW_IMPLICIT);
	}

	private void loadData() {
		if (PassengerApp.getInstance().getProfileDetailsInfo() != null) {
			txtViewUserName.setText(PassengerApp.getInstance().getProfileDetailsInfo().getFirstName() + " " + PassengerApp.getInstance().getProfileDetailsInfo().getLastName());
			txtViewUserMsg.setText("Not "+PassengerApp.getInstance().getProfileDetailsInfo().getFirstName() + " " + PassengerApp.getInstance().getProfileDetailsInfo().getLastName() + " or lost your PIN?");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(LoginWithPinActivity.this, v);
			break;
			
		case R.id.txt_view_lost_click:
			preferenceUtil.setUserID("");
			preferenceUtil.setLOGIN_TYPES("0");
			Intent intent = new Intent(LoginWithPinActivity.this, LoginActivity.class);
			Log.e("I am here clearing top", ""+"-----------");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
	
	public void sendToChoosePinCodeActivity() {
		if(edTxtPinOne.getText().toString().length() > 0 && edTxtPinTwo.getText().toString().length() > 0 && edTxtPinThree.getText().toString().length() > 0 && edTxtPinFour.getText().toString().length() > 0) {
			String strPin = edTxtPinOne.getText().toString().trim() + edTxtPinTwo.getText().toString().trim() + edTxtPinThree.getText().toString().trim() + edTxtPinFour.getText().toString().trim();
			if(InternetConnectivity.isConnectedToInternet(LoginWithPinActivity.this)) {
				  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					new LoginWithPinAsyncTask(LoginWithPinActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									finish();
									startActivity(new Intent(LoginWithPinActivity.this, MainMenuActivity.class));
								} else {
									edTxtPinOne.setText("");
									edTxtPinTwo.setText("");
									edTxtPinThree.setText("");
									edTxtPinFour.setText("");
									edTxtPinOne.requestFocus();
									edTxtPinOne.setCursorVisible(true);
									Toast.makeText(LoginWithPinActivity.this, "Invalid PIN, please try again", Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								Toast.makeText(LoginWithPinActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ConstantValues.FUNC_ID_LOGIN_WITH_PIN, preferenceUtil.getUserID(), strPin, "2", getDeviceToken(), preferenceUtil.getRegistrationKey(), "");
				  } else {
						new LoginWithPinAsyncTask(LoginWithPinActivity.this, new OnRequestComplete() {
							@Override
							public void onRequestComplete(String result) {
								try {
									if("2001".equals(result)) {
										finish();
										startActivity(new Intent(LoginWithPinActivity.this, MainMenuActivity.class));
									} else {
										edTxtPinOne.setText("");
										edTxtPinTwo.setText("");
										edTxtPinThree.setText("");
										edTxtPinFour.setText("");
										edTxtPinOne.requestFocus();
										edTxtPinOne.setCursorVisible(true);
										Toast.makeText(LoginWithPinActivity.this, "Invalid PIN, please try again", Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
									Toast.makeText(LoginWithPinActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
								}
							}
						}).execute(ConstantValues.FUNC_ID_LOGIN_WITH_PIN, preferenceUtil.getUserID(), strPin, "2", getDeviceToken(), preferenceUtil.getRegistrationKey(), "");
				  }
			} else {
				Toast.makeText(LoginWithPinActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private String getDeviceToken() {
		return Secure.getString(LoginWithPinActivity.this.getContentResolver(), Secure.ANDROID_ID);
	}
	
	@Override
	protected void onPause() {
		BaseActivity.isLock = true;
		super.onPause();
	}
	
	
	@Override
	public void onBackPressed() {
		if(ediTxtFocusedPosition == 0){
			edTxtPinOne.clearFocus();
			finish();
		} else if(ediTxtFocusedPosition == 1){
			ediTxtFocusedPosition = 0;
			edTxtPinTwo.clearFocus();
			edTxtPinOne.requestFocus();
			edTxtPinOne.setCursorVisible(true);
		}else if(ediTxtFocusedPosition == 2){
			ediTxtFocusedPosition = 1;
			edTxtPinThree.clearFocus();
			edTxtPinTwo.requestFocus();
			edTxtPinTwo.setCursorVisible(true);
		}else if(ediTxtFocusedPosition == 3){
			ediTxtFocusedPosition = 2;
			edTxtPinFour.clearFocus();
			edTxtPinThree.requestFocus();
			edTxtPinThree.setCursorVisible(true);
		}
		//super.onBackPressed();
	}
	
}
