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

public class GWProductPrefix {
	public final static int UNKNOWN = 0;
	public final static int CC = 1;
	public final static int BC = 2;
	public final static int PC = 3;
	public final static int AB = 4;
	private static AFEnum s_EnumGWProductPrefix = new AFEnum("GWProductPrefix")
	.addEntry(GWProductPrefix.CC, "CC")
	.addEntry(GWProductPrefix.BC, "BC")	
	.addEntry(GWProductPrefix.PC, "PC")	
	.addEntry(GWProductPrefix.AB, "AB");
	public static AFEnum getEnum(){
		return s_EnumGWProductPrefix ;
	}
}
