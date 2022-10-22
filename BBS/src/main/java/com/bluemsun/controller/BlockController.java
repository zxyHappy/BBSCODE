package com.bluemsun.controller;


import com.bluemsun.service.BlockService;
import com.bluemsun.service.UserService;
import com.bluemsun.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/block")
@Scope("session")
public class BlockController {

    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private BlockService blockService =(BlockService) context.getBean("BlockService");
    private UserService userService = (UserService) context.getBean("UserService");

    @RequestMapping(value = "/show/{id}")
    public String showBlock(@PathVariable int id, HttpServletRequest request) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        Map<String,Object> map = blockService.showBlockMessage(id);
        map.put("idPhoto",userService.getUserById(userId).getIdPhoto());
        map.put("nickName",userService.getUserById(userId).getNickName());
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/show/page/{id}/{index}")
    public String getPosts(@PathVariable int index, @PathVariable int id) throws JsonProcessingException {
        return JsonUtil.toJson(blockService.showPostsPage(id,index));
    }


}
