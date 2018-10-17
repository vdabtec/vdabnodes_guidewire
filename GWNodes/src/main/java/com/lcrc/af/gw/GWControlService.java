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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import com.lcrc.af.AnalysisDataDef;
import com.lcrc.af.AnalysisEvent;
import com.lcrc.af.AnalysisService;
import com.lcrc.af.AnalysisTrigger;
import com.lcrc.af.constants.TriggerCode;
import com.lcrc.af.constants.IconCategory;
import com.lcrc.af.constants.SpecialText;
// POINT THESE TO THE VERSION OF THE STUBS 
import com.lcrc.af.gw.client.stubs.vdabapi.v8.ControlUIInfo;
import com.lcrc.af.gw.client.stubs.vdabapi.v8.IVDABControlAPI;
import com.lcrc.af.gw.client.stubs.vdabapi.v8.IVDABControlAPIPortType;
import com.lcrc.af.util.ControlDataBuffer;


public class GWControlService extends AnalysisService {

	static {
		GWProductPrefix.getEnum();
	}
	private String c_Host;
	private Integer c_Port;
	private String c_User;
	private String c_Password;
	private String c_Command;
	private Integer c_Product;
	private ControlUIInfo c_Info;
	IVDABControlAPIPortType c_VDABAPIPort;

	// ATTRIBUTES ---------------------------------------------------
	// ATTRIBUTE Methods
	
