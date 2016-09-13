package testng;

import org.testng.annotations.Test;

/**
 * Created by pritamkadam on 13/09/16.
 */
public class WordpressTest extends BaseTest{


    @Test
    public void testAppiumServer(){
        System.out.println("testAppiumServer");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
