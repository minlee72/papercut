package com.example.papercult;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		float ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        float ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
		setContentView(new TestView(this, ScreenXsize, ScreenYsize));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
