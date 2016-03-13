package com.kld.gsm.center.web.webcontroller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/1/4.
 */
@Controller
@ApiIgnore
@RequestMapping("/web/monitor")
public class WebMonitorController extends WebBaseController{
    @RequestMapping("/monitor")
    public ModelAndView monitor(){
        return new ModelAndView("monitor/monitor");
    }
}
