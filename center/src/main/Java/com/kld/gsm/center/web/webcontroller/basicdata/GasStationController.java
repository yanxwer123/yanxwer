package com.kld.gsm.center.web.webcontroller.basicdata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-09 21:22
 * @Description:
 */
@Controller
@RequestMapping("/web/basicdata")
public class GasStationController {
    @RequestMapping("/go")
    public String basicdata() {
        return "basicdata/GasStationInfor";
    }

    @ResponseBody
    @RequestMapping("/tojson")
    public Object bsasicdata() {
        System.out.println("jion tojson");
        Tree hunan = new Tree();
        hunan.setId(1);
        hunan.setText("湖南");
        hunan.setIconCls("wordnik_api.png");
        hunan.setState("open");


        Tree children = new Tree();
        children.setId(2);
        children.setText("长沙市");
        children.setState("open");
        children.setIconCls("wordnik_api.png");

        Children children1 = new Children();
        children1.setId(121);
        children1.setText("长沙油站");
        children1.setAttributes("/web/basicdata/tojson");
        List childrenlist = new ArrayList();
        childrenlist.add(children1);
        children.setChildren(childrenlist);

        Tree children2 = new Tree();
        children2.setId(3);
        children2.setText("株洲市");
        children2.setState("closed");
        children2.setIconCls("wordnik_api.png");

        List l = new ArrayList();


        l.add(children);
        l.add(children2);

        hunan.setChildren(l);

        List list = new ArrayList();
        list.add(hunan);
        return list;
    }
}

class Tree {
    int id;//
    String text;//Name
    String state;// 状态  open|closed
    String iconCls;//图标
    List children;//下级

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                ", iconCls='" + iconCls + '\'' +
                ", children=" + children +
                '}';
    }
}

class Children {
    int id;
    String text;
    String attributes;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Children{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", attributes='" + attributes + '\'' +
                '}';
    }
}