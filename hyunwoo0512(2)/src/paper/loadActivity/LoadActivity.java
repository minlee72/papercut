package paper.loadActivity;

import paper.data.StageData;
import paper.gameActivity.GameActivity;
import paper.stageSelectActivity.StageSelectActivity;

import com.example.papercult.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class LoadActivity extends Activity {
	float ScreenXsize;
	float ScreenYsize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
	    ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
		StageData.createInstance(ScreenXsize, ScreenYsize);
		
		Intent intent = new Intent(this, StageSelectActivity.class);
		this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load, menu);
		return true;
	}
}
