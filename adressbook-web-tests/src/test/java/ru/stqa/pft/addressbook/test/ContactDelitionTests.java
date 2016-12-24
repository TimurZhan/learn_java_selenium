package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDelitionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactPage();
    if (app.contact().list().size() == 0) {//Создана проверка предусловия того, что удаляемый контакт существует. Если же контакт отсутствет, то его надо создать
      app.contact().create(new ContactData().withFirstname("Test1").withMiddlename("Fddfgdg").withLastname("ssdfsdf"), true);
    }
  }

  @Test//(enabled = false)
  public void testContactDelition() {
    List<ContactData> before = app.contact().list();//Тут происходит подсчет количества контактов(элементов в списке) ДО удаления контакта.
    int index = before.size() - 1;
    app.contact().delete(index);
    app.goTo().contactPage();
    List<ContactData> after = app.contact().list();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ удаления контакта.
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(index);
    Assert.assertEquals(before, after);
    //app.getSessionHelper().logoutProgram();
  }

}
