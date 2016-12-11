package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()){//Создана проверка предусловия того, что удаляемая группа существует.
      app.getContactHelper().createContact(new ContactData("Test1", "Test2", "Test3", "Test Company", "Test address",
              "89073451234", "test1test3test2@mail.ru", "Test address 2", "Test1"), true);
    }
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Testsdfsdfsdf1", "sdsdsdfsddsfTest2", "Test3", "Test Company", "Test address",
            "89073451234", "test1test3test2@mail.ru", "Test address 2", "Test1"), false); //false означает, что поле для выбора групп, тут НЕ должно быть
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
    app.getSessionHelper().logoutProgram();
  }
}