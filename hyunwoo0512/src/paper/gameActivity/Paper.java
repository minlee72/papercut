package paper.gameActivity;

import java.util.Vector;
import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * �������� ���̸� ǥ���ϱ� ���� Ŭ����
 * @author 2013-03-27 ������
 *
 */
public class Paper {
	Vector<Vector<Polygon>> history = new Vector<Vector<Polygon>>();
	/**
	 * �������� ���� �⺻ ������ ����� �׸��� ���� �簢�� ����
	 */
	Polygon baseRect = new Polygon();
	
	
	/**
	 * �������� ���� ���̸� ǥ���ϱ� ���� Ŭ����
	 */
	Vector<Polygon> poly = new Vector<Polygon>();
	
	
	/**
	 * �������� ���� ���̸� ǥ���ϱ� ���� Ŭ����
	 */
	Vector<Polygon> base = new Vector<Polygon>();
	
	
	/**
	 * ������
	 * @param left ������ �»�� ��ǥ�� ���� ��ǥ
	 * @param top ������ �»�� ��ǥ�� ��� ��ǥ
	 * @param right ������ ���ϴ� ��ǥ�� ���� ��ǥ
	 * @param bottom ������ ���ϴ� ��ǥ�� �ϴ� ��ǥ
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
	 * ��ġ �Է¿� ���� ���̸� ������ �ҷ����� �Լ�
	 * @param touchStart ��ġ �Է��� ������
	 * @param touchEnd ��ġ �Է��� ����
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
	 * ��ġ �Է��� ������ ���̰� ������ �������� �ҷ����� �Լ�
	 */
	public void foldEnd (){
		Vector<Polygon> temp = new Vector<Polygon>(base);
		history.add(temp);
		base.clear();
		base = (Vector<Polygon>)poly.clone();
	}
	
	/**
	 * ���̸� ó�� �簢�� ������� ����
	 */
	public void reset (){
		poly.removeAllElements();
		poly.add(baseRect);
		base = (Vector<Polygon>)poly.clone();
	}
	
	/**
	 * ���̸� �信 �׸��� �Լ�
	 * @param canvas �׸����� �ϴ� ���� ĵ���� ��ü
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
