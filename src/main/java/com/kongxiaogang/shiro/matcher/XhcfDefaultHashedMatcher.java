package com.kongxiaogang.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 简单的加密验密
 */
public class XhcfDefaultHashedMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
    	String pagePasswd = new String((char[])token.getCredentials());//页面输入的密码
    	Object salt = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
    	Md5Hash hash = new Md5Hash(pagePasswd, salt);
    	info.getCredentials().toString();
        return hash.toString().equals(info.getCredentials().toString());
    }
	
}
