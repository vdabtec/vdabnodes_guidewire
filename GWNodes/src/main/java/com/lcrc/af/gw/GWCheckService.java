package com.lcrc.af.gw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.lcrc.af.AnalysisEvent;
import com.lcrc.af.constants.IconCategory;
import com.lcrc.af.service.HTTPService;

public class GWCheckService extends HTTPService {
	
	static {
		GWProductPrefix.getEnum();
	}
	private String c_Host;
	private Integer c_Port;
	private Integer c_Product;
	
	// ATTRIBUTE Methods
	// This is the external public host name

	public Integer get_IconCode(){
		return IconCategory.GWNODE_MESSAGE;
	}
	public Integer get_Product(){
		return c_Product;
	}
	public void set_Product(Integer product){

			if(c_Product == null || ((product != null) && !c_Product.equals(product))){
				if (c_Host == null)
					c_Host = "localhost";
				if (c_Port == null){
					switch (product.intValue()){
					case GWProductPrefix.CC:
						c_Port = Integer.valueOf(8080);
						break;
						
					case GWProductPrefix.PC:
						c_Port = Integer.valueOf(8180);
						break;
				
					case GWProductPrefix.BC:
						c_Port = Integer.valueOf(8380);
						break;
						
					case GWProductPrefix.AB:
						c_Port = Integer.valueOf(8580);
						break;
					}
				}
		
			}

		c_Product = product;
	}

	public String get_Host(){
		return c_Host;
	}
	public void set_Host(String host){
		c_Host = host;
	}
	public Integer get_Port(){
		return c_Port;
	}
	public void set_Port(Integer port){
		c_Port = port;
	}
	
	public synchronized void processEvent(AnalysisEvent ev){
		// HACKALERT - just calls the HTTP now - will eventually have it setup
		// for different operations.
		super.processEvent(ev);
	}

	// SUPPORTING Methods --------------------------------------
	public String buildCompleteURL(AnalysisEvent ev){
		StringBuilder sb = new StringBuilder();
		sb.append(GWWebSvcUtility.buildWsUrl(c_Host, c_Port,c_Product));
		// TODO - Need to get this right
		sb.append("/ping");
		return sb.toString();
	}
	public void serviceFailed(AnalysisEvent ev, int code){
		serviceResponse(ev, new AnalysisEvent(this, "ServerCheck" , Boolean.FALSE));	
	}
	public void processReturnStream(AnalysisEvent inEvent, InputStream is){
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = in.readLine()) != null)
				sb.append(line);
			String response = sb.toString();
		
			serviceResponse(inEvent, new AnalysisEvent(this, "ServerCheck" , isThere(response)));
		}
		catch (Exception e){
			this.serviceFailed(inEvent, 3);
		}
	}
	private Boolean isThere(String response){
		return Boolean.valueOf(response.indexOf("2") >= 0);
	}
 }
