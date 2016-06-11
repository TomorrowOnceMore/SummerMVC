package com.summer.fastdfs;

import com.summer.common.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by toy on 6/10/16.
 */
public class FastdfsTest {

    @Test
    public void testUpLoad() throws IOException, MyException {
        String clientConf = "/Users/toy/Projects/JavaProjects/SummerMVC/summer-manager/summer-manager-web/src/main/resources/properties/client.conf";
        ClientGlobal.init(clientConf);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        String[] strings = storageClient.upload_file("/Users/toy/Projects/JavaProjects/SummerMVC/4737f13e6f7cb4523c872ea1627f823a.jpg", "jpg", null);
        for (String str: strings) {
            System.out.println(str);
        }
    }

    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient client = new FastDFSClient("/Users/toy/Projects/JavaProjects/SummerMVC/summer-manager/summer-manager-web/src/main/resources/properties/client.conf");
        String uploadFile = client.uploadFile("/Users/toy/Projects/JavaProjects/SummerMVC/lacelady.jpg", "jpg");
        System.out.println(uploadFile);
    }
}
