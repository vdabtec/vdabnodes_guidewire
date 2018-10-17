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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.lcrc.af.AnalysisCompoundData;
import com.lcrc.af.AnalysisDataDef;
import com.lcrc.af.AnalysisEvent;
import com.lcrc.af.constants.IconCategory;
import com.lcrc.af.constants.SpecialText;
import com.lcrc.af.service.DBQueryService__A;
import com.lcrc.af.util.ControlDataBuffer;

public class GWMsgDelegateService extends DBQueryService__A {
	static {
		GWMajorVersion.getEnum();
		GWProductPrefix.getEnum();
	}
	private Integer c_Product;
	private Integer c_Version;
//	private HashMap<String,GWDelegateDestData> c_PreviousDestData_map; 
	public class GWDelegateDestData {
		String c_Dest;
		int[] valsByStatus = new int[GWMsgDelegateStatusType.MAXSTATUS-GWMsgDelegateStatusType.MINSTATUS];
		public GWDelegateDestData(String dest){
			c_Dest = dest;
		}
			public void setByStatus(int status, int no){
			int off = status - GWMsgDelegateStatusType.MINSTATUS;
			
			if (off < valsByStatus.length && off >= 0)
				valsByStatus[off] = no;
		}

		public Integer getByStatus(int status){
			int off = status - GWMsgDelegateStatusType.MINSTATUS;
			
			if (off < valsByStatus.length && off >= 0)
				return Integer.valueOf(valsByStatus[off]);
			return null;
		}
	}
	// ATTRIBUTES ---------------------------------------------------
	// ATTRIBUTE Methods
	
	public Integer get_IconCode(){
		return IconCategory.GWNODE_MESSAGE;
	}
	public Integer get_Product(){
		return c_Product;
	}
	public void set_Product(Integer product){
		c_Product = product;
	}
	public Integer get_Version(){
		return c_Version;
	}
	public void set_Version(Integer vers){
		c_Version = vers;
	}
	private ControlDataBuffer c_cdb_MonitorStatuses = new ControlDataBuffer("MonitorStatuses");
	public String get_MonitorStatuses(){
		if(c_cdb_MonitorStatuses.isEmpty())
			return null;
		return c_cdb_MonitorStatuses.getAllSet(","); 
	}	
	public void set_MonitorStatuses(String statuses){
		// Multitple attributes, probably read from xml config
		if (statuses.contains(",")){
			c_cdb_MonitorStatuses.setAll(statuses,","); 	
		} 
		// Clear command from option picker
		else if (statuses.equals(SpecialText.CLEAR)){
			c_cdb_MonitorStatuses.clear();
		}
		// One value to add.
		else {
			c_cdb_MonitorStatuses.set(statuses);
		}
// 		setError("SQL="+buildQuery(null));
	}
	public AnalysisDataDef def_MonitorStatuses(AnalysisDataDef theDataDef){
		String[] statusLabels = GWMsgDelegateStatusType.getEnum().getAllLabels();

		ArrayList<String> l = new ArrayList<String>();
		if (!c_cdb_MonitorStatuses.isEmpty())
			l.add(SpecialText.CLEAR);
		for (String label: statusLabels){
			if  (!c_cdb_MonitorStatuses.isSet(label))
				l.add(label);
		}
		theDataDef.setAllPickValues(l.toArray(new String[l.size()]));
		return theDataDef;
	}
	@Override
	public String buildQuery(AnalysisEvent ae) {
		StringBuilder sb = new StringBuilder("SELECT DESTINATION,COUNT(*),STATUS FROM ");
		String tspace = get_TableSpace(); 
		if (tspace != null && tspace.length() > 0)
			sb.append(tspace).append(".");
		sb.append("PCX_MDWORKITEM_EXT ");
		if (!c_cdb_MonitorStatuses.isEmpty()){
			int noConds = DBUtility.addOrConditions(0, sb, "STATUS",  GWMsgDelegateStatusType.getEnum(), c_cdb_MonitorStatuses);
		}
		sb.append(" GROUP BY DESTINATION,STATUS");
		return sb.toString();
	}

	@Override
	public AnalysisEvent[] processResultSet(ResultSet rs) {

		HashMap<String,GWDelegateDestData> newDataMap = new HashMap<String,GWDelegateDestData>(); 
		int noRows = 0;
		try {
			while (rs.next() ){
				noRows++;					
				String dest = "Dest"+rs.getString(1);
				GWDelegateDestData destData = newDataMap.get(dest);
				if (destData == null){
					destData = new GWDelegateDestData(dest);
					newDataMap.put(dest, destData);
				}
				int count  = rs.getInt(2);
				int statusCode = rs.getInt(3);
				destData.setByStatus(statusCode, count);
			}
		} 
		catch (Exception e){
			setError("Failed reading data returned from query e>"+e);
			return null;
		}
		
		AnalysisCompoundData acd = new AnalysisCompoundData("GWDelegateMessages");
		for (Entry<String,GWDelegateDestData> destEntry :newDataMap.entrySet()){
			String dest = destEntry.getKey();
			AnalysisCompoundData acd2 = new AnalysisCompoundData(dest);
			acd.addAnalysisData(acd2);
			for ( int n = GWMsgDelegateStatusType.MINSTATUS ; n <= GWMsgDelegateStatusType.MAXSTATUS ; n++){
				Integer count = destEntry.getValue().getByStatus(n);
				if (count != null)
					acd2.addAnalysisData(GWMsgDelegateStatusType.getEnum().getLabel(n), count);
			}
		}
		return new AnalysisEvent[]{ new AnalysisEvent(this, acd)};
	}
}
