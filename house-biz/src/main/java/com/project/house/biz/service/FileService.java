package com.project.house.biz.service;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Created by user on 2018-04-19.
 */

@Service
public class FileService {


    @Autowired
    private FtpService ftpService;

    @Autowired
    private ServletContext servletContext;

    private String tempFilePath; //文件临时存放路径




    @Value("${file.server.path}")
    private String serverFilePath;//文件服务器路径

    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.port}")
    private int port;


    public List<String> uploadAndGetImgPaths(List<MultipartFile> files) {
        List<String> paths = Lists.newLinkedList();
        if (ftpService.connect(ip, username, password, port)){
            List<File> fileList = Lists.newLinkedList();
            try {
                tempFilePath = servletContext.getRealPath("upload");
                fileList = saveToLocal(files, tempFilePath);
                paths = ftpService.upload(fileList, serverFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return paths;
    }

    private List<File> saveToLocal(List<MultipartFile> multipartFileList, String tempFilePath) throws IOException {
        File tempDir = new File(tempFilePath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        tempDir.setWritable(true);

        List<File> newFileList = Lists.newLinkedList();
        for (MultipartFile multipartFile : multipartFileList) {
//            String fileName = multipartFile.getOriginalFilename();
//            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
            File newFile = new File(tempFilePath, Instant.now().getEpochSecond() + "_" + multipartFile.getOriginalFilename());
            newFile.createNewFile();
            multipartFile.transferTo(newFile);
            newFileList.add(newFile);
        }
        return newFileList;
    }


}
