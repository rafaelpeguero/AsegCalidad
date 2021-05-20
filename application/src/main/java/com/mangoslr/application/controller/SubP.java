package com.mangoslr.application.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Controller
@RequestMapping("/sub/p/")
public class SubP {
    HashMap<String, String> caminosImg = new HashMap<String, String>();

    @PostConstruct
    public void init() {
        caminosImg.put("producto", "img_productos/");
    }

    @ResponseBody
    @RequestMapping(value = "{source}/img/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByte(@PathVariable(value = "source") String source, @PathVariable(value = "imageName") String imageName) throws IOException {
//        System.out.println(source);
//        System.out.println(caminosImg.get(source));
//        File file = new File();
        return Files.readAllBytes(Paths.get(caminosImg.get(source) + imageName));
    }
}
