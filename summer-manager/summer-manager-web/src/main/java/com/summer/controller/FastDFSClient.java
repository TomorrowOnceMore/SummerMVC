package com.summer.controller;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

/**
 * Created by toy on 6/10/16.
 */
public class FastDFSClient {
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient storageClient = null;

    public FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = ((StorageClient1)storageClient).upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
        String result = ((StorageClient1)storageClient).upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }
}
