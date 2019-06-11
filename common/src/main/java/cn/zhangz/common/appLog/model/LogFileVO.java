package cn.zhangz.common.appLog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class LogFileVO {
    private int id;
    private int parentId;
    private String name;
    private String absolutePath;
    private boolean isFile;

    LogFileVO(int id, int parentId, String name, String absolutePath, boolean isFile){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.absolutePath = absolutePath;
        this.isFile = isFile;
    }

    private static int key;

    private static void initKey(){
        key = 0;
    }
    private static int getKey(){
        return key;
    }
    private static int increaseKey(){
        return  ++key;
    }

    public static void readFile(String path, List<LogFileVO> fileList){
        initKey();
        File file = new File(path);
        readFile(file,fileList,0);
    }

    private static void readFile(File file, List<LogFileVO> fileList, int pId) {
        int c = increaseKey();
        fileList.add(new LogFileVO(getKey(), pId, file.getName(), file.getAbsolutePath(), !file.isDirectory()));
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                readFile(files[i], fileList, c);
            }
        }
    }
}
