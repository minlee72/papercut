package paper.stageSelectActivity;

import java.util.Vector;

import paper.gameActivity.Stage;

import com.example.papercult.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class StageAdapter extends BaseAdapter {
	Vector<Stage> stageVector = new Vector<Stage>();
	LayoutInflater inflater;
	int scrHeight;
	Context con;
	float alpha;
	
	public StageAdapter(Context context, Vector<Stage> v, int screenheight) {
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
			convertView = inflater.inflate(R.layout.itemlayout, parent, false);
			AbsListView.LayoutParams param = (AbsListView.LayoutParams)convertView.getLayoutParams();
			param.height = scrHeight/4;
			convertView.setLayoutParams(param);
		}
		ImageView img = (ImageView)convertView.findViewById(R.id.item);
		img.setImageResource(stageVector.get(position).titleImage);
		img.setAlpha(alpha);
		return convertView;
	}

}
