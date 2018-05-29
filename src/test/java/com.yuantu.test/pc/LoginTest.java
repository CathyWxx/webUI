package com.yuantu.test.pc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class LoginTest {
    {
        private static WebDriver driver;
        private static String loginURL = "http://uat.yuantutech.com/yuantu/web/1.0.42/#/login";

        // 电话验证数据
        @DataProvider(name = "user")
        public Object[][] Users()
        {
            return new Object[][]
                    {
                            //{ "12345678910", "请输入正确的手机号码" },//电话不合法，13\14\15\17\18\合法
                            { "1806797990", "请输入11位手机号码" },//电话位数<11
                            { "180679799020", "请输入11位手机号码" },//电话位数>11
                            { "1806797990a", "请输入11位手机号码" },//电话包含字母
                            { " ", "请输入11位手机号码" }, //电话为空
                    };
        }

        /***
         * 电话校验
         */
        @Test(dataProvider = "user")
        public void TeleError(String userName, String text)
        {
            // TODO Auto-generated method stub
            driver.get(loginURL);
            driver.manage().window().maximize();

            WebElement loginname = driver.findElement(By.name("phoneNum-login"));
            loginname.sendKeys(userName);
            WebElement login = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[1]/div/form/ul/li[4]/input"));
            login.click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            WebElement alert = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[1]/div/div/span[2]"));
            System.out.println(userName);
            System.out.println(alert.getText());
            Assert.assertEquals(alert.getText(), text);
        }

        // 密码校验数据
        @DataProvider(name = "password")
        public Object[][] Password()
        {
            return new Object[][]
                    {
                            { "18067979902",  "abcde", "请输入6-20个字符的密码" },//密码<6位
                            { "18067979902",  "123456789012345678901", "请输入6-20个字符的密码" },//密码>20位
                            //{ "18067979902",  "123456aaa*", "密码只能由6-20位的字母、数字组成" },
                            { "18067979902",  " ", "请输入6-20个字符的密码" }, //密码为空
                    };
        }

        /**
         * 密码校验
         */
        @Test(dataProvider = "password")
        public void Password(String userName,  String Password, String text)
        {
            // TODO Auto-generated method stub
            driver.get(loginURL);
            driver.manage().window().maximize();

            WebElement loginname = driver.findElement(By.name("phoneNum-login"));
            loginname.sendKeys(userName);

            WebElement loginpassword = driver.findElement(By.name("pwd-login"));
            loginpassword.sendKeys(Password);

            WebElement login = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[1]/div/form/ul/li[4]/input"));
            login.click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement alert = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[1]/div/div/span[2]"));
            System.out.println(alert.getText());
            Assert.assertEquals(alert.getText(), text);
        }


        @BeforeMethod
        public void beforeMethod()
        {
            System.out.println("********** TestCase Start **********");
            driver = new ChromeDriver();
        }

        @AfterMethod
        public void afterMethod()
        {
            driver.quit();// 退出浏览器
            System.out.println("********** TestCase Stop **********\n");
        }

        @BeforeClass
        public void beforeClass()
        {
            System.out.println("========== Test   Start ==========");
        }

        @AfterClass
        public void afterClass()
        {
            driver.quit();// 退出浏览器
            System.out.println("========== Test   Stop ==========");
        }
    }

}
