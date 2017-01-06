package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void contactCreationTests() {
    app.goTo().homePage();
    Contacts before = app.contact().all();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
    File photo = new File("src/test/resources/rt.jpg");
    ContactData contact = new ContactData()
            .withFirstname("sdfTest")
            .withLastname("Test3")
            .withAddress1("Test address")
            .withMobilePhoneNumber("8900045001")
            .withEmail("test1test3test2@mail.ru")
            .withPhoto(photo);
    app.contact().create(contact, true);
    app.goTo().homePage();
    //Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));//Cравниваем списки, после их упорядочивания.
  }

  @Test //(enabled = false)
  public void testBadContactCreationTests() {
    app.goTo().homePage();
    Contacts before = app.contact().all();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
    ContactData contact = new ContactData()
            .withFirstname("sdfTe'st")
            .withLastname("Test3")
            .withAddress1("Test address")
            .withMobilePhoneNumber("8900045001")
            .withEmail("test1test3test2@mail.ru")
            .withGroup("Тест 2222");
    app.contact().create(contact, true);
    app.goTo().homePage();
    //Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    assertThat(after, equalTo(before));//Cравниваем списки, после их упорядочивания.
  }

}
