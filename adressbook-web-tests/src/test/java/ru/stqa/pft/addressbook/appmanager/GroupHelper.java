package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Создан вспомогательный класс. Т.н. помошник по работе с руппами.
 */
public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  //Создан отдельный вспомогательный метод для шага вовзврата в раздел "Группы".
  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  //Создан отдельный вспомогательный метод для шага создания элемента в разделе "Группы".
  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  //Создан отдельный вспомогательный метод для шага заполнения формы создаваемой группы.
  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  //Создан отдельный вспомогательный метод для шага инициализации создания элемента в разделе "Группы".
  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup() {
    click(By.name("selected[]"));
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void createGroup(GroupData group) {
    initGroupCreation();
    fillGroupForm(group); //Данный метод сделан универсальным
    submitGroupCreation();
    returnToGroupPage();
  }

  //Создан отдельный метод для проверки наличия уже созданной группы
  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }
}
