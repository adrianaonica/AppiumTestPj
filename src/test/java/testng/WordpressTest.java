package testng;

import org.testng.annotations.Test;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class WordpressTest extends BaseTest{


    @Test
    public void test1(){
        System.out.println("Test 1");

        driver.get("http://www.google.co.in");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        System.out.println("Test 1");

        driver.get("http://www.google.co.uk");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
