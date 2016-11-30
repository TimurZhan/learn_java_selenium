package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Создан вспомогательный класс. Т.н. помошник по переключению между разделами тестируемого приложения.
 */
public class NavigationHelper {


  private FirefoxDriver wd;

  public NavigationHelper(FirefoxDriver wd) {
    this.wd = wd;
  }

  //Создан отдельный вспомогательный метод для шага перехода в раздел "Группы".
  public void gotoGroupPage() {
      wd.findElement(By.linkText("groups")).click();
  }
}
