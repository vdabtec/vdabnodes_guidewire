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

import com.lcrc.af.datatypes.AFEnum;

public class GWMsgDelegateStatusType {
	public final static int MINSTATUS = 10001;
	public final static int DONE = 10001;
	public final static int ERROR = 10002;
	public final static int ERRORHOLD = 10003;
	public final static int INPROGRESS = 10004;
	public final static int INITIAL = 10005;
	public final static int SKIPPED = 10006;
	public final static int TAKEN = 10007;
	public final static int MAXSTATUS = 10007;
	private static AFEnum s_GWMsgDelegateStatusType = new AFEnum("GWMsgDelegateStatusType")
	.addEntry(DONE, "Done")
	.addEntry(ERROR, "Error")
	.addEntry(ERRORHOLD, "ErrorHold")	
	.addEntry(INPROGRESS, "InProgress")	
	.addEntry(INITIAL, "Initial")	
	.addEntry(SKIPPED, "Skipped")	
	.addEntry(TAKEN, "Taken")	
	;

	public static AFEnum getEnum(){
		return s_GWMsgDelegateStatusType ;
	}
	
}
