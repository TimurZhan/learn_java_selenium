package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Создан вспомогательный класс. Т.н. помошник по переключению между разделами тестируемого приложения.
 */
public class NavigationHelper extends HelperBase {

  public NavigationHelper(FirefoxDriver wd) {
    super(wd);//Ссылка на драйвер Firefox наследуется из HelperBase
  }

  //Создан отдельный вспомогательный метод для шага перехода в раздел "Группы".
  public void gotoGroupPage() {
    click(By.linkText("groups"));
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

}