	public Integer get_IconCode(){
		return IconCategory.GWNODE_MESSAGE;
	}
	public Integer get_Product(){
		return c_Product;
	}
	public void set_Product(Integer product){
		if (isRegistered()){
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
	public String get_User(){
		return c_User;
	}
	public void set_User( String user){
		c_User = user;
	}
	public String get_Password(){
		return c_Password;
	}
	public void set_Password( String password){
		c_Password = password;
	}
	private String c_ControlArea;
	public String get_ControlArea(){
		return c_ControlArea;
	}
	public void set_ControlArea( String area){
		boolean changed =  c_ControlArea != null && area!= null  && !c_ControlArea.equals(area);
		c_ControlArea = area;
		if (changed){
			c_Command = null;
			c_Parameter= null;
			c_cdb_SelectedScopeObjs.clear();
		}
		
	}
	public AnalysisDataDef def_ControlArea(AnalysisDataDef theDataDef){
		if (c_VDABAPIPort!= null){
			try {
			String[] objNames =  c_VDABAPIPort.getAvailableObjects().split(",");
			if ( objNames.length > 0)
				theDataDef.setAllPickValues( objNames);	
			}
			catch (Exception e){}
		}
		return theDataDef;
	}
	public String get_Command(){
		
		return c_Command;
	}
	public void set_Command( String cmd){
		boolean changed =  c_Command != null && cmd != null  && !c_Command.equals(cmd);
		c_Command = cmd;
		if (c_VDABAPIPort != null && c_ControlArea != null )
			try {
			c_Info =  c_VDABAPIPort.getCommandUIInfo(c_ControlArea, c_Command);
			}
			catch (Exception e){}
		
		if (changed){
			c_Parameter = null;
			c_cdb_SelectedScopeObjs.clear();
		}
	}
	public AnalysisDataDef def_Command(AnalysisDataDef theDataDef){
		if (c_VDABAPIPort!= null && c_ControlArea != null){
			try{
				String[] cmds =  c_VDABAPIPort.getAvailableCommands(c_ControlArea).split(",");
				if ( cmds.length > 0)
					theDataDef.setAllPickValues(cmds);	
			}
			catch (Exception e){}
		}
		return theDataDef;
	}
	private ControlDataBuffer c_cdb_SelectedScopeObjs = new ControlDataBuffer("SelectedScopeObj");
	public String get_Scope(){
		if(c_cdb_SelectedScopeObjs.isEmpty())
			return null;
		return c_cdb_SelectedScopeObjs.getAllSet(","); 
	}
	public void set_Scope( String scope){
		// Multitple attributes, probably read from xml config
		if (scope.contains(",")){
			c_cdb_SelectedScopeObjs.setAll(scope,","); 	
		} 
		// Clear command from option picker
		else if (scope.equals(SpecialText.CLEAR)){
			c_cdb_SelectedScopeObjs.clear();
		}
		// One value to add.
		else {
			c_cdb_SelectedScopeObjs.set(scope);
		}
	}
	public AnalysisDataDef def_Scope(AnalysisDataDef theDataDef){
		
		if (c_Info != null){
			if (c_Info.getScopeOpts() != null) {
				ArrayList<String> l = new ArrayList<String>();
				if(!c_cdb_SelectedScopeObjs.isEmpty())
					l.add(SpecialText.CLEAR);
				String[] scopeObjs =  c_Info.getScopeOpts().split(",");
				for (String scopeObj: scopeObjs){
					if (!c_cdb_SelectedScopeObjs.isSet(scopeObj))
						l.add(scopeObj);
					}
					theDataDef.setAllPickValues(l.toArray(new String[l.size()]));
			}
			else {
				theDataDef.disable();
			}
		}
		else {
			theDataDef.disable();
		}
		return theDataDef;

	}
	private String c_Parameter;
	public String get_Parameter(){
		return c_Parameter;
	}
	public void set_Parameter( String args){
		c_Parameter = args;
	}
	public AnalysisDataDef def_Parameter(AnalysisDataDef theDataDef){	
		if (c_Info != null){	
			if (c_Info.getNoArgs() <= 0){
				theDataDef.disable();
			}
			else if (c_Info.getParamOpts() != null) {
				String[] args =  c_Info.getParamOpts().split(",");
				if ( args.length > 0)
					theDataDef.setAllPickValues(args);	
			}

		}
		else {
			theDataDef.disable();
		}
		return theDataDef;
	}
	public void _start(){
		String url = null;
		try {
			url = GWWebSvcUtility.buildWsUrl(c_Host, c_Port,c_Product);
			c_VDABAPIPort = getPort(url);
			if (c_Command != null && c_ControlArea != null)
				c_Info =  c_VDABAPIPort.getCommandUIInfo(c_ControlArea, c_Command);
			super._start();
		}
		catch (Exception e){
			setError("Unable to connect to the GW Instance at "+url+" e>"+e);
			_disable();
		}
		
	}
	public synchronized void processEvent(AnalysisEvent ev){	
		if (c_VDABAPIPort != null && get_Command() != null){
			
			try {
			int retVal = c_VDABAPIPort.execute(c_ControlArea, c_Command, get_Scope(), c_Parameter);
			if (retVal == 0) //TRYIT - Changed to have 0 be success
				publish(new AnalysisTrigger(this, TriggerCode.COMPLETED));	
			else
				publish(new AnalysisTrigger(this, TriggerCode.FAILED, retVal));	
			}
			catch (Exception e){			
				publish(new AnalysisTrigger(this, TriggerCode.FAILED, 12));	
				
			}

		}
	}	
	public IVDABControlAPIPortType getPort( String url) throws MalformedURLException{
		StringBuilder sb = new StringBuilder(url);
		sb.append("/ws/gw/acc/vdab/web/IVDABControlAPI");	
		IVDABControlAPI lcService = new IVDABControlAPI(
				new URL(sb.toString()),
				new QName(
						"http://example.com/gw/acc/vdab/web/IVDABControlAPI",
						"IVDABControlAPI")
				);
		IVDABControlAPIPortType lcPort = lcService.getIVDABControlAPISoap11Port();
		Map<String, Object> requestContext = ( (BindingProvider) lcPort)
				.getRequestContext();
		requestContext.put(BindingProvider.USERNAME_PROPERTY, (c_User == null ? "su": c_User));
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, (c_Password == null ? "gw": c_Password));
		return lcPort;
	}

}
