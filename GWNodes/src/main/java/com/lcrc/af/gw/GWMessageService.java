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

public class GWMessageService extends DBQueryService__A {
	static {
		GWMajorVersion.getEnum();
		GWProductPrefix.getEnum();
	}
	private Integer c_Product;
	private Integer c_Version;
//	private HashMap<String,GWDestData> c_PreviousDestData_map; 
	public class GWDestData {
		String c_Dest;
		int c_Unsent;
		int c_Inflight;
		int c_Error;
		int c_Retryable;
		public GWDestData(String dest){
			c_Dest = dest;
		}
		public void setUnsent(int unsent){
			c_Unsent = unsent;
		}
		public void setInflight(int inflight){
			c_Inflight = inflight;
		}
		public void setError(int error){
			c_Error = error;
		}
		public void setRetryable(int retryable){
			c_Retryable = retryable;
		}
		public String getDest(){
			return c_Dest;
		}
		public Integer getUnsent(){
			return c_Unsent;
		}
		public Integer getInflight(){
			return c_Inflight;
		}
		public Integer getError(){
			return c_Error;
		}
		public Integer getRetryable(){
			return c_Retryable;	
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
	}
	public AnalysisDataDef def_MonitorStatuses(AnalysisDataDef theDataDef){
		String[] statusLabels = GWMessageStatusType.getEnum().getAllLabels();

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
		StringBuilder sb = new StringBuilder("SELECT DESTINATIONID,COUNT(*),STATUS from ");
		String tspace = get_TableSpace(); 
		if (tspace != null && tspace.length() > 0)
			sb.append(tspace).append(".");
		sb.append(GWProductPrefix.getEnum().getLabel(c_Product));
		sb.append("_MESSAGE ");
		if (!c_cdb_MonitorStatuses.isEmpty()){
			int noConds = DBUtility.addOrConditions(0, sb, "STATUS",  GWMessageStatusType.getEnum(), c_cdb_MonitorStatuses);
		}
		sb.append( " GROUP BY DESTINATIONID,STATUS");
		return sb.toString();
	}

	@Override
	public AnalysisEvent[] processResultSet(ResultSet rs) {

		HashMap<String,GWDestData> newDataMap = new HashMap<String,GWDestData>(); 
		int noRows = 0;
		try {
			while (rs.next() ){
				noRows++;					
				String dest = "Dest"+rs.getString(1);
				GWDestData destData = newDataMap.get(dest);
				if (destData == null){
					destData = new GWDestData(dest);
					newDataMap.put(dest, destData);
				}
				int count  = rs.getInt(2);
				int statusCode = rs.getInt(3);
				
				if (!isStatusEnabled(statusCode))
					continue;
				
				switch (statusCode){
				case GWMessageStatusType.UNSENT:	
					destData.setUnsent(count);
					break;

				case GWMessageStatusType.INFLIGHT:
					destData.setInflight(count);
					break;	
					
				case GWMessageStatusType.ERROR:
					destData.setError(count);
					break;

				case GWMessageStatusType.RETRYABLE:
					destData.setRetryable(count);
					break;	
				}
			}
		} 
		catch (Exception e){
			setError("Failed reading data returned from query e>"+e);
			return null;
		}

		AnalysisCompoundData acd = new AnalysisCompoundData("GWMessages");
		for (Entry<String,GWDestData> destEntry :newDataMap.entrySet()){
			String dest = destEntry.getKey();
			AnalysisCompoundData acd2 = new AnalysisCompoundData(dest);
			acd.addAnalysisData(acd2);
			if (isStatusEnabled(GWMessageStatusType.UNSENT))
				acd2.addAnalysisData("Unsent", destEntry.getValue().getUnsent());
			if (isStatusEnabled(GWMessageStatusType.INFLIGHT))
				acd2.addAnalysisData("Inflight", destEntry.getValue().getInflight());
			if (isStatusEnabled(GWMessageStatusType.ERROR))
				acd2.addAnalysisData("Error", destEntry.getValue().getError());
			if (isStatusEnabled(GWMessageStatusType.RETRYABLE))
				acd2.addAnalysisData("Retryable", destEntry.getValue().getRetryable());
		}
		return new AnalysisEvent[]{ new AnalysisEvent(this, acd)};
	}
	private boolean isStatusEnabled(int code){
		if (c_cdb_MonitorStatuses.isEmpty())
			return true;
		return c_cdb_MonitorStatuses.isSet(GWMessageStatusType.getEnum().getLabel(code));
		
	}
}
