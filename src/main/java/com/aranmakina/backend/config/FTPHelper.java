package com.aranmakina.backend.config;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FTPHelper {

    public static void uploadFile(String server, int port, String user, String pass, String remoteDir, File localFile) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFile = remoteDir + "/" + localFile.getName();
            try (FileInputStream inputStream = new FileInputStream(localFile)) {
                boolean done = ftpClient.storeFile(remoteFile, inputStream);
                if (done) {
                    System.out.println("Dosya başarıyla yüklendi: " + remoteFile);
                } else {
                    System.out.println("Dosya yüklenemedi.");
                }
            }
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}

