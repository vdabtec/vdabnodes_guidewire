/*LICENSE*
 * Copyright (C) 2013 - 2018 MJA Technology LLC 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.lcrc.af.gw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import vdab.api.node.HTTPService_A;

import com.lcrc.af.AnalysisEvent;
import com.lcrc.af.constants.IconCategory;

public class GWCheckService extends HTTPService_A {
	
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
		sb.append("/ping");
		return sb.toString();
	}
	public void serviceFailed(AnalysisEvent ev, int code){
		serviceResponse(ev, new AnalysisEvent(this, "ServerCheck" , Boolean.FALSE));	
	}
	public void processReturnStream(AnalysisEvent inEvent, int msgCode, InputStream is){
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
