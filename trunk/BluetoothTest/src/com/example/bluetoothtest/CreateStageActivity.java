package com.example.bluetoothtest;

import java.util.Vector;
import paper.stage.Stage;
import paper.stage.StageData;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateStageActivity extends Activity{
	Vector<PointF>pt;
	EditText xEt;
	EditText yEt;
	EditText stageNameEt;
	Button addBtn;
	Button createBtn;
	TextView tv;
	StageData mStageData;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stage);
        
        xEt = (EditText)findViewById(R.id.inputX);
        yEt = (EditText)findViewById(R.id.inputY);
        tv = (TextView)findViewById(R.id.pt_info);
        stageNameEt = (EditText)findViewById(R.id.stage_name);
        pt = new Vector<PointF>();
        mStageData = StageData.getInstance();
        
        addBtn = (Button)findViewById(R.id.addPt);
        addBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float x = Float.parseFloat(xEt.getText().toString());
				float y = Float.parseFloat(yEt.getText().toString());
				String vw = new String();
				vw.concat(""+xEt.getText().toString()+" / "
						+yEt.getText().toString()+"\n");
				tv.setText(vw);
				
				pt.add(new PointF(x,y));
				Toast.makeText(CreateStageActivity.this, "success", Toast.LENGTH_SHORT).show();
			}
		});
        

        
        createBtn = (Button)findViewById(R.id.createStage);
        createBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(true) Log.e("TAG", "check");
				Stage st = new Stage(stageNameEt.getText().toString(), 5, pt);
				mStageData.setStage(st);
				Intent intent = new Intent(CreateStageActivity.this, MainActivity.class);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
        
    }
	@Override
	protected void onDestroy(){
		super.onDestroy();
		setResult(RESULT_OK);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
