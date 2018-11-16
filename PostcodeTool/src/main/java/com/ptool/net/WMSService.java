package com.ptool.net;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.ptool.pojo.MapImageParametersTO;
import com.ptool.service.MTService;

public class WMSService implements Runnable {
	
	private MTService mapTool=null;
	private MapImageParametersTO params=null;
	private HttpURLConnection conn = null;
	private String targetURL="http://geo.stat.fi/geoserver/postialue/wms";
	
	public WMSService(MTService mapTool) {
		this.mapTool=mapTool;
	}
	
	public void run() {
		//send request to WMS server
		//http://geo.stat.fi/geoserver/postialue/wms?
		//send received data (image) to mapTool
		BufferedImage mapImg=null;
		if(params!=null) {
			String urlParameters=params.toString();
			URL url;
			try {
				url = new URL(targetURL);
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
				mapImg=ImageIO.read(in);
				
				in.close();
			} 
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(conn!=null) {
					conn.disconnect();
				}
			}
			
			mapTool.setMapImage(mapImg);
		}
	}
	
	public synchronized void requestMapImage(MapImageParametersTO params) {
		this.params=params;
	}

}
