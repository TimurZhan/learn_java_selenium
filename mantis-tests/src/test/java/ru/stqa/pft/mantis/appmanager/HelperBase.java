package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * Создан базовый класс для помошников
 */
public class HelperBase {

  protected ApplicationManager app;
  protected WebDriver wd;

  public HelperBase(ApplicationManager app) {
    this.app = app;
    this.wd = app.getDriver();
  }

/**Создан отдельный вспомогательный метод "click" для другого вспомогательного метода "submitGroupCreation" класса "GroupHelper".
 * Но этот метод можно использовать и в других классах
 */
  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  protected void clear(By locator){
    wd.findElement(locator).clear();
  }

  /**Создан отдельный вспомогательный метод "type" для другого вспомогательного метода "fillGroupForm" класса "GroupHelper".
   * Но этот метод можно использовать и в других классах
  */
  protected void type(By locator, String text) {
    clear(locator);
    click(locator);
    if (text != null) {
      String exsistingText = wd.findElement(locator).getAttribute("value"); //Извлечение значения из поля, в котором оно хранится.
      if (! text.equals(exsistingText)) { //Не вводить текст, если он совпадает с уже существующим текстом. Тогда необходимо совершить действия приведенные в теле кода.
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  protected void attach(By locator, File file) {
    clear(locator);
    if (file != null) {
        wd.findElement(locator).sendKeys(file.getAbsolutePath());//В метод sendKeys передается абсолютный путь к файлу: file.getAbsolutePath()
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
