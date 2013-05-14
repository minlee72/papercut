package paper.stageSelectActivity;

import paper.data.StageData;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import bayaba.engine.lib.GameInfo;

import com.example.papercult.R;



public class StageSelectActivity extends Activity {
	enum d_state {toVisible, toInvisible, stop};
	public SBGView sbgView;
	public SBGViewMain sbgMain;
	public SFGView sfgView;
	public GameInfo gInfo;
	public ListView stageList;
	StageAdapter adt;
	ScrTimer scrTimer;
	
	//Debugging
	private static final String TAG = "Bluetooth";
	private static final boolean D = true;
	//블루투스 어댑터
	BluetoothAdapter mBTAdapter;
	//블루투스 디바이스 활성화를 위한 상수변수
	

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		if(D) Log.e(TAG, "+++ ON CREATE +++");
		super.onCreate(savedInstanceState);
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        scrTimer = new ScrTimer();
        FrameLayout r = new FrameLayout(this);
        
        
		
        sbgMain = new SBGViewMain( this, gInfo);
        sbgView = new SBGView( this, sbgMain );
        sbgView.setRenderer( new SBGSurfaceClass(sbgMain) );
        
        r.addView(sbgView);
         
		adt = new StageAdapter(this, StageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		
		stageList.setOnTouchListener(new StageListListener(sbgMain, stageList, (int)gInfo.ScreenYsize));
		sbgMain.lv = stageList;
		
		sfgView = new SFGView(this,(int)(gInfo.ScreenXsize/10)*6, (int)gInfo.ScreenYsize);
		
		sbgMain.afgv = sfgView;
		sbgMain.adt = adt;
		r.addView(stageList, (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);
		r.addView(sfgView , (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);
		
		mBTAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBTAdapter == null){
			Toast.makeText(this, "이 모델은 블루투스를 지원하지 않는 모델입니다.\n", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
        setContentView( r );
       
	}

	public void onStart(){
		super.onStart();
		if(D) Log.e(TAG, "+++ ON START +++"+mBTAdapter.isEnabled());
		if(!mBTAdapter.isEnabled()){		
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}else{
			setupChat();
		}
		
	}
	@Override
	public synchronized void onResume(){
		super.onResume();
		StageData.getInstance().setStageLock();
		adt.alpha=0;
		sfgView.setAlpha(0);
		adt.notifyDataSetChanged();
		sfgView.invalidate();
		scrTimer.draw_state = d_state.toVisible;
		scrTimer.sendEmptyMessageDelayed(0, 1000);
		sbgMain.startScr();
		
		if(mChatService != null){
			if(mChatService.getState() == BluetoothChatService.STATE_NONE){
				mChatService.start();
			}
		}
	}
	
	//여기에 스테이지 셋업 하는 함수 넣을 예정
	private void setupChat() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public synchronized void onPause(){
		super.onPause();
		if(D) Log.e(TAG, "- ON PAUSE -");
	}
	
	@Override
	public void onStop(){
		super.onStop();
		if(D) Log.e(TAG, "-- ON STOP --");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		if(mChatService != null) mChatService.stop();
		if(D) Log.e(TAG, "---ON DESTROY---");
	}
	
	private void ensureDiscoverable(){
		if(D) Log.d(TAG, "ensure discoverable");
		if(mBTAdapter.getScanMode() !=
				BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
		}
			
	}
	
	//여기서 Stage 보내고
	private void sendMessage(){
		
	}
	
	private final void setStatus(int resId){
		final ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(resId);
	}
	
	private final void setStatus(CharSequence subTitle){
		final ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(subTitle);
	}
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    setStatus(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
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
	
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        	case REQUEST_CONNECT_DEVICE_SECURE:
        		// When DeviceListActivity returns with a device to connect
        		if (resultCode == Activity.RESULT_OK) {
        			connectDevice(data, true);
        			}
        		break;        		
        	case REQUEST_CONNECT_DEVICE_INSECURE:
        		// When DeviceListActivity returns with a device to connect
        		if (resultCode == Activity.RESULT_OK) {
        			connectDevice(data, false);
        			}
        		break;
        		
        	case REQUEST_ENABLE_BT:
        		// When the request to enable Bluetooth returns
        		if (resultCode == Activity.RESULT_OK) {
        			// Bluetooth is now enabled, so set up a chat session
        			setupChat();
        		} else {
        			
        				// User did not enable Bluetooth or an error occurred
        				Log.d(TAG, "BT not enabled");
        				Toast.makeText(this, "bt_not_enabled_leaving", Toast.LENGTH_SHORT).show();
        				finish();
        		}
        		break;
        }
    }

	//장치연결하기	
	private void connectDevice(Intent data, boolean secure) {
		// TODO Auto-generated method stub
		 // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
	}

	public void doDiscovery(){
		if(mBTAdapter.isDiscovering()){
			
		}else{
			mBTAdapter.startDiscovery();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        Intent serverIntent = null;
	        switch (item.getItemId()) {
	        case R.id.secure_connect_scan:
	            // Launch the DeviceListActivity to see devices and do scan
	            serverIntent = new Intent(this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
	            return true;
	        case R.id.insecure_connect_scan:
	            // Launch the DeviceListActivity to see devices and do scan
	            serverIntent = new Intent(this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
	            return true;
	        case R.id.discoverable:
	            // Ensure this device is discoverable by others
	            ensureDiscoverable();
	            return true;
	        }
	        return false;
	    }
	
	
	class ScrTimer extends Handler{
   	 d_state draw_state = d_state.stop;
   	 public void handleMessage(Message msg){
     		 if(draw_state == d_state.toVisible){
     			if(adt.alpha<1){
     				adt.alpha = adt.alpha + 0.03f;
     				sfgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				sfgView.invalidate();
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			}
     			else{
     				adt.alpha = 1;
     				sfgView.setAlpha(1);
     				sfgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				draw_state = d_state.stop;
     			}
     		}
     		 else if(draw_state == d_state.toInvisible){
     			 if(adt.alpha>0){
     				adt.alpha = adt.alpha - 0.03f;
     				sfgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				sfgView.invalidate();
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			 }
     			 else{
     				adt.alpha = 0;
     				sfgView.setAlpha(0);
     				sfgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				draw_state = d_state.stop;
     			 }
     		 }
     		 else{
     			 ;
     		 }
     	}
    }
}
