package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Создан вспомогательный класс. Т.н. помошник авторизации в приложении
 */

public class SessionHelper extends HelperBase {


  public SessionHelper(FirefoxDriver wd) {
    super(wd);//Ссылка на драйвер Firefox наследуется из HelperBase
  }

  public void login(String username, String password) {
    type(By.name("user"), username);
    type(By.name("pass"), password);
    click(By.xpath("//form[@id='LoginForm']/input[3]"));
  }

  public void logoutProgram() {
    click(By.linkText("Logout"));
  }

}
