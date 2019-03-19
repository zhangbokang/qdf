package com.mycharx.qdf;

import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QdfApplicationTests {

    @Resource
    private DocumentConverter documentConverter;

    @Test
    public void contextLoads() {
        File src = new File("C:\\Users\\zhangbokang\\Desktop\\如何去除浏览器input自动填充效果.docx");
        File tag = new File("C:\\Users\\zhangbokang\\Desktop\\如何去除浏览器input自动填充效果.pdf");
        try {
            documentConverter.convert(src).to(tag).execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }

}
