package com.bluemsun.controller;


import com.bluemsun.entity.Block;
import com.bluemsun.entity.Result;
import com.bluemsun.entity.vo.BlockVO;
import com.bluemsun.service.BlockService;
import com.bluemsun.service.IndexService;
import com.bluemsun.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService = (UserService)context.getBean("UserService");

    @RequestMapping(value = "/main")
    public Result showMain(HttpServletRequest request) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        Map<String,Object> map = indexService.getIndex(userId);
        map.put("nickName",userService.getUserById(userId).getNickName());
        map.put("idPhoto",userService.getUserById(userId).getIdPhoto());
        return Result.ok().data(map);
    }

    @RequestMapping(value = "/block")
    public Result showBlock(HttpServletRequest request) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        List<Block> list = blockService.showBlock(userId);
        BlockVO blockVO = new BlockVO(list,userService.getUserById(userId).getNickName(),userService.getUserById(userId).getIdPhoto());
        return Result.ok().data(blockVO);
    }

    @GetMapping(value = "/search/{value}")
    public Result search(HttpServletRequest request, @PathVariable String value){
        int userId = (int) request.getAttribute("id");
        Map<String,Object> map = indexService.indexSearch(value,userId);
        return Result.ok().data(map);
    }

}
