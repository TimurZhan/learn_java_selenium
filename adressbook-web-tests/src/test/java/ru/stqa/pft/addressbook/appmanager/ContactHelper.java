package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

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
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("address2"), contactData.getAddress2());
    attach(By.name("photo"), contactData.getPhoto());//Метод getAbsolutePath преобразует данные в строку. При этом он содержит абослютный путь к файлу.
    //if (creation) { //Если это форма создания контакта, то элемент выбора групп должен быть
      //if (contactData.getGroups().size() > 0) {
        //Assert.assertTrue(contactData.getGroups().size() == 1);
       // new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName()); //
      //}
   // } else {//Если это форма модификации контакта, то элемент выбора групп тут НЕ должен быть
     // Assert.assertFalse(isElementPresent(By.name("new_group")));
    //}
  }

  public ContactData InfoFromEditFrom(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address1 = wd.findElement(By.xpath(".//textarea[@name='address']")).getText();
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String Email1 = wd.findElement(By.name("email")).getAttribute("value");
    String Email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String Email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstname(firstname)
            .withLastname(lastname)
            .withAddress1(address1)
            .withHomePhoneNumber(home)
            .withMobilePhoneNumber(mobile)
            .withWorkPhoneNumber(work)
            .withEmail(Email1)
            .withEmail2(Email2)
            .withEmail3(Email3);

  }

  public void viewById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();
  }

  public void infoDetails() {
    wd.findElement(By.cssSelector("div[id='content']")).getText();
  }

  public void initContact() {
    click(By.linkText("add new"));
  }

  public void create(ContactData contact, boolean creation) {
    initContact();
    fillContactForm(contact, true); //true означает, что поле для выбора групп, тут должно быть
    submitContact();
    contactCashe = null;
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false); //false означает, что поле для выбора групп, тут НЕ должно быть
    submitContactModification();
    contactCashe = null;
  }


  public void initContactModificationById(int id) {
    wd.findElement(By.xpath(String.format("//a[@href='edit.php?id=%s']", id))).click();
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void delete(ContactData deletedContact) {
    selectToDeleteContactById(deletedContact.getId());
    deleteContact();
    contactCashe = null;
  }

  public void selectToDeleteContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.cssSelector("img[title='Edit']"));
  }

  //Создан отдельный метод подсчитывающий количество контактов
  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCashe = null;

  //Создан отдельный метод подсчитывающий количество контактов, как объектов в списке
  public Contacts all() {
    if (contactCashe != null) {
      return new Contacts(contactCashe);
    }
    contactCashe = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));//Получаем список объектов типа element по тегу tr, у которого параметр name.
    for (WebElement element : elements) {//Инициализируем цикл по перебору массива полученных элементов.
      List<WebElement> cells = element.findElements(By.tagName("td"));//Так как имя и фамилия пользователя - это текст отдельных ячеек строки, строку разбиваем на ячейки
      String lastname = cells.get(1).getText();//Получаем от элемента его текст, который идет в переменную lastname
      String firstname = cells.get(2).getText();//Получаем от элемента его текст, который идет в переменную firstname
      String address = cells.get(3).getText();
      String allEmail = cells.get(4).getText();
      String allPhones = cells.get(5).getText();//Тут получаем текст из 6 яйчейки (где телефоны). А затем, при помощи метода "split" режем этот тест на отдельные яйчейки.
      //Получаем элемент input, у которого получаем аттрибут id, и сохраняем это все в переменную id. Метод Integer.parseInt преобразует строку в число.
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      //Создаем и добавляем объект типа ContactData с именем contact, который будет использоваться для добавления в список
      contactCashe.add(new ContactData()
              .withId(id)
              .withFirstname(firstname)
              .withLastname(lastname)
              .withAddress1(address)
              .withAllEmail(allEmail)
              .withAllPhones(allPhones));
    }
    return new Contacts(contactCashe);
  }
}