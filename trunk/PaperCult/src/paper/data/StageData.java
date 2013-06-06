package paper.data;

import java.util.Vector;

import com.example.papercult.R;


public class StageData {
	private static StageData instance = null;
	public Vector<Stage> list = new Vector<Stage>();
	
	private StageData(float scrWidth, float scrHeight) {
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
		
		StagePolygon fstp5 = new StagePolygon();
		fstp5.add(1.0f, 0.0f);
		fstp5.add(1.0f, 0.2f);
		fstp5.add(0.2f, 1.0f);
		fstp5.add(0.0f, 1.0f);
		fstp5.add(0.0f, 0.8f);
		fstp5.add(0.8f, 0.0f);
		
		Stage fst5 = new Stage(3, fstp5);
		fst5.name = "죽부인";
		fst5.score = 0;
		fst5.locked = false;
		list.add(fst5);
		
		StagePolygon fstp6 = new StagePolygon();
		fstp6.add(0.0f, 0.42f);
		fstp6.add(0.41f, 0.42f);
		fstp6.add(0.41f, 0.0f);
		fstp6.add(0.5f, 0.0f);
		fstp6.add(0.5f, 0.42f);
		fstp6.add(0.41f, 0.59f);
		fstp6.add(0.0f, 0.59f);
		
		Stage fst6 = new Stage(4, fstp6);
		fst6.name = "부메랑";
		fst6.score = 0;
		fst6.locked = false;
		list.add(fst6);
		
		StagePolygon fstp7 = new StagePolygon();
		fstp7.add(0.0f, 0.35f);
		fstp7.add(0.85f, 0.35f);
		fstp7.add(1.0f, 0.5f);
		fstp7.add(0.7f, 0.5f);
		fstp7.add(0.5f, 0.7f);
		fstp7.add(0.0f, 0.7f);
		
		Stage fst7 = new Stage(3, fstp7);
		fst7.name = "비행기1";
		fst7.score = 0;
		fst7.locked = false;
		list.add(fst7);
		
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
		
		StagePolygon stp1 = new StagePolygon();
		stp1.add((float)0.18, (float)0.48);
		stp1.add((float)0.46, (float)0.48);
		stp1.add((float)0.54, (float)0.31);
		stp1.add((float)0.86, (float)0.19);
		stp1.add((float)0.96, (float)0.48);
		stp1.add((float)1, (float)0.48);
		stp1.add((float)1, (float)0.56);
		stp1.add((float)0.03, (float)0.73);
		stp1.add((float)0, (float)0.73);
		stp1.add((float)0, (float)0.66);
		
		Stage st1 = new Stage(5, stp1);
		st1.name = "비행기";
		st1.score = 0;
		st1.locked = false;
		list.add(st1);
		
		StagePolygon stp2 = new StagePolygon();
		stp2.add((float)0.3, 0);
		stp2.add((float)0.37, (float)-0.03);
		stp2.add((float)0.4, (float)-0.1);
		stp2.add((float)0.49, (float)-0.07);
		stp2.add((float)0.57, (float)-0.1);
		stp2.add((float)0.6, (float)-0.03);
		stp2.add((float)0.7, (float)0);
		stp2.add((float)0.515, (float)1);
		stp2.add((float)0.485, (float)1);

		Stage st2 = new Stage(5, stp2);
		st2.name = "횃불";
		st2.score = 0;
		st2.locked = false;
		list.add(st2);
		
		StagePolygon stp3 = new StagePolygon();
		stp3.add((float)0.37, (float)0.36);
		stp3.add((float)0.45, (float)0.23);
		stp3.add((float)0.5, (float)0.2);
		stp3.add((float)0.62, (float)0.26);
		stp3.add((float)0.62, (float)0.47);
		stp3.add((float)0.86, (float)0.47);
		stp3.add((float)0.90, (float)0.40);
		stp3.add((float)1, (float)0.475);
		stp3.add((float)0.79, (float)0.60);
		stp3.add((float)0.62, (float)0.60);
		stp3.add((float)0.5, (float)0.47);
		stp3.add((float)0.5, (float)0.357);
		stp3.add((float)0.40, (float)0.41);

		Stage st3 = new Stage(7, stp3);
		st3.name = "오리";
		st3.score = 0;
		st3.locked = false;
		list.add(st3);
		
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
		
		StagePolygon stp5 = new StagePolygon();
		stp5.add(0, (float)0.1923);
		stp5.add((float)0.3, (float)0.19);
		stp5.add((float)0.5, (float)0);
		stp5.add((float)0.7, (float)0.19);
		stp5.add((float)1, (float)0.19);
		stp5.add((float)0.84, (float)0.34);
		stp5.add((float)0.15, (float)0.34);
		
		Stage st5 = new Stage(4, stp5);
		st5.name = "왕관";
		st5.score = 0;
		st5.locked = false;
		list.add(st5);
		
		StagePolygon stp6 = new StagePolygon();
		stp6.add((float)0.4, (float)0.28);
		stp6.add((float)0.62, (float)0.23);
		stp6.add((float)0.65, (float)0.35);
		stp6.add((float)0.62, (float)0.36);
		stp6.add((float)0.62, (float)1);
		stp6.add((float)0.5, (float)1);
		stp6.add((float)0.5, (float)0.4);
		stp6.add((float)0.42, (float)0.40);
		
		Stage st6 = new Stage(5, stp6);
		st6.name = "망치";
		st6.score = 0;
		st6.locked = false;
		list.add(st6);
		
		StagePolygon stp7 = new StagePolygon();
		stp7.add((float)0.42, (float)0.08);
		stp7.add((float)0.5, (float)0.081);
		stp7.add((float)0.62, (float)0.20);
		stp7.add((float)0.62, (float)0.8);
		stp7.add((float)0.7, (float)0.8);
		stp7.add((float)0.7, (float)0.92);
		stp7.add((float)0.62, (float)0.92);
		stp7.add((float)0.5, (float)0.8);
		stp7.add((float)0.5, (float)0.20);
		stp7.add((float)0.42, (float)0.20);
		
		Stage st7 = new Stage(5, stp7);
		st7.name = "잠망경";
		st7.score = 0;
		st7.locked = false;
		list.add(st7);
		
		StagePolygon stp8 = new StagePolygon();
		stp8.add((float)0.30, (float)0.3);
		stp8.add((float)0.5, (float)0.10);
		stp8.add((float)0.69, (float)0.30);
		stp8.add((float)0.60, (float)0.39);
		stp8.add((float)0.60, (float)0.60);
		stp8.add((float)0.5, (float)0.5);
		stp8.add((float)0.4, (float)0.60);
		stp8.add((float)0.4, (float)0.39);
		
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
		
		list.add(dum);
		list.add(dum);
		
		for (int i=2; i<list.size()-2; i++){
			list.get(i).loadStage(new Paper(scrWidth, scrHeight));
		}
	}
	
	public static void createInstance(float scrWidth, float scrHeight){
		if (instance == null)
			instance = new StageData(scrWidth, scrHeight);
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

