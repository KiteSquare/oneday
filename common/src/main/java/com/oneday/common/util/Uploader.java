package com.oneday.common.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.util.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
/**
 * UEditor文件上传辅助类
 *
 */
public class Uploader {
    // 输出文件地址
    private String url = "";
    // 上传文件名
    private String fileName = "";
    // 状态
    private String state = "";
    // 文件类型
    private String type = "";
    // 原始文件名
    private String originalName = "";
    // 文件大小
    private String size = "";
    private String basePath = null;

    private HttpServletRequest request = null;
    private String title = "";

    // 保存路径
    private String savePath = "upload";
    // 文件允许格式
    private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf",".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
    // 文件大小限制，单位KB
    private int maxSize = 10000;

    private HashMap<String, String> errorInfo = new HashMap<String, String>();

    public Uploader(HttpServletRequest request) {
        this.request = request;
        HashMap<String, String> tmp = this.errorInfo;
        tmp.put("SUCCESS", "SUCCESS"); //默认成功
        tmp.put("NOFILE", "未包含文件上传域");
        tmp.put("TYPE", "不允许的文件格式");
        tmp.put("SIZE", "文件大小超出限制");
        tmp.put("ENTYPE", "请求类型ENTYPE错误");
        tmp.put("REQUEST", "上传请求异常");
        tmp.put("IO", "IO异常");
        tmp.put("DIR", "目录创建失败");
        tmp.put("UNKNOWN", "未知错误");

    }

    public void upload() throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(this.request);
        if (!isMultipart) {
            this.state = this.errorInfo.get("NOFILE");
            return;
        }
        DiskFileItemFactory dff = new DiskFileItemFactory();
        String savePath = this.getFolder(this.savePath);
        dff.setRepository(new File(savePath));
        try {
            ServletFileUpload sfu = new ServletFileUpload(dff);
            sfu.setSizeMax(this.maxSize * 1024);
            sfu.setHeaderEncoding("utf-8");
            FileItemIterator fii = sfu.getItemIterator(this.request);
            while (fii.hasNext()) {
                FileItemStream fis = fii.next();
                if (!fis.isFormField()) {
                    this.originalName = fis.getName().substring(fis.getName().lastIndexOf(System.getProperty("file.separator")) + 1);
                    if (!this.checkFileType(this.originalName)) {
                        this.state = this.errorInfo.get("TYPE");
                        continue;
                    }
                    this.fileName = this.getName(this.originalName);
                    this.type = this.getFileExt(this.fileName);
                    this.url = savePath + "/" + this.fileName;
                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
                    FileOutputStream out = new FileOutputStream(new File(this.getPhysicalPath(this.url)));
                    BufferedOutputStream output = new BufferedOutputStream(out);
                    Streams.copy(in, output, true);
                    this.state=this.errorInfo.get("SUCCESS");
                    //UE中只会处理单张上传，完成后即退出
                    break;
                } else {
                    String fname = fis.getFieldName();
                    //只处理title，其余表单请自行处理
                    if(!fname.equals("pictitle")){
                        continue;
                    }
                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer result = new StringBuffer();
                    while (reader.ready()) {
                        result.append((char)reader.read());
                    }
                    this.title = new String(result.toString().getBytes(),"utf-8");
                    reader.close();

                }
            }
        } catch (SizeLimitExceededException e) {
            this.state = this.errorInfo.get("SIZE");
        } catch (InvalidContentTypeException e) {
            this.state = this.errorInfo.get("ENTYPE");
        } catch (FileUploadException e) {
            this.state = this.errorInfo.get("REQUEST");
        } catch (Exception e) {
            this.state = this.errorInfo.get("UNKNOWN");
        }
    }

    /**
     * 接受并保存以base64格式上传的文件
     * @param fieldName
     */
    public void uploadBase64(String fieldName){
        String savePath = this.getFolder(this.savePath);
        String base64Data = this.request.getParameter(fieldName);
        if (base64Data == null || !base64Data.startsWith("data:image/")) {
            throw new RuntimeException("没有图片数据");
        }
        this.fileName =  this.getName("test." + base64Data.substring(base64Data.indexOf("data:image/")+"data:image/".length(),base64Data.indexOf(";")));
//        this.fileName = this.getName("test.png");
        base64Data = base64Data.substring(base64Data.indexOf("base64,") + "base64,".length());
        this.url = savePath + "/" + this.fileName;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            File outFile = new File(this.getPhysicalPath(this.url));
            OutputStream ro = new FileOutputStream(outFile);
            byte[] b = decoder.decodeBuffer(base64Data);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            ro.write(b);
            ro.flush();
            ro.close();
            this.state=this.errorInfo.get("SUCCESS");
        } catch (Exception e) {
            this.state = this.errorInfo.get("IO");
        }
    }

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 依据原始文件名生成新文件名
     * @return
     */
    private String getName(String fileName) {
        Random random = new Random();
        return this.fileName = "" + random.nextInt(10000)
                + System.currentTimeMillis() + this.getFileExt(fileName);
    }

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     * @param path
     * @return
     */
    private String getFolder(String path) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        File dir = new File(this.getPhysicalPath(path));
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                this.state = this.errorInfo.get("DIR");
                return "";
            }
        }
        return path;
    }

    /**
     * 根据传入的虚拟路径获取物理路径
     *
     * @param path
     * @return
     */
    private String getPhysicalPath(String path) {
        String servletPath = this.request.getServletPath();
//        String realPath = this.request.getSession().getServletContext()
//                .getRealPath(servletPath);
        if (this.basePath == null) {
            this.basePath = this.request.getSession().getServletContext()
                    .getRealPath(servletPath);
        }
        return new File(this.basePath) +"/" +path;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setAllowFiles(String[] allowFiles) {
        this.allowFiles = allowFiles;
    }

    public void setMaxSize(int size) {
        this.maxSize = size;
    }

    public String getSize() {
        return this.size;
    }

    public String getUrl() {
        return this.url;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getState() {
        return this.state;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    public static void main(String[] args) {
//        String strImg = getImageStr("D:\\v2-b0a58563ecc47acf415dcf1730a89f9b_r.jpg");
//        System.out.println(strImg);
        String base64Data = "data:image/png;base64,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";
        String fileName =   base64Data.substring(base64Data.indexOf("data:image/")+11,base64Data.indexOf(";"));
//        this.fileName = this.getName("test.png");
        base64Data = base64Data.substring(base64Data.indexOf("base64,") + "base64,".length());
        System.out.println(fileName);
        System.out.println(base64Data);
    }
}