package com.bluemsun.controller;


import com.bluemsun.entity.Inform;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Result;
import com.bluemsun.entity.vo.InformVO;
import com.bluemsun.service.InformService;
import com.bluemsun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/inform")
@ResponseBody
@Scope("session")
public class InformController {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private InformService informService =(InformService) context.getBean("InformService");
    private UserService userService = (UserService) context.getBean("UserService");

    @GetMapping(value = "/{type}/{index}")
    public Result showInform(HttpServletRequest request, @PathVariable String type, @PathVariable int index){
        int userId = (int) request.getAttribute("id");
        Page<Inform> page =  informService.showInform(userId,index,type);
        InformVO informVO = new InformVO(userService.getUserById(userId).getNickName(),userService.getUserById(userId).getIdPhoto(),page);
        return Result.ok().data(informVO);
    }

    @GetMapping(value = "/delete/{informId}")
    public Result deleteInform(HttpServletRequest request,@PathVariable int informId){
        int userId = (int) request.getAttribute("id");
        return Result.ok().data(informService.deleteInform(informId,userId));
    }
}
