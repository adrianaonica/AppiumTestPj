package testng;

import org.testng.annotations.Test;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class WordpressTest2 extends BaseTest{

    @Test
    public void testWordpress2(){

        System.out.println("testWordpress2");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
