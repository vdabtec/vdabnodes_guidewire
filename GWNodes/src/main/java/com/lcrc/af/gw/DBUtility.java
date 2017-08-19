package com.lcrc.af.gw;

import com.lcrc.af.datatypes.AFEnum;
import com.lcrc.af.util.ControlDataBuffer;

public class DBUtility {
	public static int addOrConditions(int noConds, StringBuilder sb, String whereColumn, AFEnum valEnum, ControlDataBuffer cdb){
		String column = whereColumn.toUpperCase();
		for (String whereValue: cdb.getAllSet()){
			Integer code = valEnum.getCode(whereValue);
			if (code == null)	
				continue;			
			if (noConds == 0)
				sb.append(" WHERE ");
			else
				sb.append(" OR ");
			noConds++;

			sb.append(column);
			sb.append(" = ");
			sb.append(code);
		}
		return noConds;
	}
}
