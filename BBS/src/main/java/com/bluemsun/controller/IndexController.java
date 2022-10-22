package com.bluemsun.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.entity.Block;
import com.bluemsun.service.BlockService;
import com.bluemsun.service.IndexService;
import com.bluemsun.util.JWTUtil;
import com.bluemsun.util.JsonUtil;
import com.bluemsun.util.UserCheckUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/index")
@ResponseBody
@Scope("session")
public class IndexController {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private IndexService indexService = (IndexService) context.getBean("IndexService");
    private BlockService blockService = (BlockService) context.getBean("BlockService");
    private Map<String,Object> map = new HashMap<>();

    @RequestMapping(value = "/main")
    public String showMain(HttpServletRequest request) throws JsonProcessingException {
        UserCheckUtil.checkUserLogin(request);
        Map<String,Object> map = indexService.getIndex();
        map.put("msg",UserCheckUtil.getMsg());
        map.put("nickName",UserCheckUtil.getNickName());
        map.put("idPhoto",UserCheckUtil.getIdPhoto());
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/block")
    public String showBlock(HttpServletRequest request) throws JsonProcessingException {
        UserCheckUtil.checkUserLogin(request);
        List<Block> list = blockService.showBlock();
        map.clear();
        map.put("blockList",list);
        map.put("nickName",UserCheckUtil.getNickName());
        map.put("idPhoto",UserCheckUtil.getIdPhoto());
        return JsonUtil.toJson(map);
    }
}
