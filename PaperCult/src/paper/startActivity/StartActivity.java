package paper.startActivity;

import paper.data.StageData;
import paper.gameActivity.GameActivity;
import paper.stageSelectActivity.StageSelectActivity;

import com.example.papercult.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class StartActivity extends Activity {
	float ScreenXsize;
	float ScreenYsize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citemlayout);	
		ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
	    ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
		StageData.createInstance(ScreenXsize, ScreenYsize);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load, menu);
		return true;
	}
}
