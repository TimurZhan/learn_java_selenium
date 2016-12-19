package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase  {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContact() {
    click(By.xpath("//*[@id='content']/*/input[@value='Enter']"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) { // boolean creation - это параметр, определяющий создается контакт или только модифиципуется.
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("company"), contactData.getCompanyName());
    type(By.name("address"), contactData.getAddress1());
    type(By.name("mobile"), contactData.getMobilePhoneNumber());
    type(By.name("email"), contactData.getEmail());
    type(By.name("address2"), contactData.getAddress2());

    if (creation) { //Если это форма создания контакта, то элемент выбора групп должен быть
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(ContactData.getGroup()); //
    } else {//Если это форма модификации контакта, то элемент выбора групп тут НЕ должен быть
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

  }

  public void initContact() {
    click(By.linkText("add new"));
  }

  public void initContactModification() {
    click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void deleteContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void selectToDeleteContact(int index) {
    wd.findElements(By.xpath("//div/div[4]/form[2]/table/tbody//input[@name='selected[]']")).get(index).click();
  }

  public void createContact(ContactData contact, boolean creation) {
    initContact();
    fillContactForm(contact, true); //true означает, что поле для выбора групп, тут должно быть
    submitContact();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  //Создан отдельный метод подсчитывающий количество контактов
  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  //Создан отдельный метод подсчитывающий количество контактов, как объектов в списке
  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));//Получаем список объектов типа element по тегу tr, у которого параметр name.
    for (WebElement element : elements){//Инициализируем цикл по перебору массива полученных элементов.
      List<WebElement> cells = element.findElements(By.tagName("td"));//Так как имя и фамилия пользователя - это текст отдельных ячеек строки, строку разбиваем на ячейки
      String lastname = cells.get(1).getText();//Получаем от элемента его текст, который идет в переменную lastname
      String firstname = cells.get(2).getText();//Получаем от элемента его текст, который идет в переменную firstname
      //Получаем элемент input, у которого получаем аттрибут id, и сохраняем это все в переменную id. Метод Integer.parseInt преобразует строку в число.
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData(id, firstname, null, lastname, null,
              null, null, null, null, null);// Создаем объект типа ContactData с именем contact, который будет использоваться для добавления в список
      contacts.add(contact);// Добавляем созданный объект в спискок
    }
    return contacts;
  }
}
