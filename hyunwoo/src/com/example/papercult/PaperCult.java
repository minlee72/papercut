package com.example.papercult;

import java.util.Vector;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Path;
import android.view.Menu;

public class PaperCult extends Activity {
	PaperView pv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pv = new PaperView(this);
		setContentView(pv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_paper_cult, menu);
		return true;
	}

}
