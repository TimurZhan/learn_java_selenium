package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void contactCreationTests() {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
    ContactData contact = new ContactData()
            .withFirstname("sdfTest")
            .withMiddlename("Tsdfsdfesу2")
            .withLastname("Test3")
            .withCompanyName("Test Company")
            .withAddress1("Test address")
            .withMobilePhoneNumber("8900045001")
            .withEmail("test1test3test2@mail.ru")
            .withAddress2("Test address 2")
            .withGroup("Тест 2222");
    app.contact().initContact();
    app.contact().fillContactForm(contact, true);
    app.contact().submitContact();
    app.goTo().homePage();
    Set<ContactData> after = app.contact().all();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    Assert.assertEquals(after.size(), before.size() + 1);//Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);//Cравниваем списки, после их упорядочивания.
    //app.getSessionHelper().logoutProgram();
  }



}
