package paper.cstageSelectActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;
import paper.data.CStageData;
import paper.data.Paper;
import paper.data.Stage;
import paper.data.SurfaceClass;
import paper.sfx.Music;
import paper.sfx.Sound;


import com.example.papercult.R;
import bayaba.engine.lib.GameInfo;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
	public static Activity AActivity;
	boolean sendMode;
	Stage stageToSend = null;
	
	BluetoothService bluetooth = null;
    
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_SEND_FAIL = 6;
    public static final int MESSAGE_CONNECT_FAIL = 7;

    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";
    public static final String TOAST = "toast";

    private static final int CREAT_ACTION = 1;
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private String mConnectedDeviceName = null;
    private String connectedAddress = new String();

    private BluetoothAdapter mBluetoothAdapter = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        AActivity = CStageSelectActivity.this;
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        scrTimer = new ScrTimer();
        FrameLayout r = new FrameLayout(this);
        
        csbMain = new CSBViewMain( this, gInfo );
        csbView = new CSBView( this, csbMain );
        csbView.setRenderer( new SurfaceClass(csbMain) );
        
        csbMain.st = scrTimer;
        
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
	public void onPause(){
		super.onPause();
		Music.createMusicPause(this);
	}
	public void onBackPressed(){
		super.onBackPressed();
		Music.createMusicPause(this);
		Music.init();
	}
	public void onResume(){
		super.onResume();
		Music.createMusicStart(this);
		if(csbMain.scrAnime){
			adt.notifyDataSetChanged();
			alp = 0;
			stageList.setAlpha(alp);
			csbMain.startScr();
			int index = stageList.getFirstVisiblePosition() + 2;
			int score = CStageData.getInstance().getStage(index).score;
			csbMain.setBarImg(score);
			csbMain.setSnum(score);
			csbMain.scrAnime = false;
		}
	}	
	public void stageSendStart(Stage st){
		sendMode = true;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		stageToSend = st;
		
        if (mBluetoothAdapter == null) {
            return;
        }
		if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } 
		else {
			stageSendSet();
        }
	}
	public void stageSendSet(){
		if(bluetooth == null){
			bluetooth = new BluetoothService(this, mHandler);
			bluetooth.start();
		}
		Intent serverIntent = null;
		serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
	}
	
	public void stageRecvStart(){
		sendMode = false;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return;
        }
		if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } 
		else {
			stageRecvSet();
		}
	}
	public void stageRecvSet(){
		if(bluetooth == null){
			bluetooth = new BluetoothService(this, mHandler);
			bluetooth.start();
		}
		if (mBluetoothAdapter.getScanMode() !=
	            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
	            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
	            startActivity(discoverableIntent);
	    }
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            if (resultCode == Activity.RESULT_OK) {
            	String address = data.getExtras()
                        .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    if(connectedAddress.equals(address)){
                    	bluetooth.write(stageToSend);
                    }else{
                    	bluetooth.connect(device);
                    }
            }
            break;
        case REQUEST_ENABLE_BT:
            if (resultCode == Activity.RESULT_OK) {
            	if(sendMode == true)
            		stageSendSet();
            	else
            		stageRecvSet();
            } 
            else {
            }
        }
    }
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                case BluetoothService.STATE_CONNECTED:

                    break;
                case BluetoothService.STATE_CONNECTING:
                	
                    break;
                case BluetoothService.STATE_LISTEN:
                	
                	break;
                case BluetoothService.STATE_NONE:
                    break;
                }
                break;
            case MESSAGE_WRITE:
            	FrameLayout frame = (FrameLayout)View.inflate(CStageSelectActivity.this, R.layout.sendtoast_layout, null);
				Toast toast = new Toast(CStageSelectActivity.this);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setView(frame);
				toast.show();
				Sound.playToastSound(CStageSelectActivity.this);
            	sendMode = false;
                break;
            case MESSAGE_READ:
            	float scrWidth = gInfo.ScreenXsize;
            	float scrHeight = gInfo.ScreenYsize;
            	Paper p = new Paper(scrWidth, scrHeight);
            	Stage st = (Stage) msg.obj;
            	st.setInnerPolygon(st.stagePolygon.getPolygon(p));
            	st.pll = p.lineLength;
            	st.score = 0;
            	CStageData.getInstance().addStage(st);
            	FrameLayout frame1 = (FrameLayout)View.inflate(CStageSelectActivity.this, R.layout.revievetoast_layout, null);
				Toast toast1 = new Toast(CStageSelectActivity.this);
				toast1.setDuration(Toast.LENGTH_SHORT);
				toast1.setView(frame1);
				toast1.show();
				Sound.playToastSound(CStageSelectActivity.this);
            	sendMode = false;
            	adt.notifyDataSetChanged();
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                connectedAddress =  msg.getData().getString(DEVICE_ADDRESS);
                if(sendMode){
                	bluetooth.write(stageToSend);
                }
                break;
            case MESSAGE_TOAST:
            	Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                        Toast.LENGTH_SHORT).show();
            	connectedAddress = "";
                break;
            case MESSAGE_SEND_FAIL:
            	FrameLayout frame2 = (FrameLayout)View.inflate(CStageSelectActivity.this, R.layout.sendfailtoast_layout, null);
				Toast toast2 = new Toast(CStageSelectActivity.this);
				toast2.setDuration(Toast.LENGTH_SHORT);
				toast2.setView(frame2);
				toast2.show();
				Sound.playToastSound(CStageSelectActivity.this);
            	break;
            case MESSAGE_CONNECT_FAIL:
            	FrameLayout frame3 = (FrameLayout)View.inflate(CStageSelectActivity.this, R.layout.connectfailtoast_layout, null);
				Toast toast3 = new Toast(CStageSelectActivity.this);
				toast3.setDuration(Toast.LENGTH_SHORT);
				toast3.setView(frame3);
				toast3.show();
				Sound.playToastSound(CStageSelectActivity.this);
            	break;
            }
        }
    };
	public void onDestroy(){
		super.onDestroy();
		if (bluetooth != null) bluetooth.stop();
		
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
	     	}
	    }
}