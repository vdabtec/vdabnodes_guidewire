package com.lcrc.af.gw;

import java.util.HashMap;

import com.lcrc.af.AnalysisCompoundData;
import com.lcrc.af.AnalysisEvent;
import com.lcrc.af.AnalysisObject;
import com.lcrc.af.background.RemoteFunctionRunner;
import com.lcrc.af.remote.AnalysisObjectRef;
import com.lcrc.af.remote.RemotePolledAgent;

public class GWActivityAgent extends RemotePolledAgent {
	public static final String GWEVENTLABEL = "GWEvents";
	public static final String REMOTEFUNCTION = "persistEvents";
	public static final String DEFAULT_SOURCE = "GWActivityAgent";
	public static String c_Source = DEFAULT_SOURCE;

	private boolean c_SendZeroCounts = false;
	private String c_EventLabel;
	private HashMap<String,Long> s_map_ActivityCount = new HashMap<String,Long>();

	public GWActivityAgent(){
		super(DEFAULT_SOURCE);
		registerAgent(DEFAULT_SOURCE, this);
	}
	public  GWActivityAgent(String label){ // Adds an additional event label.
		super(DEFAULT_SOURCE);
		c_EventLabel = label;
		registerAgent(DEFAULT_SOURCE, this);
	}
	public static GWActivityAgent instance(){
		return (GWActivityAgent) getAgentInstance(c_Source);
	}
	public static int getAgentStatus(){
		RemotePolledAgent agent = getAgentInstance(c_Source);
		if (agent == null)
			return RemotePolledAgent.UNLOADED;				
		if (agent.isRunning())	
			return RemotePolledAgent.RUNNING;		
		return RemotePolledAgent.LOADED;
	}
	public void sendZeroCounts(){
		c_SendZeroCounts = true;
	}
	
	public static void pushActivityData(String eventName){				
		( (GWActivityAgent)getAgentInstance(c_Source)).incrementActivityMap(eventName);
	}
	private void incrementActivityMap(String eventName){
		Long count = s_map_ActivityCount.get(eventName);
		if (count == null )
			count = Long.valueOf(1L);
		else
			count = Long.valueOf(count.longValue()+1L);	
		s_map_ActivityCount.put(eventName, count);
	}	
	@Override
	public void _flush(){
		// This should be done in a separate thread.
		if (RemoteFunctionRunner.isFunctionRunning(REMOTEFUNCTION)){
			AnalysisObject.logError("GWActivityAgent.flushActivityData()", "Alread pushing events waiting");
		}
		else {
			AnalysisCompoundData acd0;
			AnalysisCompoundData acd1;
			if (c_EventLabel != null){
				acd0 = new AnalysisCompoundData(c_EventLabel);
				acd1 = new AnalysisCompoundData(GWEVENTLABEL);
				acd0.addAnalysisData(acd1);				
			}
			else {
				acd0 = acd1 = new AnalysisCompoundData(GWEVENTLABEL);
			}
			for(String eventName: s_map_ActivityCount.keySet()){
				Long count = s_map_ActivityCount.get(eventName);
				if (count > 0 || c_SendZeroCounts) {
					acd1.addAnalysisData(eventName, count);
				}
			}
			for(String eventName: s_map_ActivityCount.keySet()){
				s_map_ActivityCount.put(eventName,Long.valueOf(0L));
			}
			if (acd1.hasChildData()){
				AnalysisEvent ev = new AnalysisEvent(getVDABSource(), acd0);
				ev.setOrigins(getVDABSource(), "0.0.0.0");
				ev.setEventPropogationLevel(1);
				Integer flushTime = getFlushTimeInMillis();
				if (flushTime > 1000)
					flushTime -= 800;
				
				logInfo("GWActivityAgent._flush()", "Sending Event to Object OBJ="+getTargetRef()+" FLUSHTIME="+flushTime);
				new RemoteFunctionRunner(getTargetRef(),REMOTEFUNCTION,new Object[] {getVDABSource(), new AnalysisEvent[]{ev}},flushTime);
			}
		}
	
	}
	public void logInfo(String scope, String info){
		AnalysisObject.logInfo(scope,info);
	}
	
}
