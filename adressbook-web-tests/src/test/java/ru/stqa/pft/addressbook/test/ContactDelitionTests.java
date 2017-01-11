package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDelitionTests extends TestBase {

  @BeforeMethod//Проверка предусловия
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0){//Создана проверка предусловия того, что удаляемая группа существует.
      app.contact().create(new ContactData()
              .withFirstname("sdfTest")
              .withMiddlename("Tsdfsdfesу2")
              .withLastname("Test3")
              .withCompanyName("Test Company")
              .withAddress1("Test address")
              .withMobilePhoneNumber("8900045001")
              .withEmail("test1test3test2@mail.ru")
              .withAddress2("Test address 2")
              .withGroup("Тест 2222"), true);
    }
  }

  @Test
  public void testContactDelition() {
    Contacts before = app.db().contacts();///Тут происходит подсчет количества контактов(элементов в списке) ДО удаления контакта.
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    //Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();///Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ удаления контакта.
    assertThat(after, equalTo(before.without(deletedContact)));
    //app.getSessionHelper().logoutProgram();
  }

}