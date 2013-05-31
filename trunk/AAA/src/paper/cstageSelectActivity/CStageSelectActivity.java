package paper.cstageSelectActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;


import paper.data.CStageData;
import paper.data.Stage;
import paper.data.StageData;
import paper.data.StagePolygon;
import com.example.papercult.R;
import bayaba.engine.lib.GameInfo;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;



public class CStageSelectActivity extends Activity {
	enum d_state {toVisible, toInvisible, stop};
	public CSBView csbView;
	public CSBViewMain csbMain;
	public GameInfo gInfo;
	public ListView stageList;
	CStageAdapter adt;
	ScrTimer scrTimer;
	float alp;
	
	// Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

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
    
	
	protected void onCreate(Bundle savedInstanceState) {
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
        
        csbMain = new CSBViewMain( this, gInfo );
        csbView = new CSBView( this, csbMain );
        csbView.setRenderer( new CSBSurfaceClass(csbMain) );
        
        r.addView(csbView);
        
        CStageData.createInstance(this);
		adt = new CStageAdapter(this, CStageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		csbMain.adt = adt;
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		alp = 0;
		stageList.setAlpha(alp);
		
		stageList.setOnTouchListener(new CStageListListener(csbMain, stageList, (int)gInfo.ScreenYsize));
		csbMain.lv = stageList;
		
		FrameLayout.LayoutParams listviewParams = new FrameLayout.LayoutParams((int)((gInfo.ScreenXsize/10)*5.3), (int)gInfo.ScreenYsize);
		listviewParams.leftMargin = (int)((gInfo.ScreenXsize/10)*0.5);
		stageList.setLayoutParams(listviewParams);
		r.addView(stageList);
        setContentView( r );
       
	}
	@Override
	public void onStart(){
		super.onStart();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return;
        }
        
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
//            if (mChatService == null) setupChat();
        }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onResume(){
		super.onResume();
		adt.notifyDataSetChanged();
		alp = 0;
		stageList.setAlpha(alp);
		scrTimer.draw_state = d_state.toVisible;
		scrTimer.sendEmptyMessageDelayed(0, 1000);
		csbMain.startScr();
		int index = stageList.getFirstVisiblePosition() + 2;
		int score = CStageData.getInstance().getStage(index).score;
		csbMain.setBarImg(score);
		csbMain.setSnum(score);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
//                connectDevice(data, true);
            }
            break;
        case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
//                connectDevice(data, false);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
//                setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, "BT not enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }
	
	private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
	
	public void onDestroy(){
		super.onDestroy();
		
		Vector<Stage> stl = CStageData.getInstance().list;

		int size = stl.size()-4;
		Stage writeStg;
		
		if(size < 1)
			return;
		
		try {
			FileOutputStream fos;
			fos = this.openFileOutput("DataList", Context.MODE_PRIVATE);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeInt(size);
			for(int i=0; i<size; i++){
				int index = i + 2;
				writeStg = stl.get(index);
				oos.writeObject(writeStg);
			}
		    oos.flush();
		    oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ScrTimer extends Handler{
   	 d_state draw_state = d_state.stop;
   	 public void handleMessage(Message msg){
     		 if(draw_state == d_state.toVisible){
     			if(alp<1){
     				alp = alp + 0.03f;
     				stageList.setAlpha(alp);
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			}
     			else{
     				alp = 1;
     				stageList.setAlpha(alp);
     				draw_state = d_state.stop;
     			}
     		}
     		 else if(draw_state == d_state.toInvisible){
     			 if(alp>0){
     				alp = alp - 0.03f;
     				stageList.setAlpha(alp);
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			 }
     			 else{
     				alp = 0;
     				stageList.setAlpha(alp);
     				draw_state = d_state.stop;
     			 }
     		 }
     		 else{
     			 ;
     		 }
     	}
    }
}