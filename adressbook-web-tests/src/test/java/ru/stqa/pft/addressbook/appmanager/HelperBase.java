package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Создан базовый класс для помошников
 */
public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

/**Создан отдельный вспомогательный метод "click" для другого вспомогательного метода "submitGroupCreation" класса "GroupHelper".
 * Но этот метод можно использовать и в других классах
 */
  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  /**Создан отдельный вспомогательный метод "type" для другого вспомогательного метода "fillGroupForm" класса "GroupHelper".
   * Но этот метод можно использовать и в других классах
  */
  protected void type(By locator, String text) {
    click(locator);
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }
}
