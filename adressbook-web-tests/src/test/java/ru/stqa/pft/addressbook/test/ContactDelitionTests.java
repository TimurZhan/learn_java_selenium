package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDelitionTests extends TestBase {
    @Test
    public void testContactDelition() {
      if (! app.getContactHelper().isThereAContact()){//Создана проверка предусловия того, что удаляемая группа существует.
        app.getContactHelper().createContact(new ContactData("Test1", "Test2", "Test3", "Test Company", "Test address",
                "89073451234", "test1test3test2@mail.ru", "Test address 2", "Test1"), true);
      }
      app.getNavigationHelper().returnToHomePage();
      List<ContactData> before = app.getContactHelper().getContactList();//Тут происходит подсчет количества контактов(элементов в списке) ДО удаления контакта.
      app.getContactHelper().selectToDeleteContact(before.size() - 1);
      app.getContactHelper().deleteContact();
      app.getNavigationHelper().returnToHomePage();
      List<ContactData> after = app.getContactHelper().getContactList();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ удаления контакта.
      Assert.assertEquals(after.size(), before.size() - 1);
      before.remove(before.size() - 1);
      Assert.assertEquals(before, after);
      app.getSessionHelper().logoutProgram();
    }
}
