package com.study.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class HdfsClient {

    static FileSystem fs = null;
    static Configuration conf;

    public static void main(String[] args) throws Exception {
        //创建一个配置对象，用于设置集群上的块大小，副本数量等。
        Configuration hdfsConfiguration = new Configuration();
        hdfsConfiguration = new Configuration();
        hdfsConfiguration.addResource("hdfs/hdfs-site.xml");
        hdfsConfiguration.addResource("hdfs/core-site.xml");
        hdfsConfiguration.addResource("hdfs/custom-site.xml");
        fs = FileSystem.get(new URI(hdfsConfiguration.get("fs.defaultFS")), hdfsConfiguration, hdfsConfiguration.get("dfs.user"));
        //testDownLoad();
        //upload();
        mkdir();
    }

    /*
    从hdfs下载文件
     */
    public static void testDownLoad() throws IOException {
        Path localPath = new Path("/Users/hebiao/");
        System.out.println(localPath);
        fs.copyToLocalFile(new Path("/data1/dispatch_data_fans/cellSync_delta/12/412/2719345633_0/4_a5890cac0a6b4d8cd3bd761dcc7b7bbc"), localPath);
        fs.close();
    }

    /*
   从hdfs下载文件
    */
    public static void upload() throws IOException {
        fs.copyFromLocalFile(new Path("/Users/hebiao/f01.txt"), new Path("/data1/"));
        fs.close();
    }


    public static void mkdir(){
        Path dir = new Path("/data1/groupchat/f01/");
        try {
            fs.mkdirs(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}