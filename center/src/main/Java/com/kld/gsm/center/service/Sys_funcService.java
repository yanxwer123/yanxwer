package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.Sys_func;

import java.util.List;
import java.util.Map;

/**
 * Created by jw on 2015/7/30.
 */
public interface Sys_funcService {


    List<Sys_func> getFuncList(Map<String, Object> map);
}
