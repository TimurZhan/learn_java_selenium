package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Создан вспомогательный класс. Т.н. помошник по работе с руппами.
 */
public class GroupHelper {

  private FirefoxDriver wd;

  public GroupHelper(FirefoxDriver wd) {
    this.wd = wd;
  }

  //Создан отдельный вспомогательный метод для шага вовзврата в раздел "Группы".
  public void returnToGroupPage() {
      wd.findElement(By.linkText("group page")).click();
  }

  //Создан отдельный вспомогательный метод для шага создания элемента в разделе "Группы".
  public void submitGroupCreation() {
      wd.findElement(By.name("submit")).click();
  }

  //Создан отдельный вспомогательный метод для шага заполнения формы создаваемой группы.
  public void fillGroupForm(GroupData groupData) {
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
  public void initGroupCreation() {
      wd.findElement(By.name("new")).click();
  }

  public void deleteSelectedGroups() {
      wd.findElement(By.name("delete")).click();
  }

  public void selectGroup() {
      wd.findElement(By.name("selected[]")).click();
  }
}
