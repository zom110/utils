package com.wodan.platform.foundation.util;

import java.util.HashMap;
import java.util.Map;

public class UrlKeyValuePrase {
	
	 /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL){
    	
    	strURL=strURL.trim().toLowerCase();     
      	if(strURL.length()>1)
      	{
      		String[] arrSplit =strURL.split("[?]");
      		if(arrSplit.length>1 && arrSplit[1]!=null){
      			 return arrSplit[1];
      		}
      	}
      	return null;
    }
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL)
    {
    	Map<String, String> mapRequest = new HashMap<String, String>();
   
    	String strUrlParam=TruncateUrlPage(URL);
    	
	    if(strUrlParam==null)
	    {
	        return mapRequest;
	    }
	    
	    String[] arrSplit = strUrlParam.split("[&]");
	    for(String strSplit:arrSplit)
	    {
	          String[] arrSplitEqual=null;         
	          arrSplitEqual= strSplit.split("[=]");
	         
	          //解析出键值
	          if(arrSplitEqual.length > 1){
	              mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
	          }else{
	              if(arrSplitEqual[0]!=""){
	            	  mapRequest.put(arrSplitEqual[0], "");       
	              }
	          }
	    }   
	    return mapRequest;   
    }
    
    public static void main(String[] args) throws Exception {
    	
    	String url = "http://download.jianke.cc/?qr_code=123456";
    	
    	Map<String, String> mapRequest = URLRequest(url);
    	
    	System.out.println(mapRequest.get("qr_code"));
    }
}
