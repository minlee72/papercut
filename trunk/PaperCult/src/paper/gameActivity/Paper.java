package paper.gameActivity;

import java.util.Vector;
import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * 접혀지는 종이를 표현하기 위한 클래스
 * @author 2013-03-27 폴더폰
 *
 */
public class Paper {
	Vector<Vector<Polygon>> history = new Vector<Vector<Polygon>>();
	/**
	 * 접혀지지 않은 기본 종이의 모습을 그리기 위한 사각형 정보
	 */
	Polygon baseRect = new Polygon();
	
	
	/**
	 * 접혀지는 동안 종이를 표현하기 위한 클래스
	 */
	Vector<Polygon> poly = new Vector<Polygon>();
	
	
	/**
	 * 접혀지기 전의 종이를 표현하기 위한 클래스
	 */
	Vector<Polygon> base = new Vector<Polygon>();
	
	
	/**
	 * 생성자
	 * @param left 종이의 좌상단 좌표중 좌측 좌표
	 * @param top 종이의 좌상단 좌표중 상단 좌표
	 * @param right 종이의 우하단 좌표중 우측 좌표
	 * @param bottom 종이의 우하단 좌표중 하단 좌표
	 */
	Paper(float scrWidth, float scrHeight){ 
        float lineLength = Math.min(scrWidth, scrHeight); 
        lineLength = lineLength * (float)0.8; 
        baseRect.pointVector.add(new PointF( 0+((scrWidth-lineLength)/2), 0+((scrHeight-lineLength)/2) )); 
        baseRect.pointVector.add(new PointF( scrWidth-((scrWidth-lineLength)/2), 0+((scrHeight-lineLength)/2) )); 
        baseRect.pointVector.add(new PointF( scrWidth-((scrWidth-lineLength)/2), scrHeight-((scrHeight-lineLength)/2) )); 
        baseRect.pointVector.add(new PointF( 0+((scrWidth-lineLength)/2), scrHeight-((scrHeight-lineLength)/2) )); 
        reset(); 
} 
	
	/**
	 * 터치 입력에 따라 종이를 접는중 불려지는 함수
	 * @param touchStart 터치 입력의 시작점
	 * @param touchEnd 터치 입력의 끝점
	 */
	public void foldStart (PointF touchStart, PointF touchEnd){
		poly.clear();
		
		for(int i=0; i<base.size(); i++){
			Polygon cut = base.get(i).cutPolygon(touchStart, touchEnd);    
			Polygon pull = base.get(i).pullPolygon(touchStart, touchEnd);  
			
			if(cut != null)
				poly.add(cut);
			if(pull != null)
				poly.add(pull);
		}
	}
	
	/**
	 * 터치 입력이 끝나고 종이가 완전히 접혀질때 불려지는 함수
	 */
	public void foldEnd (){
		Vector<Polygon> temp = new Vector<Polygon>(base);
		history.add(temp);
		base.clear();
		base = (Vector<Polygon>)poly.clone();
	}
	
	/**
	 * 종이를 처음 사각형 모습으로 복구
	 */
	public void reset (){
		poly.removeAllElements();
		poly.add(baseRect);
		base = (Vector<Polygon>)poly.clone();
	}
	
	/**
	 * 종이를 뷰에 그리는 함수
	 * @param canvas 그리고자 하는 뷰의 캔버스 객체
	 */
	public void draw(Canvas canvas, int ARGB){
		for(int i=0; i<poly.size(); i++){
			poly.get(i).draw(canvas, ARGB);
		}
	}
	
	public PointF getLeftTop(){
		PointF p = baseRect.pointVector.get(0);
		return p;
	}
	
	public float getWidth(){
		float left = baseRect.pointVector.get(0).x;
		float right = baseRect.pointVector.get(1).x;
		float width = right - left;
		return width;
	}
	
	public float getHeight(){
		float top = baseRect.pointVector.get(0).y;
		float bot = baseRect.pointVector.get(3).y;
		float height = bot - top;
		return height;
	}
	
	public void initHistory(){
		history.clear();
	}
}
