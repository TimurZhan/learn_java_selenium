package ru.stqa.pft.addressbook;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;

public class GroupCreationTests {
    FirefoxDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wd.get("http://localhost:8080/addressbook/");
        login("admin", "secret"); //Параметризовали данный метод, таким образом он стал униыерсальным. Можно входить под любым юзером
    }

    private void login(String username, String password) {
        wd.findElement(By.name("user")).click();
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys(username);
        wd.findElement(By.name("pass")).click();
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys(password);
        wd.findElement(By.xpath("//form[@id='LoginForm']/input[3]")).click();
    }

    @Test
    public void testGroupCreation() {
        //Тут каждый шаг теста выделен в отдельный вспомогательный метод
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd")); //Данный метод сделан универсальным
        submitGroupCreation();
        returnToGroupPage();
        //wd.findElement(By.linkText("Logout")).click();

    }

    //Создан отдельный вспомогательный метод для шага вовзврата в раздел "Группы".
    private void returnToGroupPage() {
        wd.findElement(By.linkText("group page")).click();
    }

    //Создан отдельный вспомогательный метод для шага создания элемента в разделе "Группы".
    private void submitGroupCreation() {
        wd.findElement(By.name("submit")).click();
    }

    //Создан отдельный вспомогательный метод для шага заполнения формы создаваемой группы.
    private void fillGroupForm(GroupData groupData) {
        wd.findElement(By.name("group_name")).click();
        wd.findElement(By.name("group_name")).clear();
        wd.findElement(By.name("group_name")).sendKeys(groupData.getName());
        wd.findElement(By.name("group_header")).click();
        wd.findElement(By.name("group_header")).clear();
        wd.findElement(By.name("group_header")).sendKeys(groupData.getHeader());
        wd.findElement(By.name("group_footer")).click();
        wd.findElement(By.name("group_footer")).clear();
        wd.findElement(By.name("group_footer")).sendKeys(groupData.getFooter());
    }

    //Создан отдельный вспомогательный метод для шага инициализации создания элемента в разделе "Группы".
    private void initGroupCreation() {
        wd.findElement(By.name("new")).click();
    }

    //Создан отдельный вспомогательный метод для шага перехода в раздел "Группы".
    private void gotoGroupPage() {
        wd.findElement(By.linkText("groups")).click();
    }

    @AfterMethod
    public void tearDown() {
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
