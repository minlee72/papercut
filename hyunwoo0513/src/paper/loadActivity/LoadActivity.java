package paper.loadActivity;

import paper.data.StageData;
import paper.freeModeActivity.FreeModeActivity;
import paper.stageSelectActivity.StageSelectActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.papercult.R;

public class LoadActivity extends Activity {
	float ScreenXsize;
	float ScreenYsize;
	Button stageModeBtn;
	Button freeModeBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
	    ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
		StageData.createInstance(ScreenXsize, ScreenYsize);
		
		stageModeBtn = (Button)findViewById(R.id.StageModeBtn);
		stageModeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoadActivity.this, StageSelectActivity.class);
				startActivity(intent);
			}
		});
		
		freeModeBtn = (Button)findViewById(R.id.FreeModeBtn);
		freeModeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoadActivity.this, "test..", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoadActivity.this, FreeModeActivity.class);
				startActivity(intent);
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load, menu);
		return true;
	}
}
