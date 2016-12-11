package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDelitionTests extends TestBase {
    @Test
    public void testContactModification() {
      if (! app.getContactHelper().isThereAContact()){//Создана проверка предусловия того, что удаляемая группа существует.
        app.getContactHelper().createContact(new ContactData("Test1", "Test2", "Test3", "Test Company", "Test address",
                "89073451234", "test1test3test2@mail.ru", "Test address 2", "Test1"), true);
      }
      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().selectToDeleteContact();
      app.getContactHelper().deleteContact();
      app.getSessionHelper().logoutProgram();
    }
}
