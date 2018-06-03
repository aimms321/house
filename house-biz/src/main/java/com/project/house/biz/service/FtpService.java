package com.project.house.biz.service;

import com.google.common.collect.Lists;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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

    public List<String> upload(List<File> fileList, String path) throws IOException {
        FileInputStream fis = null;
        List<String> pathList = Lists.newLinkedList();
        try {
            ftpClient.changeWorkingDirectory(path);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            for (File file : fileList) {
                fis = new FileInputStream(file);
                ftpClient.storeFile(file.getName(), fis);
                String filePath = "/" + file.getName();
                pathList.add(filePath);
            }


        } catch (IOException e) {
            logger.error("FTP上传失败",e);
        }finally {
            fis.close();
            ftpClient.disconnect();
        }
        return pathList;
    }
}
