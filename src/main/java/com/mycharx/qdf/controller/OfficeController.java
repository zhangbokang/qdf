package com.mycharx.qdf.controller;

import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * The type Office controller.
 *
 * @author 张卜亢
 * @date 2019.03.19 18:19:13
 */
@Controller
public class OfficeController {

    @Resource
    private DocumentConverter documentConverter;

    /**
     * 预览pdf文件
     * @param docUrl
     * @param request
     * @param response
     */
    @RequestMapping("/readpad")
    public void readPdf(String docUrl, HttpServletRequest request, HttpServletResponse response) {
//        下载文件

        response.reset();
        response.setContentType("application/pdf");
        try {
//            File file = new File(dest);
            File file = new File("C:\\Users\\zhangbokang\\Desktop\\如何去除浏览器input自动填充效果.pdf");
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            response.setHeader("Content-Disposition", "inline; filename= file");
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 Word 转换为 pdf.
     *
     * @param srcPath the src path
     * @param tagPath the tag path
     * @author 张卜亢
     * @date 2019.03.19 18:19:23
     */
    @RequestMapping("/wordToPdf")
    public void wordToPdf(String srcPath, String tagPath) {
        File src = new File(srcPath);
        File tag = new File(tagPath);
        try {
            documentConverter.convert(src).to(tag).execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }
}
