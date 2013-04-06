package paper.stageSelectActivity;

import java.util.Vector;

import paper.gameActivity.Stage;

import com.example.papercult.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class StageAdapter extends BaseAdapter {
	Context con;
	Vector<Stage> stageVector = new Vector<Stage>();
	LayoutInflater inflater;
	
	public StageAdapter(Context context, Vector<Stage> v) {
		stageVector = v;
		con = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		}
		ImageView img = (ImageView)convertView.findViewById(R.id.item);
		img.setImageResource(stageVector.get(position).titleImage);
		
		return convertView;
	}

}
