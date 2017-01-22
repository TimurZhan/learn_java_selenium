package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteFromGroups extends TestBase {


  @BeforeMethod//Проверка предусловия
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0){ //Создана проверка предусловия того, что Контакт существует.
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

    if (app.db().groups().size() == 0){ //Создана проверка предусловия того, что группа существует.
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test1"));
    }
  }

  @Test
  public void testContactDeleteFromGroups() {
    app.goTo().homePage();
    Contacts before = app.db().contacts();///Тут происходит подсчет количества контактов(элементов в списке)
    Groups groups = app.db().groups();
    ContactData selectedAContact = before.iterator().next();
    GroupData group = groups.iterator().next();
    Groups beforeGroups = app.db().getIdGroupsToContact(selectedAContact.getId());
    app.contact().selecttingContactAdded1();
    app.contact().deleteContactFromGroup(selectedAContact);
    app.goTo().homePage();
    app.contact().selecttingContactAdded2();
    Groups afterGroups=app.db().getIdGroupsToContact(selectedAContact.getId());
    assertThat(afterGroups, equalTo(beforeGroups.without(group)));
  }

}