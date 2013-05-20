package paper.stage;

import java.util.Vector;

import android.content.Context;
import android.graphics.PointF;
import android.widget.Toast;

public class StageData {
	private static StageData instance;
	public Vector<Stage>list;
	Context con;
	private StageData(Context con){
		this.con = con;
		list = new Vector<Stage>();
		PointF pt1 = new PointF ((float)0, (float)0);
		PointF pt2 = new PointF ((float)0.5, (float)0);
		PointF pt3 = new PointF ((float)0.5, (float)0.5);
		PointF pt4 = new PointF ((float)0, (float)0.5);
		
		Vector<PointF> pt = new Vector<PointF>();
		pt.add(pt1);
		pt.add(pt2);
		pt.add(pt3);
		pt.add(pt4);
		Stage st = new Stage("sample1", 5, pt);
		list.add(st);
		
		PointF pt5 = new PointF((float)0, (float)0);
		PointF pt6 = new PointF((float)1, (float)0);
		PointF pt7 = new PointF((float)0, (float)1);
		
		Vector<PointF> pt_ = new Vector<PointF>();
		pt_.add(pt5);
		pt_.add(pt6);
		pt_.add(pt7);
		
		Stage st_ = new Stage("sample2", 5, pt);
		list.add(st_);
	}
	
	public static StageData getInstance(Context con){
		if(instance == null){
			instance = new StageData(con);
		}
		return instance;
	}
	public static StageData getInstance(){
		return instance;
	}
	
	public void setStage(Stage stage){
		list.add(stage);
	}
	
	public void removeStage(int index){
		list.remove(index);
	}
	
	public void ToastStage(){
		for(int i=0; i<list.size(); i++){
			String str = new String();
			for(int k=0; k<list.get(i).pt.size(); k++){
				Toast.makeText(con, ""+list.get(i).pt.get(k).x+","+
						list.get(i).pt.get(k).y+"\n", Toast.LENGTH_LONG).show();
//			
			}
			
		}
	}
}
