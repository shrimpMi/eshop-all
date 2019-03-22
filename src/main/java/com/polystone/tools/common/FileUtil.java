package com.polystone.tools.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * 文件操作工具类
 *
 * @author jimmy
 * @version V1.0, 2017/7/11
 * @copyright
 */
public class FileUtil {

    private static final int BUFF_SIZE = 8096;
    private static FileUtil me;

    private FileUtil() {

    }

    public static FileUtil getInstance() {
        return null == me ? me = new FileUtil() : me;
    }


    /**
     * 把字符串写到文件
     *
     * @param outPath 输出路径
     * @param str 文本
     * @throws IOException io异常
     */
    public void writeFile(String outPath, String str) throws IOException {
        if (StringUtil.isTrimEmpty(outPath)) {
            return;
        }
        Writer out = new FileWriter(makeFolder(outPath));
        try {
            out.write(str);
            out.flush();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 把字符串追加到文件
     *
     * @param outPath 输出路径
     * @param str 文本
     * @throws IOException io异常
     */
    public void appendToFile(String outPath, String str) throws IOException {
        if (StringUtil.isTrimEmpty(outPath)) {
            return;
        }
        Writer out = new FileWriter(makeFolder(outPath), true);
        try {
            out.append(str);
            out.flush();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 文件拷贝
     */
    public void copyFolder(String src, String dest) throws IOException {
        copyFolder(new File(src), new File(dest));
    }

    private void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdirs();
            }
            String[] files = src.list();
            File srcFolder;
            File destFolder;
            String fileName;
            for (int i = 0, len = files.length; i < len; i++) {
                fileName = files[i];
                srcFolder = new File(src, fileName);
                destFolder = new File(dest, fileName);
                copyFolder(srcFolder, destFolder);
            }
        } else {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(src);
                os = new FileOutputStream(dest);
                byte[] b = new byte[BUFF_SIZE];
                int len;
                while ((len = is.read(b)) != -1) {
                    os.write(b, 0, len);
                }
                os.flush();
            } finally {
                if (null != os) {
                    os.close();
                }
                if (null != is) {
                    is.close();
                }
            }
        }
    }

    /**
     * 删除目录
     */
    public boolean deleteFolder(String delpath) {
        File file = new File(delpath);
        return file.exists() ? deleteFolder(file) : false;
    }

    private boolean deleteFolder(File folder) {
        try {
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                for (int i = 0, len = files.length; i < len; i++) {
                    deleteFolder(files[i]);
                }
            }
            folder.delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 构建输出文件夹
     *
     * @return 构建好的文件
     */
    public static File makeFolder(File outPath) {
        File parentFile = outPath.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        return outPath;
    }

    /**
     * 构建输出文件夹
     *
     * @return 构建好的文件
     */
    public static File makeFolder(String outPath) {
        return makeFolder(new File(outPath));
    }

    /**
     * 获取文件名称中的扩展名
     *
     * @return [说明]
     */
    public String getExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * 检查文件的扩展是否在给定的扩展里，不存在返回false
     *
     * @return [说明]
     */
    public boolean isExtension(String fileName, List<String> extensions) {
        return FilenameUtils.isExtension(fileName, extensions);
    }


    /**
     * 移动文件
     *
     * @throws IOException [说明]
     */
    public void move(File srcFile, String destDirStr, boolean createDestDir) throws IOException {
        File destDir = new File(destDirStr);
        FileUtils.moveFileToDirectory(srcFile, destDir, createDestDir);

    }

    /**
     * 保存文件
     *
     * @return [说明]
     */
    public void save(String fileName, InputStream ins) throws IOException {
        if (ins == null) {
            return;
        }
        FileUtils.copyInputStreamToFile(ins, makeFolder(fileName));
    }

    /**
     * 把file文件读入OutputStream中
     *
     * @throws IOException [说明]
     */
    public void copyFileToOutputStream(String fileName, OutputStream output) throws IOException {
        File destDir = new File(fileName);
        FileUtils.copyFile(destDir, output);
    }

    /**
     * 把file文件读入OutputStream中
     *
     * @throws IOException [说明]
     */
    public void copyFileToOutputStream(File file, OutputStream output) throws IOException {
        FileUtils.copyFile(file, output);
    }

    /**
     * 获取相对目录
     *
     * @param path 相对路径
     * @return 返回值
     */
    public String relativeDir(String path) {
        String p = this.getClass().getClassLoader().getResource("/").getPath() + path;
        File dir = new File(p);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return p;
    }

}
