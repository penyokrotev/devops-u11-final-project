package com.example.finalproject.controllers.rest;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/images")
public class ImagesControllerRest {
    @GetMapping("/ovrc/{fileName}")
    public ResponseEntity<byte[]> getOvrcImage(@PathVariable String fileName) throws IOException {
        File file = new File("../files/SafetyCarImagesOVRC/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);
    }
}
