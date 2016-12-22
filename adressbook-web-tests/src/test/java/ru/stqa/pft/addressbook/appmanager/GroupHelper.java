package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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

  public void modify(int index, GroupData group) { //Метод для модификации групп в адресной книге
    selectGroup(index);//В параметре указывается индекс элемента. "before - 1", это последний элемент в списке
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  public void delete(int index) {
    selectGroup(index);//В параметре указывается индекс элемента. "before - 1", это последний элемент в списке
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int index) {//Принимает в параметр индекс элемента.
    wd.findElements(By.name("selected[]")).get(index).click();//Выбирает выбранный по индексу элемент.
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group); //Данный метод сделан универсальным
    submitGroupCreation();
    returnToGroupPage();
  }

  //Создан отдельный метод для проверки наличия уже созданной группы
  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  //Создан отдельный метод подсчитывающий количество групп
  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  //Создан отдельный метод подсчитывающий количество групп как объектов в списке
  public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));//Получаем список объектов типа element по тегу span, у которого параметр класс group.
    for (WebElement element : elements){//Инициализируем цикл по перебору массива полученных элементов.
      String name = element.getText();//Получаем с каждого элемента его текст, который идет в переменную name
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));//Получаем элемент input, у которого получаем аттрибут value, и сохраняем это все в переменную id. Метод Integer.parseInt преобразует строку в число.
      groups.add(new GroupData().withId(id).withName(name));//Создаем объект типа GroupData с именем group, который будет использоваться для добавления в список
    }
    return groups;
  }
}
