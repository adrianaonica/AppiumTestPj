package testng;

import org.testng.annotations.Test;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class WordpressTest2 extends BaseTest{

    @Test
    public void testWordpress2(){

        System.out.println("testWordpress2");

        driver.get("http://www.stackoverflow.com");

    }

    @Test
    public void testWordpress1(){

        System.out.println("testWordpress1");
        driver.get("http://www.wordpress.com");

    }
}
