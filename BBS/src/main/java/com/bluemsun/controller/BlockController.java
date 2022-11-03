package com.bluemsun.controller;


import com.bluemsun.entity.Block;
import com.bluemsun.entity.Result;
import com.bluemsun.service.BlockService;
import com.bluemsun.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/block")
@Scope("session")
public class BlockController {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private BlockService blockService =(BlockService) context.getBean("BlockService");
    private UserService userService = (UserService) context.getBean("UserService");

    @RequestMapping(value = "/show/{id}")
    public Result showBlock(@PathVariable int id, HttpServletRequest request) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        Map<String,Object> map = blockService.showBlockMessage(id,userId);
        map.put("idPhoto",userService.getUserById(userId).getIdPhoto());
        map.put("nickName",userService.getUserById(userId).getNickName());
        return Result.ok().data(map);
    }

    @RequestMapping(value = "/show/page/{id}/{index}")
    public Result getPosts(@PathVariable int index, @PathVariable int id) throws JsonProcessingException {
//        return JsonUtil.toJson(blockService.showPostsPage(id,index));
          return Result.ok().data("page",blockService.showPostsPage(id,index));
    }

    @PostMapping(value = "/add")
    public Result addBlock(HttpServletRequest request, @RequestBody Block block){
        int userId = (int) request.getAttribute("id");
        return Result.ok().data(blockService.addBlock(block.getBlockName(),block.getDescribe(),userId));
    }

    @GetMapping(value = "/delete/{blockId}")
    public Result deleteBlock(HttpServletRequest request,@PathVariable int blockId){
        int userId = (int) request.getAttribute("id");
        return Result.ok().data(blockService.deleteBlock(blockId,userId));
    }

}
