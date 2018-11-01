package com.ptool.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ptool.pojo.Postcode;

public class NetUtil {
	//http://geo.stat.fi/geoserver/postialue/wfs?request=GetFeature&typeNames=postialue:pno_meri_2018&outputFormat=json
	private static NetUtil instance;
	
	private NetUtil() {
		
	}
	
	public static synchronized NetUtil getInstance() {
		if(instance==null) {
			instance=new NetUtil();
		}
		return instance;
	}
	
	public JSONObject getPostcodeJSON(){
		HttpURLConnection conn = null;
		JSONObject json=null;
		String targetURL="http://geo.stat.fi/geoserver/postialue/wfs";
		String urlParameters="request=GetFeature&typeNames=postialue:pno_meri_2018&outputFormat=json";
		
		try {
			URL url=new URL(targetURL);
			
			conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", "" +Integer.toString(urlParameters.getBytes().length));
			conn.setRequestProperty("Content-Language", "en-US");
			conn.setUseCaches (false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			DataOutputStream out=new DataOutputStream(conn.getOutputStream());
			out.writeBytes(urlParameters);
			out.flush();
			out.close();
			
			InputStream in=conn.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			StringBuffer response=new StringBuffer();
			String line=null;
			while((line=reader.readLine())!=null) {
				response.append(line);
			}
			in.close();
			JSONParser parser=new JSONParser();
			json=(JSONObject) parser.parse(response.toString());
		} 
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(conn!=null) {
				conn.disconnect();
			}
		}
		return json;
	}
	
}
