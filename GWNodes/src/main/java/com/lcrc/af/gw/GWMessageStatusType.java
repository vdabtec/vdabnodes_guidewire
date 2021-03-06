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

public class GWMessageStatusType {
	public final static int UNSENT = 1;
	public final static int INFLIGHT = 2;
	public final static int  ERROR = 3;
	public final static int  RETRYABLE  = 4;

	private static AFEnum s_GWMessageStatusType = new AFEnum("GWMessageStatusType")
	.addEntry(UNSENT, "Unsent")
	.addEntry(INFLIGHT, "Inflight")
	.addEntry(ERROR, "Error")
	.addEntry(RETRYABLE, "Retryable")
	;
	public static AFEnum getEnum(){
		return s_GWMessageStatusType ;
	}
	
}
