package com.kongxiaogang.model.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kongxiaogang.model.common.BaseModel;
/**
 * 
 * <pre>项目名称：xhcf-back-model    
 * 类名称：TreeNode    
 * 类描述：    树节点（目前只用于角色选择时显示的菜单树）
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月25日 下午3:26:57    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月25日 下午3:26:57    
 * 修改备注：       
 * @version </pre>
 */
public class TreeNode extends BaseModel{

	private String nodeid; //节点编号（权限号）
	private String text; //节点描述
	private String icon; //图标样式
	private String href; //节点连接
	private String tags; //下级节点数
	private String parentId; //上级节点编号
	private String menuId; //对应菜单的编号
	private Map<String,String> state = new HashMap<String,String>();
	private List<TreeNode> nodes = new ArrayList<TreeNode>(); //下级所有节点
	
	public TreeNode() {
	}
	public TreeNode(String nodeid,String text,String parentId,String menuId,List<TreeNode> nodes) {
		this.nodeid = nodeid;
		this.text = text;
		this.nodes = nodes;
		this.parentId = parentId;
		this.menuId = menuId;
	}
	public TreeNode(String nodeid,String text,String icon,String href, String tags, Map<String,String> state,String parentId,String menuId, List<TreeNode> nodes) {
		this.nodeid = nodeid;
		this.text = text;
		this.icon = icon;
		this.href = href;
		this.tags = tags;
		this.state = state;
		this.parentId = parentId;
		this.menuId = menuId;
		this.nodes = nodes;
	}
	
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public List<TreeNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public Map<String, String> getState() {
		return state;
	}
	public void setState(Map<String, String> state) {
		this.state = state;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
