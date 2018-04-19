package com.project.house.biz.service;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Created by user on 2018-04-19.
 */
public class FileService {

    @Value("${file.paths}")
    private String filePath; //文件服务器存放路径

    public List<String> getImgPaths(List<MultipartFile> files) {

        List<String> paths = Lists.newLinkedList();
        files.forEach(file -> {
            if (!file.isEmpty()) {
                try {
                    File localFile = saveToLocal(file, filePath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
                    paths.add(path);
                } catch (IOException e) {
                    throw new IllegalArgumentException("存储到本地服务地址出错", e);
                }
            }
        });
        return paths;
    }

    private File saveToLocal(MultipartFile file, String filePath) throws IOException {
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        newFile.getParentFile().mkdirs();
        newFile.createNewFile();
        Files.write(file.getBytes(), newFile);
        return newFile;
    }


}
