package com.project.house.biz.service;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FtpService {
    private static final Logger logger = LoggerFactory.getLogger(FTPClient.class);

    private static FTPClient ftpClient;



    public boolean connect(String ip, String username, String password, int port) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess=ftpClient.login(username, password);
        } catch (IOException e) {
            throw new IllegalArgumentException("服务器连接失败",e);
        }
        return isSuccess;
    }

    public boolean upload(MultipartFile file, String path) {

        FileInputStream fis = new FileInputStream(file.transferTo(););
        ftpClient.storeFile()
    }
}
