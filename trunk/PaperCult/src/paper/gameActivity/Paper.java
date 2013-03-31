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
	Paper(float left, float top, float right, float bottom){
		baseRect.add(new PointF(left,top));
		baseRect.add(new PointF(right,top));
		baseRect.add(new PointF(right,bottom));
		baseRect.add(new PointF(left,bottom));
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
	public void draw(Canvas canvas){
		for(int i=0; i<poly.size(); i++){
			poly.get(i).draw(canvas);
		}
	}
}
