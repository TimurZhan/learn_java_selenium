package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Создан вспомогательный класс. Т.н. помошник по переключению между разделами тестируемого приложения.
 */
public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);//Ссылка на драйвер Firefox наследуется из HelperBase
  }

  //Создан отдельный вспомогательный метод для шага перехода в раздел "Группы".
  public void groupPage() {
    if (isElementPresent(By.tagName("h1"))// Создана конструкция "if", которая проверяет дислоцирование на странице "Groups"
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))){
      return;
    }
      click(By.linkText("groups"));
  }

  //Создан отдельный вспомогательный метод для шага перехода в раздел "Контакты".
  public void homePage() {
    if (isElementPresent(By.id("maintable"))){
      return;
    }
    click(By.linkText("home"));
  }

}
