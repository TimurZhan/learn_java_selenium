package ru.stqa.pft.addressbook.test;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase {

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
  public void testContactPhones(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditFrom = app.contact().InfoFromEditFrom(contact);

  }
}
