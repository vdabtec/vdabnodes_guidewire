package com.lcrc.af.gw;

public class GWWebSvcUtility {
	public static String buildWsUrl(String host, Integer port, Integer product){
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(GWProductPrefix.getEnum().getLabel(product).toLowerCase());
		return sb.toString();
	}
}
