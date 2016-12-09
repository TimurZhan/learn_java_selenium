package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Test1eee", "Test2eee", "Test3ggg","Test Companergergy", "Test address3gdg3",
            "89073451234", "test1test3test2@google.ru", "Test address 2dfgdfg", null), false); //false означает, что поле для выбора групп, тут НЕ должно быть
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
    app.getSessionHelper().logoutProgram();
  }
}