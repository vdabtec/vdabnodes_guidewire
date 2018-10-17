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

public class GWMajorVersion {
	public final static int UNKNOWN = 0;
	public final static int VERS7 = 7;
	public final static int VERS8 = 8;
	public final static int VERS9 = 9;
	private static AFEnum s_EnumGWMajorVersion = new AFEnum("GWMajorVersion")
	.addEntry(GWMajorVersion.VERS7, "V7")
	.addEntry(GWMajorVersion.VERS8, "V8")
	.addEntry(GWMajorVersion.VERS9, "V9");
	public static AFEnum getEnum(){
		return s_EnumGWMajorVersion ;
	}
}
