package com.uploadanddownload.updown.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${fileUploadFolder}")
    private String fileUploadFolder;
    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeName = newFileName + "." + extension;
        File finalFolder = new File(fileUploadFolder);
        if(!finalFolder.exists())throw new IOException("folder not found");
        if(!finalFolder.isDirectory()){throw new IOException("final folder is not a directory");}
        File finalDestination = new File(fileUploadFolder + "\\" +completeName);
        if(finalDestination.exists())throw new IOException("file conflict");
        file.transferTo(finalDestination);
        return completeName;
    }
    public byte[] download(String fileName) throws IOException {
        File fileFromFolder = new File(fileUploadFolder + "\\" + fileName);
        if(!fileFromFolder.exists()) throw new IOException("folder doesn't exist");
        return IOUtils.toByteArray(new FileInputStream(fileFromFolder));
    }
}
