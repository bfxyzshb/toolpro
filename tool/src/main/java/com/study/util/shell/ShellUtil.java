package com.study.util.shell;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

/**
 * 本地执行shell
 */
public class ShellUtil {


    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public static BaseResponse executeCommand(String command) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader in = null;
        BufferedReader br = null;

        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        try {
            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
            pb.environment();
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (process != null) {
                in = new InputStreamReader(process.getInputStream());
                br = new BufferedReader(in, 1024);
                process.waitFor();
            } else {
                //logger.info("There is no PID found, command={}.", command);
            }
            String line;
            while (br != null && (line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            //logger.error("executeCommand: get Exception where execute command, command={}.", command);
            return new BaseResponse(ResultCodeType.ERROR.getValue(), "命令执行失败");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    //logger.error("executeCommand: get Exception where close br, command={}.", command);
                    return new BaseResponse(ResultCodeType.ERROR.getValue(), "命令执行失败");
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //logger.error("executeCommand: get Exception where close in, command={}.", command);
                    return new BaseResponse(ResultCodeType.ERROR.getValue(), "命令执行失败");
                }
            }
            BaseResponse response = new BaseResponse(ResultCodeType.SUCCESS.getValue(), "命令执行成功");
            response.setData(sb.toString().trim());
            return response;
        }
    }

    public static void main(String[] args) {
        /*String cmd = "printf 'show resource messageflow.checkBlackList.switcher\r\n' | nc 10.75.2.158 881";
        System.out.println(executeCommand(cmd));*/
        String command = String.format("ping %s -c 10 -i 0.5", "www.baidu.com");// 检查10次网络情况，间隔0.5秒
        BaseResponse response = ShellUtil.executeCommand(command);
        System.out.println(response.getData());
        /*
        String regex = "http://#ip#:[0-9]+";
        String cmd = "curl 'http://#ip#:8080/apicell/switch.jsp?name=feature.dm.group.push.wesync.mobile.webim'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cmd);
        if (matcher.find()) {
            System.out.println(matcher.group().replaceAll("http://#ip#:", ""));
        }
        */
        /*
        String cmd = "ping weibo.com -c 5 -i 0.5";
        BaseResponse response = executeCommand(cmd);
        String data = response.getData();
        System.out.println("==========================================");
        System.out.println(data);
        System.out.println("==========================================");
        String regex1 = "received, ([0-9]{1,3}(\\.{0,1}[0-9]{1,2})?)% packet loss";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(data);
        if (matcher1.find()) {
            System.out.println("丢包率：" + matcher1.group(1));
            System.out.println("==========================================");
        }
        String regex2 = "min/avg/max/[a-z]+ = (([0-9]{1,3}\\.{0,1}[0-9]{1,3})/([0-9]{1,3}\\.{0,1}[0-9]{1,3})/([0-9]{1,3}\\.{0,1}[0-9]{1,3})/([0-9]{1,3}\\.{0,1}[0-9]{1,3})) ms";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(data);
        if (matcher2.find()) {
            System.out.println("cost：" + matcher2.group(1));
            System.out.println("min：" + matcher2.group(2));
            System.out.println("avg：" + matcher2.group(3));
            System.out.println("max：" + matcher2.group(4));
            System.out.println("stddev：" + matcher2.group(5));
            System.out.println("==========================================");
        }
        */
        System.out.println(executeCommand("dig weibo.com"));
    }
}