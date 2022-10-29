package com.bluemsun.controller;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.entity.Result;
import com.bluemsun.service.FileService;
import com.bluemsun.util.DataUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/file")
public class FileController {

    @RequestMapping(value = "/add")
    public Result addFile(HttpServletRequest request, @RequestParam("postsId") int postsId,@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        com.bluemsun.entity.File file1 =(com.bluemsun.entity.File) context.getBean("File");
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        file1.setFileName(fileName);
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString()+hzName;
        ServletContext servletContext = session.getServletContext();
        String filePath = servletContext.getRealPath("file");
        File f = new File(filePath);
        if(!f.exists()){
            f.mkdir();
        }
        String finalPath = filePath+File.separator+fileName;
        file1.setRealPath(finalPath);
        file.transferTo(new File(finalPath));
        FileService fileService = (FileService) context.getBean("FileService");
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");
        Claim userId = decodedJWT.getClaim("id");
        String url = "http://43.140.247.80:8080"+ "/showfile/"+fileName;
        file1.setUrl(url);
        file1.setUserId(userId.asInt());
        file1.setPostsId(postsId);
        String msg = fileService.addFile(file1);
        Map<String,Object> m = new HashMap<>();
        m.put("msg",msg);
        m.put("url",url);
        m.put("fileId",file1.getId());
        return Result.ok().data(m);
    }

    @RequestMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable int id,HttpSession session) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        FileService fileService =(FileService) context.getBean("FileService");
        com.bluemsun.entity.File file = fileService.getFile(id);
        InputStream is = new FileInputStream(file.getRealPath());
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        MultiValueMap<String,String> headers = new HttpHeaders();
        String value = "attachment;filename="+file.getFileName();
        headers.add("Content-Disposition",value);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity  = new ResponseEntity<>(bytes,headers,statusCode);
        is.close();
        return responseEntity;
    }
}
