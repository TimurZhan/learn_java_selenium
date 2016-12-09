package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
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
    if (text != null) {
      String exsistingText = wd.findElement(locator).getAttribute("value"); //Извлечение значения из поля, в котором оно хранится.
      if (! text.equals(exsistingText)) { //Если не верно, что текст совпадает с уже существующим текстом, тогда необходимо совершить действия приведенные в теле кода.
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public boolean isElementPresent(By locator){ //Создан метод, проверяющий наличие или отсутствие элементов на странице.
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex){
      return false;
    }
  }
}
