package com.example.android.BluetoothChat;

import paper.stage.StageData;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
	Context con;
	StageData mStageData;
	LayoutInflater Inflater;
	int layout;
	
	public MyListAdapter(Context context, int layout){
		this.con = context;
		mStageData = StageData.getInstance();
		Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = layout;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mStageData.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mStageData.list.get(position);
	}

	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = Inflater.inflate(layout, parent, false);			
		}
		TextView name = (TextView)convertView.findViewById(R.id.stage_name2);
		name.setText(mStageData.list.get(position).name);
		TextView limit = (TextView)convertView.findViewById(R.id.stage_limit);
		Integer temp= mStageData.list.get(position).limit;
		limit.setText(temp.toString());
		
		if(mStageData.list.get(position).selected == true){
			convertView.setBackgroundColor(Color.LTGRAY);
		}
		else{
			convertView.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

}
