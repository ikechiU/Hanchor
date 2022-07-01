package com.nextgendevs.hanchor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    public String uploadFile(MultipartFile file, String userId, String sensorId);

    public String destroyFile(String userId, String url) throws IOException;
}
