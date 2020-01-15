/*
package com.proto;


import com.weibo.api.wsccenter.common.resolver.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

*/
/**
 * Created by junxian on 2016/1/29.
 *//*

public class ProtoReader {
    private static final Logger logger = LoggerFactory.getLogger(ProtoReader.class);
    private static final String SUFFIX_PRO = ".pro";
    private static final String PROTO_LIST = "/proto.list";
    private static final String PROTO_PATH = "/proto/";
    private static final String PROTO_TEMP_PATH = "D:/proto.list";
    private static ProtoReader instance = null;
    private Map<String, ProtoFileInfo> protoFileInfoMap = null;
    private ProtoReader(){}

    public static void main(String[] args) {
        new ProtoReader().setProtoList();
    }

    */
/**
     * 初始化proto.list文件
     *//*

    private void setProtoList(){
        File protoListFile = new File(PROTO_TEMP_PATH);
        BufferedWriter bufferedWriter = null;
        try{
            bufferedWriter = new BufferedWriter(new FileWriter(protoListFile));
            URL url = ProtoReader.class.getResource(PROTO_PATH);
            File protoDir = new File(url.getPath());
            if (protoDir != null) {
                File[] files = protoDir.listFiles();
                for (File file : files) {
                    if (file.getName().endsWith(SUFFIX_PRO)) {
                        String fileName = file.getName();
                        bufferedWriter.write(fileName + "\r\n");
                    }
                }
            }
        }catch (Exception e) {
            logger.error("setProtoList with Exception:", e);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized ProtoReader getInstance(){
        if(instance == null){
            instance = new ProtoReader();
            try {
                if (instance.isInJar()) {
                    instance.initFieldMappingInJar();
                } else {
                    instance.initFieldMappingInClass();
                }
                if(instance.protoFileInfoMap.isEmpty()) {
                    instance.initFieldMappingInFatjar();
                }
            } catch (Throwable e) {
                try {
                    instance.initFieldMappingInFatjar();
                } catch (Throwable e1) {
                    logger.error("initFieldMappingInFatjar with Throwable:", e1);
                }
            }
        }
        return instance;
    }

    public Map<String, ProtoFileInfo> getProtoFileInfoMap() {
        return protoFileInfoMap;
    }

    private boolean isInJar() {
        URL url = ProtoReader.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
            JarFile file = new JarFile(filePath);
            file.getName();
            file.close();
            return  true;
        } catch (Throwable e) {
            return false;
        }
    }

    */
