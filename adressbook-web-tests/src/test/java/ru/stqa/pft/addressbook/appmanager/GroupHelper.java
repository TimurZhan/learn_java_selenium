package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group); //Данный метод сделан универсальным
    submitGroupCreation();
    groupCashe = null; //На данном шаге очищаем кеш (это происходит, если список групп изменился).
    returnToGroupPage();
  }

  public void modify(GroupData group) { //Метод для модификации групп в адресной книге
    selectGroupById(group.getId());//В параметре указывается индекс элемента. "before - 1", это последний элемент в списке
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    groupCashe = null; //На данном шаге очищаем кеш (это происходит, если список групп изменился).
    returnToGroupPage();
  }

  public void delete(GroupData group) {//В параметре указывается элемент (группа), который будет удален. "before - 1", это последний элемент в списке
    selectGroupById(group.getId());// Группа будет удаляться по Id
    deleteSelectedGroups();
    groupCashe = null; //На данном шаге очищаем кеш (это происходит, если список групп изменился).
    returnToGroupPage();
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroupById(int id) {//Данный метод выбирает группу по ID (принимает в параметр id элемента).
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();//Выбирает выбранный по id элемент.
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  //Создан отдельный метод для проверки наличия уже созданной группы
  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  //Создан отдельный метод подсчитывающий количество групп
  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  //Создан метод для кеширования групп (его множества).
  private Groups groupCashe = null;

  //Создан отдельный метод подсчитывающий количество групп как объектов и возвращающий их как множество (НЕ упорядоченное количество элементов)
  public Groups all() {
    if (groupCashe != null){//Тут происходит проверка, если кеш не пустой, то его и вовзвращаем, точнее его копию.
      return new Groups(groupCashe);
    }
    groupCashe = new Groups();//Тут создается множество элементов типа GroupData
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));//Получаем список объектов типа element по тегу span, у которого параметр класс group.
    for (WebElement element : elements){//Инициализируем цикл по перебору массива полученных элементов.
      String name = element.getText();//Получаем с каждого элемента его текст, который идет в переменную name
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));//Получаем элемент input, у которого получаем аттрибут value, и сохраняем это все в переменную id. Метод Integer.parseInt преобразует строку в число.
      groupCashe.add(new GroupData().withId(id).withName(name));//Создаем объект типа GroupData с именем group, который будет использоваться для добавления в список
    }
    return new Groups(groupCashe);//Тут возвращаем копию кеша
  }

}
