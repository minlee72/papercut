package paper.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Vector;

import android.content.Context;
import android.widget.Toast;



public class CStageData {
	private static CStageData instance = null;
	public Vector<Stage> list = new Vector<Stage>();
	
	private CStageData(Context con) {
		Stage temp = new Stage(0, null);
		temp.name = "";
		temp.score = 0;
		list.add(temp);
		list.add(temp);
		
		int size;
		Stage ust = new Stage();
		FileInputStream fis;
		try {
			fis = con.openFileInput("DataList");
			ObjectInputStream ois = new ObjectInputStream(fis);
			size = ois.readInt();
			for(int i=0; i<size; i++){
				ust = (Stage)ois.readObject();
				list.add(ust);
			}
			ois.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		list.add(temp);
		list.add(temp);
	}
	
	public static void createInstance(Context con){
		if (instance == null)
			instance = new CStageData(con);
	}
	public static CStageData getInstance(){
		return instance;
	}
	public void addStage(Stage stg){
		list.add(list.size()-2, stg);
	}
	public Stage getStage(int index){
		return list.get(index);
	}
}
