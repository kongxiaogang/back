package com.kongxiaogang.service.vo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ServiceVO<T> {
	
	/**
	 * @属性说明：当前执行服务
	 **/
	private String curService;
	
	/**
	 * @属性说明：服务执行链表
	 **/
	private LinkedList<String> services = new LinkedList<String>();
	
	/**
	 * @属性说明：执行过程需要的业务参数列表
	 **/
	private Map<String, Object> busiParam = new HashMap<String, Object>();
	
	/**
	 * @属性说明：执行结果，true-表示成功，false-表示失败
	 **/
	private boolean runResult = false;
	
	private T resultObject = null;
	
	public void setResultObject(T resultObject) {
		this.resultObject = resultObject;
	}

	private String message = "";
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 *   @生成时间： 2013-12-17 下午05:33:23
	 *   @方法说明： 设置服务执行
	 *   @参数：srvCode-服务编号
	 *   @返回值： 
	 *   @异常：
	 **/
	public void setService(String srvCode){
		this.services.add(srvCode);
	}
	
	/**
	 *   @生成时间： 2013-12-17 下午05:34:45
	 *   @方法说明： 获取服务执行路径
	 *   @参数：
	 *   @返回值： 
	 *   @异常：
	 **/
	public LinkedList<String> getServices(){
		return this.services;
	}

	/**
	 *   @生成时间： 2013-12-17 下午05:38:21
	 *   @方法说明： 设置单个业务参数
	 *   @参数：key-键值，value-值
	 *   @返回值： 
	 *   @异常：
	 **/
	public void setBusiParam(String key, Object value){
		this.busiParam.put(key, value);
	}
	

	/**
	 *   @生成时间： 2013-12-17 下午05:38:21
	 *   @方法说明： 填充整个业务对象
	 *   @参数：busiParam-业务对象
	 *   @返回值： 
	 *   @异常：
	 **/
	public void setBusiParam(Map<String, Object> busiParam){
		this.busiParam = busiParam;
	}
	/**
	 *   @生成时间： 2013-12-23 下午02:11:06
	 *   @方法说明： 获取业务对象的大小
	 *   @参数：
	 *   @返回值： 业务参数列表大小
	 *   @异常：
	 **/
	public int getBusiParamSize(){
		return busiParam.size();
	}
	
	/**
	 *   @生成时间： 2013-12-23 下午02:13:14
	 *   @方法说明： 使用下标获取数据
	 *   @参数：i-下标
	 *   @返回值： 值
	 *   @异常：
	 **/
	public Object getValue(int i){
		return busiParam.values().toArray()[i];
	}
	
	/**
	 *   @生成时间： 2013-12-23 下午02:25:37
	 *   @方法说明： 根据下标获取键值
	 *   @参数：i-下标
	 *   @返回值： 键值
	 *   @异常：
	 **/
	public String getKey(int i){
		return (String)busiParam.keySet().toArray()[i];
	}
	
	/**
	 *   @生成时间： 2013-12-17 下午05:40:21
	 *   @方法说明： 获取参数键对应的数据
	 *   @参数：key-键值
	 *   @返回值： 值
	 *   @异常：
	 *
	 */
	public Object getBusiParam(String key){
		return this.busiParam.get(key);
	}
	
	/**
	 *   @生成时间： 2013-12-17 下午05:40:21
	 *   @方法说明： 获取参数键对应的数据
	 *   @参数：key-键值
	 *   @返回值： 值
	 *   @异常：
	 *
	 */
	public Map<String, Object> getBusiParam(){
		return this.busiParam;
	}
	
	/**
	 *   @生成时间： 2013-12-23 下午02:56:16
	 *   @方法说明： 将参数添加到业务参数列表
	 *   @参数：param-子参数表
	 *   @返回值： 
	 *   @异常：
	 **/
	public void setSubParam(Map<String, Object> map){
		this.busiParam.putAll(map);
	}
	
	/**
	 *   @生成时间： 2013-12-23 下午02:56:16
	 *   @方法说明： 使用键值获取控制参数
	 **/

	public boolean isRunResult() {
		return runResult;
	}

	public void setRunResult(boolean runResult) {
		this.runResult = runResult;
	}

	public String getCurService() {
		return curService;
	}

	public void setCurService(String curService) {
		this.curService = curService;
	}

	public T getResultObject() {
		return resultObject;
	}
}