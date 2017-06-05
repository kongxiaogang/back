package com.kongxiaogang.service.vo;

import java.util.List;

import com.kongxiaogang.model.common.BaseModel;
/**
 * User业务result对象
 * @author LiJZ
 *
 */
public class PageResult extends BaseModel {
	private int code; //返回码
	private String msg; //返回信息
	private List resultList;//实体类列表
	private Object modelInfo; //实体类
	
	public PageResult (int code,String msg,Object modelInfo,List resultList){
		this.code = code;
		this.msg = msg;
		this.modelInfo=modelInfo;
		this.resultList=resultList;
	}
	
	public PageResult (int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	public PageResult (){
		this.code = -1;
		this.msg = "失败的请求";
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public Object getModelInfo() {
		return modelInfo;
	}
	public void setModelInfo(Object modelInfo) {
		this.modelInfo = modelInfo;
	}
	
	
}
