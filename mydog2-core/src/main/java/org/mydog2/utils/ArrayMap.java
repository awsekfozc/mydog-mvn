package org.mydog2.utils;

import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;


public class ArrayMap<V> extends KeyMap<List<V>> {

	private static final long serialVersionUID = -3443539285123988233L;
	
	public void putItem(String k , V v){
		
		List<V> values = StringUtils.equalsAny("", 
				StringTools.toString( get(k)) ) ? null : get( k );
		if(null == values){
			values = new Vector<>();
			super.put(k, values) ;
		}
		values.add(v) ;
	}
}
