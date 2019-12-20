package com.seckill.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2018/04/13.
 */
public class MediaUtils {
	
	private static Logger logger = Logger.getLogger(MediaUtils.class);

    /**
     * 得到文件的类型。
     * 实际上就是得到文件名中最后一个“.”后面的部分。
     * @param fileName 文件名
     * @return 文件名中的类型部分
     * @since  1.0
     */
    public static String getTypePart(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        }
        else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 读取文件的内容
     * 读取指定文件的内容
     * @param path 为要读取文件的绝对路径
     * @return 以行读取文件后的内容
     * @throws IOException
     */
    public static final String getFileContent( String path) throws IOException
    {
        String filecontent = "";
        try {
            File f = new File(path);
            if (f.exists()) {
                FileReader fr = new FileReader(path);
                BufferedReader br = new BufferedReader(fr); //建立BufferedReader对象，并实例化为br
                String line = br.readLine(); //从文件读取一行字符串
                //判断读取到的字符串是否不为空
                while (line != null) {
                    filecontent += line + "\n";
                    line = br.readLine(); //从文件中继续读取一行数据
                }
                br.close(); //关闭BufferedReader对象
                fr.close(); //关闭文件
            }

        }
        catch (IOException e) {
            throw e;
        }
        return filecontent;
    }

    /**
     * 压缩文件-由于out要在递归调用外,所以封装一个方法用来
     * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles)
     * @param zip
     * @param path
     * @param srcFiles
     * @throws IOException
     */
    public static void ZipFiles(File zip,String path,File... srcFiles) throws IOException{
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
        MediaUtils.ZipFiles(out,path,srcFiles);
        out.close();
    }
    /**
     * 压缩文件-File
     * @param out  zip文件
     * @param srcFiles 被压缩源文件
     */
    public static void ZipFiles(ZipOutputStream out,String path,File... srcFiles){
    	path = path.replaceAll("\\*", "/");
//        if(!path.endsWith("/")){
//            path+="/";
//        }
        byte[] buf = new byte[1024];
        try {
            for(int i=0;i<srcFiles.length;i++){
                if(srcFiles[i].isDirectory()){
                    File[] files = srcFiles[i].listFiles();
                    String srcPath = srcFiles[i].getName();
                    srcPath = srcPath.replaceAll("\\*", "/");
                    if(!srcPath.endsWith("/")){
                        srcPath+="/";
                    }
                    out.putNextEntry(new ZipEntry(path+srcPath));
                    ZipFiles(out,path+srcPath,files);
                }
                else{
                    FileInputStream in = new FileInputStream(srcFiles[i]);
                    out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
                    int len;
                    while((len=in.read(buf))>0){
                        out.write(buf,0,len);
                    }
                    out.closeEntry();
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压到指定目录
     * @param zipPath
     * @param descDir
     */
    public static void unZipFiles(String zipPath,String descDir)throws IOException{
        unZipFiles(new File(zipPath), descDir);
    }
    /**
     * 解压文件到指定目录
     * @param zipFile
     * @param descDir
     */
    public static void unZipFiles(File zipFile,String descDir)throws IOException{
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
        	zip = new ZipFile(zipFile, Charset.forName("GBK"));
        } catch (Exception e) {
        	zip = new ZipFile(zipFile, Charset.forName("UTF-8"));
        }
        for(Enumeration<?> entries = zip.entries();entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");
            Integer index = outPath.lastIndexOf('/');
            //判断路径是否存在,不存在则创建文件路径
            if (index == -1) {
				index = outPath.lastIndexOf('\\');
			}
            File file = new File(outPath.substring(0, index));
            if(!file.exists()){
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if(new File(outPath).isDirectory()){
                continue;
            }
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while((len=in.read(buf1))>0){
                out.write(buf1,0,len);
            }
            in.close();
            out.close();
        }
        zip.close();
    }


    /**
     * 递归获取文件夹里的所有文件
     * @param path 文件夹路径
     * @return 文件夹下所有文件
     */
    public static List<File> traverseFolder2(String path) {

        File file = new File(path);
        List<File> fileList = new ArrayList<File>();
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return fileList;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        fileList.add(file2);
                    }
                }
            }
        }
        return  fileList;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static void main(String[] args) throws IOException {
        /**
         * 压缩文件
         */
//        String path1 = "E:\\Code\\201710\\09\\A012231";
//        File[] files = new File[]{new File(path1+"\\123.txt"),new File(path1+"\\123.json"),new File(path1+"\\en.json")};
//        File zip = new File(path1+"\\A012231.zip");
//        ZipFiles(zip,"\\zip",files);

        /**
         * 解压文件
         */
//        File zipFile = new File(path1+"\\A012231.zip");
//        String path = path1;
//        unZipFiles(zipFile, path);
//
//        List<File> files1 = traverseFolder2(path1);
//        System.out.println(files1);
//        System.out.println("文件路径："+files1.get(0).getPath());
//        deleteFile("E:\\Code\\201710\\09\\A012231\\zip\\123.json");
    		deleteDirectory("d:/download/");
    }
    
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * @param   sPath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符
	    if (!sPath.endsWith(File.separator)) {
	        sPath = sPath + File.separator;
	    }
	    File dirFile = new File(sPath);
	    //如果dir对应的文件不存在，或者不是一个目录，则退出
	    if (!dirFile.exists() || !dirFile.isDirectory()) {
	        return false;
	    }
	    boolean flag = true;
	    //删除文件夹下的所有文件(包括子目录)
	    File[] files = dirFile.listFiles();
	    for (int i = 0; i < files.length; i++) {
	        //删除子文件
	        if (files[i].isFile()) {
	            flag = deleteFile(files[i].getAbsolutePath());
	            if (!flag) {break;}
	        } //删除子目录
	        else {
	            flag = deleteDirectory(files[i].getAbsolutePath());
	            if (!flag) {break;}
	        }
	    }
	    if (!flag) {return false;}
	    //删除当前目录
	    if (dirFile.delete()) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	/**
	 * 将内容写入指定文件
	 * @param content 待写入内容
	 * @param path 写入文件路径
	 * @param fileName 文件名
	 * @param isAppend 是否追加写入
	 * @return 
	 */
	public static boolean writeFile(String content , 
			String path , String fileName , Boolean isAppend) {
		BufferedWriter bufferedWriter = null;
		try {
			if (!mkdirs(path)) {
				return false;
			}
			File file = new File(path+fileName);
			bufferedWriter = new BufferedWriter(new FileWriter(file, isAppend));
			bufferedWriter.write(content);
			bufferedWriter.write("\n");
			bufferedWriter.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (Exception e2) {
					logger.error("流关闭失败",e2);
				}
			}
		}
	}
	
	/**
     * 如果目录不存在就创建目录
     * @param path 
     */
    public static boolean mkdirs(String path){
    	try {
    		File f = new File(path);
            if (f.isFile()) {
            	logger.info("创建失败，该路径不是目录");
    			return false;
    		}
            if (!f.exists()){
                f.mkdirs();
                logger.info("创建目录成功");
            }	
		} catch (Exception e) {
			logger.error("创建目录失败",e);
			return false;
		}
		return true;
    }
}
