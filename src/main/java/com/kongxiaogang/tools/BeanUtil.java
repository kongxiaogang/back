package com.kongxiaogang.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.kongxiaogang.model.UserModel;

public class BeanUtil {
	public static void setProperty(UserModel userInfo,String userName)throws Exception{
        PropertyDescriptor propDesc=new PropertyDescriptor(userName,UserModel.class);
        Method methodSetUserName=propDesc.getWriteMethod();
        methodSetUserName.invoke(userInfo, 3);
        System.out.println("set userName:"+userInfo.getUserName());
        System.out.println(propDesc.getPropertyType());
    }
	
	public static void main(String[] args) throws Exception {
/*		UserBean user = new UserBean();
		setProperty(user, "loginNumber");*/
		test();
	}
	
	public static void test() throws IntrospectionException  {	
		BeanInfo beanInfo = Introspector.getBeanInfo(UserModel.class);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		 for(PropertyDescriptor pd : pds){
			 System.out.println(pd.getName());
		 }
	}
}
