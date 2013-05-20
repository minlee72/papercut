package com.example.bluetoothtest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import paper.stage.Stage;
import paper.stage.StageData;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{
	//create버튼의 Action 상수
	private static final int CREATE_ACTION = 0;
	
	//블루투스 활성화 ACtion 상수
	private static final int REQUEST_ENABLE_BT = 1;
	
	//블루투스 브로드캐스트 상수
	public static final int BLUETOOTH_STATE_UNKNOWN = -1;
	public static final int BLUETOOTH_STATE_OFF = 
				BluetoothAdapter.STATE_OFF; //자체 블루투스 OFF
	public static final int BLUETOOTH_STATE_ON = 
				BluetoothAdapter.STATE_ON; //자체 블루투스 ON
	public static final int BLUETOOTH_STATE_TURNING_OFF = 
				BluetoothAdapter.STATE_TURNING_OFF; //주변 블루투스 ON
	public static final int BLUETOOTH_STATE_TURNING_ON = 
				BluetoothAdapter.STATE_TURNING_ON; //주변 블루투스 ON
	
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 2;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 3;
	
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
 // Name of the connected device
    private String mConnectedDeviceName = null;
    
	Button createBtn;
	Button removeBtn;
	Button receiveBtn;
	Button sendBtn;
	StageData mStageData;
	ListView myList;
	MyListAdapter myAdapter;
	
	//블루투스 어댑터
	BluetoothAdapter mBluetoothAdapter;
	//블루투스 상태 수신자
	BluetoothStateReceiver mReceiver;
	//페어링된 블루투스 장치 변수
	Set<BluetoothDevice> pairedDevices;
	ArrayAdapter<String> mArrayAdapter;
	
	private Handler mHandler;
	
	//데이터 송수신을 위한 멤버 객체
	private BluetoothDataChangeService mBTDataChangeService;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.e("MainActivity", "ON CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        createBtn = (Button)findViewById(R.id.createbtn);
        createBtn.setOnClickListener(this);
        
        removeBtn = (Button)findViewById(R.id.removebtn);
        removeBtn.setOnClickListener(this);
        
        receiveBtn = (Button)findViewById(R.id.receivebtn);
        receiveBtn.setOnClickListener(this);
        
        sendBtn = (Button)findViewById(R.id.sendbtn);
        sendBtn.setOnClickListener(this);
        
        //리스트뷰에 들어갈 항목을 설정한다.
        mStageData = StageData.getInstance(this);
        loadFileData();
        
        myAdapter = new MyListAdapter(this, R.layout.adapter_layout);
        myList = (ListView)findViewById(R.id.list);
        myList.setAdapter(myAdapter);
        myList.setOnItemClickListener(this);
        myAdapter.notifyDataSetChanged();
        
        //블루투스를 지원하는지 안하는지 검사
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
        	Toast.makeText(this, "This Phone is not support Bluetooth!", Toast.LENGTH_LONG);
        	finish();
        }
        
        
        
        //블루투스를 위한 브로드캐스트 액티비티에 등록
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        if(mReceiver == null){
        	mReceiver = new BluetoothStateReceiver();
        }
        
        this.registerReceiver(mReceiver, filter);
        
        mHandler = new Handler(){
        	@Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    Log.i("MainActivity", "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                    case BluetoothDataChangeService.STATE_CONNECTED:
                        break;
                    case BluetoothDataChangeService.STATE_CONNECTING:
                        break;
                    case BluetoothDataChangeService.STATE_LISTEN:
                    	
                    case BluetoothDataChangeService.STATE_NONE:
                        break;
                    }
                    break;
                case MESSAGE_WRITE:
                	
                    break;
                case MESSAGE_READ:
                    myAdapter.notifyDataSetChanged();
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                                   + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                                   Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        };
    }
    
    
    @Override 
    public void onStart(){
    	super.onStart();
    	Log.e("MainActivity", "++ON START++");
    	
    	//블루투스를 지원기기라면 켜지도록 함
        if(!mBluetoothAdapter.isEnabled()){
        	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        	startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
        	if(mBTDataChangeService == null)
        		setupService();
        }
        
    }
    @Override
    public synchronized void onResume(){
    	super.onResume();
    	Log.e("MainActivity", "++ON RESUME++");
    	if (mBTDataChangeService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBTDataChangeService.getState() == BluetoothDataChangeService.STATE_NONE) {
              // Start the Bluetooth chat services
            	mBTDataChangeService.start();
            }
        }
    }
    
    @Override
    public synchronized void onPause() {
        super.onPause();
        Log.e("MainActivity", "- ON PAUSE -");
        Toast.makeText(this, "ON PAUSE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("MainActivity", "-- ON STOP --");
        Toast.makeText(this, "ON STOP", Toast.LENGTH_SHORT).show();
    }
    
    private void setupService() {
		// TODO Auto-generated method stub
    	Log.e("MainActivity", "ON SETUP SERVICE");
    	mBTDataChangeService = new BluetoothDataChangeService(this, mHandler);
    	
	}

    private void sendMessage() {
        // Check that we're actually connected before trying anything
    	Stage st = null;
        if (mBTDataChangeService.getState() != BluetoothDataChangeService.STATE_CONNECTED) {
            Toast.makeText(this, "not connect", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isSend = false;
        
        // Check that there's actually something to send
        for(int i=0; i<mStageData.list.size(); i++){
        	if(mStageData.list.get(i).selected == true){
        		isSend = true;
        		st = mStageData.list.get(i);
        	}
        }
        if(isSend){
            // Get the message bytes and tell the BluetoothChatService to write
            mBTDataChangeService.write(st);

            // Reset out string buffer to zero and clear the edit text field
//            mOutStringBuffer.setLength(0);
//            mOutEditText.setText(mOutStringBuffer);
        }
    }
    
	private void loadFileData() {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = openFileInput("StageData");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Stage stage = null;
			while((stage = (Stage)ois.readObject())!= null){
				mStageData.setStage(stage);
			}
			fis.close();
			ois.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    protected void onDestroy(){
    	super.onDestroy();
    	try{
    		FileOutputStream fos = openFileOutput("StageData", Activity.MODE_PRIVATE);
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		
    		for(int i=0; i<mStageData.list.size(); i++){
    			oos.writeObject(mStageData.list.get(i));
    		}
    		fos.close();
    		oos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        // Stop the Bluetooth chat services
        if (mBTDataChangeService != null) mBTDataChangeService.stop();
        Log.e("MainActivity", "--- ON DESTROY ---");
        
        unregisterReceiver(mReceiver);
        if(mBluetoothAdapter.isDiscovering()){
        	mBluetoothAdapter.cancelDiscovery();
        }
        mBTDataChangeService.stop();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item){
		
		Intent serverIntent = null;
		switch(item.getItemId()){
		case R.id.secure_connect_scan:
			Log.e("MainActivity", "ON OPTIONITEMSELECTED");
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        case R.id.insecure_connect_scan:
        	Log.e("MainActivity", "ON OPTIONITEMSELECTED");
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);            
            return true;
        case R.id.discoverable:
        	Log.e("MainActivity", "ON OPTIONITEMSELECTED");
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
		return false;
		
	}

	private void ensureDiscoverable() {
		// TODO Auto-generated method stub
		if (mBluetoothAdapter.getScanMode() !=
	            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
	    }
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.createbtn:
			Toast.makeText(this, "ok1", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(MainActivity.this, CreateStageActivity.class);		
			startActivityForResult(i, CREATE_ACTION);
			
			break;
		case R.id.removebtn:
			int index = -1;
			for(int k=0; k<mStageData.list.size(); k++){
				if(mStageData.list.get(k).selected == true){
					index = k;
				}
			}
			if(index != -1){
				mStageData.removeStage(index);
			}
			else{
				Toast.makeText(this, "Select Stage!", Toast.LENGTH_SHORT).show();
			}
			
			myAdapter.notifyDataSetChanged();
			
			break;
		case R.id.receivebtn:
			
			break;
		case R.id.sendbtn:
			sendMessage();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case CREATE_ACTION:
			if(resultCode == RESULT_OK){
				Toast.makeText(this, "okok", Toast.LENGTH_SHORT).show();
			}
			break;
		case REQUEST_ENABLE_BT:
			if(resultCode == RESULT_OK){
			}
			else{
				Toast.makeText(this, "Bluetooth is not enable", Toast.LENGTH_LONG).show();
			}
			break;
		
			
		case REQUEST_CONNECT_DEVICE_INSECURE:
			if(resultCode == RESULT_OK){
				Toast.makeText(this, "insecure scan result", Toast.LENGTH_SHORT).show();
				connectDevice(data, false);
			}else{
				
			}
			break;
			
		case REQUEST_CONNECT_DEVICE_SECURE:
			if(resultCode == RESULT_OK){
				Toast.makeText(this, "secure scan result", Toast.LENGTH_SHORT).show();
				connectDevice(data, true);
			}else{
				
			}
			break;
		}
	}


	private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBTDataChangeService.connect(device, secure);
    }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		for(int i=0; i<mStageData.list.size(); i++){
			mStageData.list.get(i).selected = false;
		}
		mStageData.list.get(position).selected = true;
		
		myAdapter.notifyDataSetChanged();
	}
	
	class BluetoothStateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BLUETOOTH_STATE_UNKNOWN);
			onBluetoothStateChanged(state);
		}	
	}
	private void onBluetoothStateChanged(int state) {
		// TODO Auto-generated method stub

		if(state == BLUETOOTH_STATE_OFF){

		}else if(state == BLUETOOTH_STATE_TURNING_OFF){
			
		}else if(state == BLUETOOTH_STATE_TURNING_ON){
			
		}else if(state == BLUETOOTH_STATE_ON){
			
		}
	}
}
