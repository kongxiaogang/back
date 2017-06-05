package com.kongxiaogang.tools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;
import com.kongxiaogang.service.vo.PageResult;
/**
 * 
 * <pre>项目名称：xhcf-back-model    
 * 类名称：JsonUtil    
 * 类描述：   将对象转换成字符串
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月23日 下午3:08:08    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月23日 下午3:08:08    
 * 修改备注：       
 * @version </pre>
 */
public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	/**
	 * <pre>pageResultToJson(将传入的对象转换成特定的字符串)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月23日 下午3:12:29    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月23日 下午3:12:29    
	 * 修改备注： 
	 * @param request
	 * @param model
	 * @return</pre>
	 */
	public static String pageResultToJson(HttpServletRequest request,PageResult pageResult) {
		//callBack 用于jsonp跨域 
		String callBack = request.getParameter("callBack");
		String mavResult = (new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create()).toJson(pageResult);
		LOGGER.debug("返回信息："+mavResult);
		//如果callBack为空 返回 非跨域数据
		if(null == callBack){
			return mavResult;
		}
		return callBack +"(" + mavResult + ")";
	}
	
	/**
	 * <pre>pageResultToJsonByJackSon(将传入的对象转换成json字符串) ，解决json转换map时，日期格式错误。
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年11月21日 下午5:36:16    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年11月21日 下午5:36:16    
	 * 修改备注： 
	 * @param pageResult
	 * @return</pre>
	 */
	public static String pageResultToJsonByJackSon(HttpServletRequest request,PageResult pageResult) {
		try {
			//callBack 用于jsonp跨域 
			String callBack = request.getParameter("callBack");
			ObjectMapper mapper = new ObjectMapper();
			String mavResult = mapper.writeValueAsString(pageResult);
			LOGGER.debug("返回信息："+mavResult);
			//如果callBack为空 返回 非跨域数据
			if(null == callBack){
				return mavResult;
			}
			return callBack +"(" + mavResult + ")";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}