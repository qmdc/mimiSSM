import com.konan.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTest {
    @Test
    public void MD5() {
        String md= MD5Util.getMD5("000000");
        System.out.println(md);
    }

    @Test
    public void Time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        System.out.println(simpleDateFormat.format(date));
    }

    @Test
    public void subString() {
        String  originalFilename="abcdefg.text";
        int i = originalFilename.lastIndexOf(".");
        String s = originalFilename.substring(i);
        System.out.println(s);
    }
}