/**
     * 使用spring-boot启动
     * @throws Exception
     *//*

    private void initFieldMappingInFatjar() {
        logger.debug("load proto from fat jar...");
        InputStream im = null;
        try {
            protoFileInfoMap = new HashMap<String, ProtoFileInfo>();
            URL url = ProtoReader.class.getResource(PROTO_LIST);
            ProtoFileHandler protoFileHandler = new ProtoFileHandler();
            URLConnection urlConnection = protoFileHandler.openConnection(url);
            im = urlConnection.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(im));
            String protoFileName = "";
            while ((protoFileName = bf.readLine()) != null) {
                initFieldMappingInFatjar(protoFileName);
            }
        } catch (Exception e) {
            logger.error("initFieldMappingInFatjar with Exception:", e);
        } finally {
            try {
                im.close();
            } catch (IOException e) {
                logger.error("initFieldMappingInFatjar with Exception:", e);
            }
        }
    }

    private void initFieldMappingInFatjar(String protoFileName) throws Exception {
        URL url = ProtoReader.class.getResource(PROTO_PATH + protoFileName);
        ProtoFileHandler protoFileHandler = new ProtoFileHandler();
        URLConnection urlConnection = protoFileHandler.openConnection(url);
        InputStream im = null;
        try {
            im = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(im));
            ProtoFileInfo protoFileInfo = getMapFromFileName(bufferedReader);
            protoFileInfoMap.put(protoFileInfo.getKey(), protoFileInfo);
        }catch (Exception e) {
            logger.error("initFieldMappingInFatjar protoFileName:"+protoFileName+" with Exception:", e);
        } finally {
            im.close();
        }
    }

    private void initFieldMappingInClass() throws Throwable {
        logger.debug("load proto from class...");
        protoFileInfoMap = new HashMap<String, ProtoFileInfo>();
        URL url = ProtoReader.class.getResource(PROTO_PATH);
        File protoDir = new File(url.getPath());
        if (protoDir != null) {
            File[] files = protoDir.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(SUFFIX_PRO)) {
                    String fileName = (PROTO_PATH) + file.getName();
                    ProtoFileInfo protoFileInfo = getMapFromFileName(fileName);
                    protoFileInfoMap.put(protoFileInfo.getKey(), protoFileInfo);
                }
            }
        }
    }

    private void initFieldMappingInJar() throws Throwable {
        logger.debug("load proto from jar...");
        protoFileInfoMap = new HashMap<String, ProtoFileInfo>();
        URL url = ProtoReader.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        JarFile file = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "UTF-8");
            file = new JarFile(filePath);
            Enumeration<JarEntry> entrys = file.entries();
            while(entrys.hasMoreElements()){
                JarEntry jar = entrys.nextElement();
                if (jar.getName().endsWith(SUFFIX_PRO)) {
                    String fileName = "/" + jar.getName();
                    ProtoFileInfo protoFileInfo = getMapFromFileName(fileName);
                    protoFileInfoMap.put(protoFileInfo.getKey(), protoFileInfo);
                }
            }
        } catch (Exception e) {
            logger.error("initFieldMappingInJar with Exception:", e);
        } finally {
            file.close();
        }
    }

    private ProtoFileInfo getMapFromFileName(BufferedReader reader){
        ProtoFileInfo protoFileInfo = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            protoFileInfo = getProtoFileInfoFromReader(bufferedReader);
            return protoFileInfo;
        } catch (Exception e) {
            logger.error("getMapFromFileName with Exception:", e);
        }
        return  null;
    }

    private ProtoFileInfo getMapFromFileName(String fileName){
        URL url = ProtoReader.class.getResource(fileName);
        InputStream in = null;
        ProtoFileInfo protoFileInfo = null;
        try {
            in = url.openStream();
            Reader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            protoFileInfo = getProtoFileInfoFromReader(bufferedReader);
            return protoFileInfo;
        } catch (Exception e) {
            logger.error("getMapFromFileName fileName:"+fileName+" with Exception:", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private ProtoFileInfo getProtoFileInfoFromReader(BufferedReader bufferedReader) {
        ProtoFileInfo protoFileInfo = new ProtoFileInfo();
        try {
            String line = bufferedReader.readLine();//read tag
            if (line.indexOf("//") > -1) {
                line = line.substring(0, line.indexOf("//"));
            }
            String[] splits = line.trim().split(" ");
            String protoName = splits[0];
            String protoTag = splits[1];
            String protoType = splits[2];
            List<Field> list = getFiledsFromReader(bufferedReader);
            protoFileInfo.setProtoName(protoName);
            protoFileInfo.setProtoTag(protoTag);
            protoFileInfo.setProtoType(protoType);
            if (list.size() > 0) {
                protoFileInfo.setRootField(list.get(0));
            } else {
                protoFileInfo.setRootField(new Field());
            }
            return protoFileInfo;
        } catch (Exception e) {
            logger.error("getProtoFileInfoFromReader with Exception:", e);
        }
        return null;
    }

    private List<Field> getFiledsFromReader(BufferedReader bufferedReader) {
        List<Field> list = new ArrayList<Field>();
        try {
            String line = "", name = "";
            int index = 0, tag = 0;
            Field field = null;
            WireFormat.FieldType fieldType = null;
            while((line = bufferedReader.readLine()) != null) {
                if (line.indexOf("//") > -1) {
                    line = line.substring(0, line.indexOf("//"));
                }
                line = line.replaceAll("\"", "").replaceAll(",","").replaceAll("-","_").trim();

                String[] splits = line.split(":");
                if (splits.length == 2){
                    name = splits[0].trim();
                    fieldType = convertFieldType(splits[1].trim());
                    tag = index;
                    field = new Field(tag, name, fieldType);
                    if (fieldType == WireFormat.FieldType.STRUCT) {
                        getSubFields(bufferedReader, field);
                    } else if (fieldType == WireFormat.FieldType.ARRAY){
                        if (splits[1].trim().endsWith("]")) {
                            field.setItemType(convertFieldType(splits[1].trim().
                                    replaceAll("\"", "").replaceAll("]" ,"").replace("[", "")));
                        } else {
                            getSubFields(bufferedReader, field);
                        }
                    }
                    list.add(field);
                } else if(splits.length == 1 &&
                        (splits[0].trim().startsWith("}") || splits[0].trim().startsWith("]"))){
                    return list;
                }
                index++;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getSubFields(BufferedReader bufferedReader, Field field) {
        List<Field> subFields = getFiledsFromReader(bufferedReader);
        Map<Integer, Field> tagMapping = new HashMap<Integer, Field>();
        Map<String, Field> nameMapping = new HashMap<String, Field>();
        field.setNameMapping(nameMapping);
        field.setTagMapping(tagMapping);
        field.setSubFields(subFields);
        for (Field subField : subFields) {
            field.getNameMapping().put(subField.getFieldName(), subField);
            field.getTagMapping().put(subField.getFieldTag(), subField);
        }
    }

    private WireFormat.FieldType convertFieldType(String type) {
        if ("uint32".equals(type)) {
            return WireFormat.FieldType.UINT64;
        }else if ("uint64".equals(type)) {
            return WireFormat.FieldType.UINT64;
        }else if ("uint8".equals(type)) {
            return WireFormat.FieldType.UINT64;
        }else if ("string".equals(type)) {
            return WireFormat.FieldType.STRING;
        }else if ("bytes".equals(type)) {
            return WireFormat.FieldType.STRING;
        }else if ("{".equals(type) || type.startsWith("{")) {
            return WireFormat.FieldType.STRUCT;
        }else if ("[".equals(type) || type.startsWith("[")) {
            return WireFormat.FieldType.ARRAY;
        }

        return WireFormat.FieldType.UINT64;
    }
}
*/
