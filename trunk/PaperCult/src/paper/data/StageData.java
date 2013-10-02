package paper.data;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import android.content.Context;

import com.example.papercult.R;


public class StageData {
	private static StageData instance = null;
	public Vector<Stage> list = new Vector<Stage>();
	
	private StageData(float scrWidth, float scrHeight, Context con) {
		StagePolygon dumP = new StagePolygon();
		dumP.add(0, 0);
		dumP.add((float)1, (float)0);
		dumP.add((float)0, (float)1);

		Stage dum = new Stage(4, dumP);
		dum.name ="";
		dum.score = 0;
		dum.locked = true;
		
		list.add(dum);
		list.add(dum);
		
		StagePolygon fstp0 = new StagePolygon();
		fstp0.add(0.0f, 0.0f);
		fstp0.add(1.0f, 0.0f);
		fstp0.add(0.0f, 1.0f);
		
		Stage fst0 = new Stage(1, fstp0);
		fst0.name = "연습";
		fst0.score = 0;
		fst0.locked = false;
		list.add(fst0);
		
		StagePolygon fstp1 = new StagePolygon();
		fstp1.add(0f, 0f);
		fstp1.add(0.5f, 0f);
		fstp1.add(0.5f, 0.5f);
		fstp1.add(0, 0.5f);
		
		Stage fst1 = new Stage(2, fstp1);
		fst1.name = "네모";
		fst1.score = 0;
		fst1.locked = false;
		list.add(fst1);
		
		StagePolygon fstp2 = new StagePolygon();
		fstp2.add(0.5f, 0.5f);
		fstp2.add(0.5f, 0f);
		fstp2.add(0f, 0.5f);
		
		Stage fst2 = new Stage(3, fstp2);
		fst2.name = "직각 삼각형";
		fst2.score = 0;
		fst2.locked = false;
		list.add(fst2);
		
		StagePolygon fstp3 = new StagePolygon();
		fstp3.add(0f, 0.3f);
		fstp3.add(1.0f, 0.7f);
		fstp3.add(1.0f, 0.3f);
		fstp3.add(0.7f, 0f);
		fstp3.add(0.25f, 0f);
		
		Stage fst3 = new Stage(5, fstp3);
		fst3.name = "타코";
		fst3.score = 0;
		fst3.locked = false;
		list.add(fst3);
		
		StagePolygon stp4 = new StagePolygon();
		stp4.add((float)0.25, (float)0.25);
		stp4.add((float)0.5, 0);
		stp4.add((float)1, (float)0.5);
		stp4.add((float)0.75, (float)0.75);
		
		Stage st4 = new Stage(4, stp4);
		st4.name = "편지 봉투";
		st4.score = 0;
		st4.locked = false;
		list.add(st4);
		
		StagePolygon stp0 = new StagePolygon();		
		stp0.add(0, 0);	
		stp0.add((float)0.54, (float)0.223);
		stp0.add((float)0.8, (float)0.218);
		stp0.add((float)0.86, (float)0.357);
		stp0.add((float)0.356, (float)0.866);
		stp0.add((float)0.223, (float)0.813);
		stp0.add((float)0.223, (float)0.532);
		
		Stage st0 = new Stage(3, stp0);
		st0.name ="팽이";
		st0.score = 0;
		st0.locked = false;
		list.add(st0);
		
		StagePolygon fstp5 = new StagePolygon();
		fstp5.add(1.0f, 0.0f);
		fstp5.add(1.0f, 0.2f);
		fstp5.add(0.2f, 1.0f);
		fstp5.add(0.0f, 1.0f);
		fstp5.add(0.0f, 0.8f);
		fstp5.add(0.8f, 0.0f);
		
		StagePolygon sttp3 = new StagePolygon();
		sttp3.add(0.0f, 1.0f);
		sttp3.add(0.6f, 1.0f);
		sttp3.add(0.71f, 0.71f);
		sttp3.add(0.3f, 0.3f);
		sttp3.add(0.0f, 0.42f);
		
		Stage sttps3 = new Stage(3, sttp3);
		sttps3.name = "다이아몬드";
		sttps3.score = 0;
		sttps3.locked = false;
		list.add(sttps3);
		
		Stage fst5 = new Stage(3, fstp5);
		fst5.name = "죽부인";
		fst5.score = 0;
		fst5.locked = false;
		list.add(fst5);
		
		StagePolygon fstp7 = new StagePolygon();
		fstp7.add(0.0f, 0.35f);
		fstp7.add(0.85f, 0.35f);
		fstp7.add(1.0f, 0.5f);
		fstp7.add(0.7f, 0.5f);
		fstp7.add(0.5f, 0.7f);
		fstp7.add(0.0f, 0.7f);
		
		StagePolygon stp2 = new StagePolygon();
		stp2.add((float)0.3, 0);
		stp2.add((float)0.37, (float)-0.03);
		stp2.add((float)0.4, (float)-0.1);
		stp2.add((float)0.495, (float)-0.075);
		stp2.add((float)0.57, (float)-0.1);
		stp2.add((float)0.6, (float)-0.035);
		stp2.add((float)0.7, (float)0);
		stp2.add((float)0.515, (float)1);
		stp2.add((float)0.485, (float)1);

		Stage st2 = new Stage(5, stp2);
		st2.name = "횃불";
		st2.score = 0;
		st2.locked = false;
		list.add(st2);
		
		Stage fst7 = new Stage(3, fstp7);
		fst7.name = "비행기1";
		fst7.score = 0;
		fst7.locked = false;
		list.add(fst7);
		
		StagePolygon stp1 = new StagePolygon();
		stp1.add((float)0.183, (float)0.4802);
		stp1.add((float)0.466, (float)0.4802);
		stp1.add((float)0.5463, (float)0.3104);
		stp1.add((float)0.86, (float)0.1923);
		stp1.add((float)0.9662, (float)0.4802);
		stp1.add((float)1, (float)0.4802);
		stp1.add((float)1, (float)0.5634);
		stp1.add((float)0.0319, (float)0.7394);
		stp1.add((float)0, (float)0.7394);
		stp1.add((float)0, (float)0.662);
		
		Stage st1 = new Stage(5, stp1);
		st1.name = "비행기2";
		st1.score = 0;
		st1.locked = false;
		list.add(st1);
		
		StagePolygon fstp4 = new StagePolygon();
		fstp4.add(1.0f, 0.7f);
		fstp4.add(0.8f, 0.9f);
		fstp4.add(0.37f, 0.9f);
		fstp4.add(0.3f, 0.7f);
		
		Stage fst4 = new Stage(6, fstp4);
		fst4.name = "사다리꼴";
		fst4.score = 0;
		fst4.locked = false;
		list.add(fst4);
		
		StagePolygon stp3 = new StagePolygon();
		stp3.add((float)0.37, (float)0.36);
		stp3.add((float)0.459, (float)0.236);
		stp3.add((float)0.5, (float)0.2);
		stp3.add((float)0.627, (float)0.267);
		stp3.add((float)0.627, (float)0.475);
		stp3.add((float)0.865, (float)0.475);
		stp3.add((float)0.904, (float)0.403);
		stp3.add((float)1, (float)0.475);
		stp3.add((float)0.798, (float)0.602);
		stp3.add((float)0.6267, (float)0.602);
		stp3.add((float)0.5, (float)0.475);
		stp3.add((float)0.5, (float)0.357);
		stp3.add((float)0.407, (float)0.415);

		Stage st3 = new Stage(7, stp3);
		st3.name = "오리";
		st3.score = 0;
		st3.locked = false;
		list.add(st3);
		
		
		StagePolygon stp5 = new StagePolygon();
		stp5.add(0, (float)0.1923);
		stp5.add((float)0.3, (float)0.1923);
		stp5.add((float)0.5, (float)0);
		stp5.add((float)0.7, (float)0.1923);
		stp5.add((float)1, (float)0.1923);
		stp5.add((float)0.8432, (float)0.345);
		stp5.add((float)0.1568, (float)0.345);
		
		Stage st5 = new Stage(4, stp5);
		st5.name = "왕관";
		st5.score = 0;
		st5.locked = false;
		list.add(st5);
		
		StagePolygon stp6 = new StagePolygon();
		stp6.add((float)0.4, (float)0.2818);
		stp6.add((float)0.6242, (float)0.2312);
		stp6.add((float)0.6559, (float)0.3501);
		stp6.add((float)0.6270, (float)0.3611);
		stp6.add((float)0.6270, (float)1);
		stp6.add((float)0.5, (float)1);
		stp6.add((float)0.5, (float)0.4);
		stp6.add((float)0.4265, (float)0.4042);
		
		Stage st6 = new Stage(5, stp6);
		st6.name = "망치";
		st6.score = 0;
		st6.locked = false;
		list.add(st6);
		
		StagePolygon stp7 = new StagePolygon();
		stp7.add((float)0.4215, (float)0.081);
		stp7.add((float)0.5, (float)0.081);
		stp7.add((float)0.6274, (float)0.2054);
		stp7.add((float)0.6274, (float)0.8);
		stp7.add((float)0.7, (float)0.8);
		stp7.add((float)0.7, (float)0.9281);
		stp7.add((float)0.6274, (float)0.9281);
		stp7.add((float)0.5, (float)0.8);
		stp7.add((float)0.5, (float)0.2054);
		stp7.add((float)0.4215, (float)0.2054);
		
		Stage st7 = new Stage(5, stp7);
		st7.name = "잠망경";
		st7.score = 0;
		st7.locked = false;
		list.add(st7);
		
		StagePolygon stp8 = new StagePolygon();
		stp8.add((float)0.3026, (float)0.3015);
		stp8.add((float)0.5, (float)0.1050);
		stp8.add((float)0.6979, (float)0.3015);
		stp8.add((float)0.605, (float)0.392);
		stp8.add((float)0.605, (float)0.603);
		stp8.add((float)0.5, (float)0.5);
		stp8.add((float)0.4, (float)0.603);
		stp8.add((float)0.4, (float)0.392);
		
		Stage st8 = new Stage(4, stp8);
		st8.name = "가오리";
		st8.score = 0;
		st8.locked = false;
		list.add(st8);
		
		StagePolygon sttp1 = new StagePolygon();
		sttp1.add(0.0f, 0.63f);
		sttp1.add(0.0f, 0.46f);
		sttp1.add(0.9f, 0.46f);
		sttp1.add(1.0f, 0.5f);
		sttp1.add(0.45f, 0.5f);
		sttp1.add(0.0f, 0.72f);
		sttp1.add(-0.07f, 0.68f);
		
		Stage sttps1 = new Stage(6, sttp1);
		sttps1.name = "제트기";
		sttps1.score = 0;
		sttps1.locked = false;
		list.add(sttps1);
		
		StagePolygon sttp2 = new StagePolygon();
		sttp2.add(0.21f, 0.13f);
		sttp2.add(0.38f,  -0.13f);
		sttp2.add(0.48f, 0.3f);
		sttp2.add(0.5f, 0.32f);
		sttp2.add(0.69f, 0.32f);
		sttp2.add(0.69f, 0.43f);
		sttp2.add(0.27f, 0.63f);
		sttp2.add(0.0f, 0.3f);
		sttp2.add(0.0f, 0.0f);
		sttp2.add(0.23f, 0.23f);
		
		Stage sttps2 = new Stage(3, sttp2);
		sttps2.name = "글라이더";
		sttps2.score = 0;
		sttps2.locked = false;
		list.add(sttps2);
		
		list.add(dum);
		list.add(dum);
		
		for (int i=2; i<list.size()-2; i++){
			list.get(i).loadStage(new Paper(scrWidth, scrHeight));
		}
		
		int size;
		int saveScr;
		FileInputStream fis;
		try {
			fis = con.openFileInput("ScoreList");
			ObjectInputStream ois = new ObjectInputStream(fis);
			size = ois.readInt();
			for(int i=0; i<size; i++){
				saveScr =  ois.readInt();
				list.get(i+2).score = saveScr;
			}
			ois.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createInstance(float scrWidth, float scrHeight, Context con){
		if (instance == null)
			instance = new StageData(scrWidth, scrHeight, con);
	}
	public static StageData getInstance(){
		return instance;
	}
	public Stage getStage(int index){
		return list.get(index);
	}
	public void setStageLock(){
		for(int i=2; i<=list.size()-6; i=i+3){
			if( (list.get(i).score > 69)
					&&(list.get(i+1).score > 69)
					&&(list.get(i+2).score > 69) )
			{
				list.get(i+3).locked = false;
				list.get(i+4).locked = false;
				list.get(i+5).locked = false;
			}
			else{
				list.get(i+3).locked = true;
				list.get(i+4).locked = true;
				list.get(i+5).locked = true;
			}
		}
	}
}

