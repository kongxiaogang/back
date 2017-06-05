package com.kongxiaogang.tools;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>项目名称：请求参数转换成对象  
 * 类名称：RequestParamersUtil    
 * 类描述：    
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月21日 下午1:58:39    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月21日 下午1:58:39    
 * 修改备注：       
 * @version </pre>
 */
public class RequestParamersUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestParamersUtil.class);
	public static Map<String,Object> paramerToMap(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				returnMap.put(key, values.length == 1 ? values[0].trim() : values);
			}
		}
		return returnMap;
	}
}