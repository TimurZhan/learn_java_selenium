package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class ContactEmailAndAddressTests extends TestBase {

  @BeforeMethod//Проверка предусловия
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0){//Создана проверка предусловия того, что удаляемая группа существует.
      app.contact().create(new ContactData()
              .withFirstname("sdfTest")
              .withLastname("Test3")
              .withAddress1("dfdfsdfdfstree.er 32/1")
              .withEmail("eeeeeee@mail.ru")
              .withEmail2("ddd45dddd@mail.ru")
              .withEmail3("hhhhhhh@mail.ru"), true);
    }
  }

  @Test
  public void testEmailAndAddressPhones(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().InfoFromEditFrom(contact);
    assertThat(contact.getAddress1(), equalTo(mergeAddress(contactInfoFromEditForm)));//Сверяем адрес с гланвной страницы и страницы редактирования контакта.
    assertThat(contact.getAllEmail(), equalTo(mergeEmail(contactInfoFromEditForm)));//Сверяем email с гланвной страницы и страницы редактирования контакта.
  }

  //Реализован метод обратной проверки. Т.е., при проверке телефонов строки сначала склеиваются, а потом проверяются.
  private String mergeAddress(ContactData contact) {
    return Arrays.asList(contact.getAddress1())
            .stream().filter((s) -> ! s.equals("")).map(ContactEmailAndAddressTests::cleaned).collect(Collectors.joining("\n"));
  }

  //Реализован метод обратной проверки. Т.е., при проверке телефонов строки сначала склеиваются, а потом проверяются.
  private String mergeEmail(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
  }

  //Реализован метод для замены символов (для корректного сравнения адреса).
  //Для этого задействовано РЕГУЛЯРНОЕ ВЫРАЖЕНИЕ, которое убирает пробелы ("\\s", "").
  //А вместо этого строка склеивается в единое целое.
  public static String cleaned(String address){
  return address.replaceAll("\\s", "");
  }

}
