package ru.stqa.pft.addressbook.appmanager;

import com.sun.javafx.binding.StringFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import javax.enterprise.inject.New;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private final Properties properties;
  WebDriver wd;

  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private ContactHelper contactHelper;
  private String browser;
  private DbHelper dbHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));//Загрузка конфигурационного файла для выполнения тестов
    dbHelper = new DbHelper(); //Тут устанавливается соединение с БД.
    if (browser.equals(BrowserType.FIREFOX)){
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)){
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)){
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
    contactHelper = new ContactHelper(wd);
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword")); //Параметризовали данный метод, таким образом он стал универсальным. Можно входить под любым юзером
  }

  public void stop() {
    wd.quit();
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public NavigationHelper goTo() {//Метод, предоставляющий доступ к методу NavigationHelper, для других методов и классов.
    return navigationHelper;
  }

  public SessionHelper getSessionHelper() {
    return sessionHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public DbHelper db(){
    return dbHelper;
  }

}
