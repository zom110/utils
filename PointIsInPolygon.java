package com.wodan.platform.foundation.util;

import java.util.ArrayList;

/**
 * @ClassName: PointInPolygon 
 * @Description: 点在多边形区域判断
 * @author yanqb
 * @date 2015-8-14 上午10:11:09 
 * @history 
 * 
 */

public class PointIsInPolygon {
	
	public static void main(String[] args) {

		  Double px = 119.211128; //经度
		  Double py = 26.082322; //纬度
		 
		  PointIsInPolygon p = new PointIsInPolygon();
		  
		  Boolean b = p.isPointInPolygon(px, py, "119.41726,26.075789;119.41844,26.01726;119.418483,26.016372;119.416723,26.012043;119.422948,26.006813;119.455477,25.970513;119.473159,25.981933;119.508521,26.028217;119.534613,26.074638;119.552466,26.070166;119.574954,26.049656;119.645826,26.054424;119.669172,26.08773;119.603941,26.133664;119.501974,26.151848;119.462406,26.11856;119.438717,26.097519;119.428567,26.087441;119.421165,26.079576");
		  if(b){
			  System.out.println("123");
		  }
		  
	}
	
	
	
	
	
	/**
	 * 
	 * @param px 经度
	 * @param py 纬度
	 * @param polygonPosition 坐标串  经度,纬度
	 * @return
	 */
	public boolean isPointInPolygon(Double px, Double py,
			   String polygonPosition) {
		if(polygonPosition == null || polygonPosition.length() == 0){
			return false;
		}
		String[] arr = polygonPosition.split(";");
		
		
		if(arr ==null ||  arr.length == 0 ){
			return false;
		}
		
		Integer len = arr.length;
		
		ArrayList<Double> polygonXA = new ArrayList<Double>();
		ArrayList<Double> polygonYA = new ArrayList<Double>();
		
		for(Integer i =0 ; i < len; i++){
			
			String[] str = arr[i].split(",");
			
			if(str!= null &&  str.length%2 == 0){
				polygonXA.add(Double.valueOf(str[0]));
				polygonYA.add(Double.valueOf(str[1]));
			}else{
				return false;
			}
			
		}
		return isInPolygon(px, py,polygonXA,polygonYA);
	}
	
	/**
	 * 算法1
	 * @param px 经度
	 * @param py 纬度
	 * @param polygonXA 多边形经度列表
	 * @param polygonYA 多边形纬度列表
	 * @return
	 */
	public boolean isInPolygon(Double px, Double py,
			   ArrayList<Double> polygonXA, ArrayList<Double> polygonYA) {
			
	    int nCross = 0;
	    int len = polygonXA.size();
	    for(int i = 0; i < len; i++) {
	       Double cx1 = polygonXA.get(i);
		   Double cy1 = polygonYA.get(i);
		   Double cx2 = polygonXA.get((i + 1)%len);
		   Double cy2 = polygonYA.get((i + 1)%len);
			   
	      // 求解 y=p.y 与 p1 p2 的交点
	      // p1p2 与 y=p0.y平行
	      if (cy1 == cy2)
	        continue;
	      // 交点在p1p2延长线上
	      if (py < Math.min(cy1, cy2))
	        continue;
	      // 交点在p1p2延长线上
	      if (py >= Math.max(cy1, cy2))
	        continue;
	      // 求交点的 X 坐标
	      double x = (double) (py - cy1) * (double) (cx2 - cx1)
	          / (double) (cy2 - cy1) + cx1;
	      // 只统计单边交点
	      if (x > px)
	        nCross++;
	    }
	    return (nCross % 2 == 1);
	  }
	
	/**
	 * 算法2
	 * @param px 经度
	 * @param py 纬度
	 * @param polygonXA 多边形经度列表
	 * @param polygonYA 多边形纬度列表
	 * @return
	 */
	public boolean isPointInPolygon(Double px, Double py,
	   ArrayList<Double> polygonXA, ArrayList<Double> polygonYA) {
		
		boolean isInside = false;
		Double ESP = 1e-9;
		int count = 0;
		Double linePoint1x;
		Double linePoint1y;
		Double linePoint2x = Double.valueOf(-180);
		Double linePoint2y;
		
		linePoint1x = px;
		linePoint1y = py;
		linePoint2y = py;

		for(int i = 0; i < polygonXA.size() - 1; i++) {
		   Double cx1 = polygonXA.get(i);
		   Double cy1 = polygonYA.get(i);
		   Double cx2 = polygonXA.get(i + 1);
		   Double cy2 = polygonYA.get(i + 1);
		   if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
			   return true;
		   }
		   if (Math.abs(cy2 - cy1) < ESP) {
			   continue;
		   }
		
		   if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x,
		     linePoint2y)) {
			   	if (cy1 > cy2)
		   			count++;
		   	}else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y,
		     linePoint2x, linePoint2y)) {
		   		if (cy2 > cy1)
		   			count++;
		   	} else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x,
		     linePoint1y, linePoint2x, linePoint2y)) {
		   		count++;
		   	}
		}
		if (count % 2 == 1) {
			isInside = true;
		}

		return isInside;
	 }

	private Double Multiply(Double px0, Double py0, Double px1, Double py1,
	   Double px2, Double py2) {
		 return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
	 }

	 private boolean isPointOnLine(Double px0, Double py0, Double px1,
			 Double py1, Double px2, Double py2) {
		 
		 boolean flag = false;
		 Double ESP = 1e-9;
		 if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP)
		    && ((px0 - px1) * (px0 - px2) <= 0)
		    && ((py0 - py1) * (py0 - py2) <= 0)) {
		   flag = true;
		 }
		 return flag;
	 }

	 private boolean isIntersect(Double px1, Double py1, Double px2, Double py2,
	   Double px3, Double py3, Double px4, Double py4) {
		 boolean flag = false;
		 Double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
		 if (d != 0) {
			 Double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3))/ d;
			 Double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1))/ d;
			 if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
				 flag = true;
			 }
		 }
		 return flag;
	 }
}


