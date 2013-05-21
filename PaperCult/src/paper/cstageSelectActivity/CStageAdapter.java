package paper.cstageSelectActivity;

import java.util.Vector;

import paper.gameActivity.Stage;

import com.example.papercult.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CStageAdapter extends BaseAdapter {
	Vector<Stage> stageVector = new Vector<Stage>();
	LayoutInflater inflater;
	int scrHeight;
	Context con;
	float alpha;
	
	public CStageAdapter(Context context, Vector<Stage> v, int screenheight) {
		stageVector = v;
		scrHeight = screenheight;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		con = context;
		alpha=0;
	}

	@Override
	public int getCount() {
		return stageVector.size();
	}

	@Override
	public Stage getItem(int position) {
		return stageVector.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = inflater.inflate(R.layout.citemlayout, parent, false);
			AbsListView.LayoutParams param = (AbsListView.LayoutParams)convertView.getLayoutParams();
			param.height = scrHeight/4;
			convertView.setLayoutParams(param);
		}
		ImageView clear = (ImageView)convertView.findViewById(R.id.citemclear);
		Typeface ft = Typeface.createFromAsset(con.getAssets(), "font.ttf");
		TextView name = (TextView)convertView.findViewById(R.id.citemname);
		name.setTypeface(ft);
		name.setText("dfdfdfdf");
		/*
		if((position==0)||(position==1)||(position==stageVector.size()-1)||position==stageVector.size()-2){
			clear.setImageResource(R.drawable.s_invisible);
		}
		else if(stageVector.get(position).score > 80)
			clear.setImageResource(stageVector.get(position).titleClearImage);
		else
			clear.setImageResource(stageVector.get(position).titleImage);
		*/
		//clear.setAlpha(alpha);
		return convertView;
	}

}