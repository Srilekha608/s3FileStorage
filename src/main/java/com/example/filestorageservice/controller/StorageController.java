package com.example.filestorageservice.controller;

import com.example.filestorageservice.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService storageService; // injecting the service

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value="file") MultipartFile file){
        return new ResponseEntity<>(storageService.uploadFile(file), HttpStatus.OK);
    }
@GetMapping("/test")
public String test(){
        log.info(storageService.ping().toString());
    return "Hello";
}

    @GetMapping("/bucketList")
    public String test1(String bucket){
        log.info("Testing youuuuu");
        return "Hello";
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource= new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);

    }
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }


}
