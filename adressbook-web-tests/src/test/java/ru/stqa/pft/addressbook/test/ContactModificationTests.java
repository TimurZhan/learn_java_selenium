package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod//Проверка предусловия
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0){//Создана проверка предусловия того, что модифицируемая группа существует.
      app.contact().create(new ContactData()
              .withFirstname("sdfTest")
              .withMiddlename("Tsdfsdfesу2")
              .withLastname("Test3")
              .withCompanyName("Test Company")
              .withAddress1("Test address")
              .withMobilePhoneNumber("8900045001")
              .withEmail("test1test3test2@mail.ru")
              .withAddress2("Test address 2"), true);
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifidedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifidedContact.getId())
            .withFirstname("sdfTest")
            .withMiddlename("Tsdfsdfesу2")
            .withLastname("Test3")
            .withCompanyName("Test Company")
            .withAddress1("Testaddress")
            .withMobilePhoneNumber("8900045001")
            .withEmail("test1test3test2@mail.ru")
            .withAddress2("Test address 2")
            .withPhoto(new File("src/test/resources/sc.jpg"));
    app.contact().modify(contact);
    app.goTo().homePage();
    //Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifidedContact).withAdded(contact)));//Тут реализовано сравнение списков after и before между собой
    verifyContactListInUI();//Сравнивает между собой списки контактов выкачанных из БД и те, которые отображаются на странице веб-приложения. Ее можно отключать и включать.
  }
}