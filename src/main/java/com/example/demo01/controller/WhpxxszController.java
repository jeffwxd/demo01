package com.example.demo01.controller;

import com.example.demo01.utils.Download;
import com.example.demo01.utils.DownloadFileUtil;
import com.example.demo01.utils.FileUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/api")
public class WhpxxszController {

    public static final String separator = File.separator;
    //文件上级目录
    public static final  String PATH = "user";
    //文件名
    public static final  String FILENAME ="系统.txt";

    //新的文件名
    public static final  String NewNAME ="模板.txt";

   /**
     * 下载模板
     * @return 返回excel模板
     */
    @RequestMapping(value = "/downloadWhpModel", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "模板下载", httpMethod = "GET", produces = "application/octet-stream")
    public Object downloadModel(){
        ResponseEntity<InputStreamResource> response = null;
        try {
            response = DownloadFileUtil.download(PATH, FILENAME, "导入模板");
        } catch (Exception e) {
            log.error("下载模板失败");
        }
        return response;
    }

    /**
     * 下载模板
     * @return 返回excel模板
     */
    @RequestMapping(value = "/downloadWhpModel01", method = RequestMethod.GET)
   // @ResponseBody
    @ApiOperation(value = "模板下载01", httpMethod = "GET", produces = "application/octet-stream")
    public Download downloadModel01(){
         final String separator = File.separator;
        //文件上级目录
         final  String PATH = "user";
        //文件名
         final  String FILENAME ="user.xlsx";

        //新的文件名
        final  String NewNAME ="模板.xlsx";

        String route = "static" + separator + "templates" + separator;
        String  path = route + PATH + separator + FILENAME;
            ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            InputStream inputStream = classPathResource.getInputStream();
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);
            return Download.buildExcel().setName(new String(NewNAME.getBytes(), StandardCharsets.ISO_8859_1)).setResults(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/download")
    @ApiOperation(value = "模板下载02", httpMethod = "GET", produces = "application/octet-stream")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        // 文件名
        String fileName = "1.xlsx";
        if (fileName != null) {
            //设置文件路径
            File file = new File("D://bigWriteTest.xlsx");
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }

}
