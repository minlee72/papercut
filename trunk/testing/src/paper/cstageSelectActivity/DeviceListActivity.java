package paper.cstageSelectActivity;

import android.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;

public class DeviceListActivity extends Activity{
	
	//Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	
	//Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}
	
	
}
