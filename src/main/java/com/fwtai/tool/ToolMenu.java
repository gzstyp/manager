package com.fwtai.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 组装无限极导航菜单
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2018-06-02 10:02
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ToolMenu{

    private String id;
    private String pid;
    private String children;

    /**
     * 指定菜单的主键,父级id,子节点的字段
     * @param id 主键
     * @param pid 父级id
     * @param children 子节点的字段
    */
    public ToolMenu(final String id,final String pid,final String children){
        this.id = id;
        this.pid = pid;
        this.children = children;
    }

    /**组装菜单数据*/
    public List<HashMap<String,Object>> initMenu(final List<HashMap<String,Object>> allMenu,final String topId){
        final List<HashMap<String,Object>> rootMenu = new ArrayList<HashMap<String,Object>>();
        for (final HashMap<String,Object> map : allMenu){
            //父节点是topId为最外层的根节点。
            if(map.get(this.pid).toString().equals(topId)){
                rootMenu.add(map);
            }
        }
        //为根菜单设置子菜单，getClild是递归调用的
        for (final HashMap<String,Object> map : rootMenu){
            /* 获取根节点下的所有子节点 使用getChild方法*/
            final List<HashMap<String,Object>> children = getChild(map.get(this.id).toString(),allMenu);
            map.put(this.children,children);
        }
        return rootMenu;
    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    private List<HashMap<String,Object>> getChild(final String id,final List<HashMap<String,Object>> allMenu){
        //子菜单
        final List<HashMap<String,Object>> childList = new ArrayList<HashMap<String,Object>>();
        for (final HashMap<String,Object> map : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较,相等说明：为该根节点的子节点。
            if(map.get(this.pid).toString().equals(id)){
                childList.add(map);
            }
        }
        //递归
        for (final HashMap<String,Object> nav : childList) {
            nav.put(this.children,getChild(nav.get(this.id).toString(),allMenu));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<HashMap<String,Object>>();
        }
        return childList;
    }
}