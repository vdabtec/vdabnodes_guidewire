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

import java.util.ArrayList;

import com.lcrc.af.AnalysisData;
import com.lcrc.af.AnalysisEvent;
import com.lcrc.af.AnalysisObject;
import com.lcrc.af.constants.PermanentIDs;
import com.lcrc.af.datatypes.AFContainerLocation;
import com.lcrc.af.datatypes.AFTimeInterval;
import com.lcrc.af.remote.AnalysisObjectRef;
import com.lcrc.af.remote.RemoteContainer;

public class GWModelingClient {
	private static String s_Location;
	private static RemoteContainer s_Container;

	public static void setVDABLocation(String loc){
		s_Location = loc;
		try {
			AFContainerLocation afLoc = new AFContainerLocation(loc);
			s_Container = new RemoteContainer(afLoc);
		}
		catch(Exception e){
			AnalysisObject.logError("GWModelingClient.setVDABLocation()", "Unable to set VDAB location e>"+e);
		}
	}
	public static AnalysisData[] getAllMetricsForInterval(String intervalName) {
		AFTimeInterval[] intervals = (AFTimeInterval[]) s_Container.executeFunction("getIntervalsForName", new Object[]{intervalName});
		ArrayList<AnalysisData> adList = new ArrayList<AnalysisData>();
		for (AFTimeInterval interval: intervals){
			AnalysisEvent ev = (AnalysisEvent) s_Container.executeFunction("callProcess", new Object[]{"calcMetrics", new AnalysisEvent("GWModelingClient",interval.getAnalysisData())});
			adList.add(ev.getAnalysisData());
		}
		return adList.toArray(new AnalysisData[adList.size()]);
	}	
	public static AnalysisEvent getMetricsForInterval(String intervalName) {
		AnalysisEvent ev = (AnalysisEvent) s_Container.executeFunction("callProcess", new Object[]{"calcMetrics", new AnalysisEvent("GWModelingClient","Interval",intervalName)});
		return ev;
	}
	/* DEBUG MAIN
	public static void main(String[] args) throws Exception {
		setVDABLocation("www.mjatech.net:31205");
		AnalysisData[] ads = getAllMetricsForInterval(args[0]);
			for (AnalysisData ad: ads){
				System.out.println("--------------------");
				System.out.println(ad);
		}
	}
	*/
}
