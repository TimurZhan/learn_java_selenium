package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod//Проверка предусловия
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0){//Создана проверка предусловия того, что удаляемая группа существует.
      app.contact().create(new ContactData()
              .withFirstname("sdfTest")
              .withLastname("Test3")
              .withHomePhoneNumber("90534535")
              .withWorkPhoneNumber("345345")
              .withMobilePhoneNumber("9899999"), true);
    }
  }

  @Test
  public void testContactPhones(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().InfoFromEditFrom(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));//Сверяем телефоны с гланвной страницы и страницы редактирования контакта.
  }

  //Реализован метод обратной проверки. Т.е., при проверке телефонов строки сначала склеиваются, а потом проверяются.
  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhoneNumber(), contact.getMobilePhoneNumber(), contact.getWorkPhoneNumber())
            .stream().filter((s) -> ! s.equals("")).map(ContactPhoneTests::cleaned).collect(Collectors.joining("\n"));
  }

  //Реализован метод для замены некоторых символов (для корректного сравнения номеров телефонов).
  //Для этого задействованны РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ, которые убирают перечисленные в шаблонах символы ("[-()]", "") и пробелы ("\\s", "").
  //А вместо них строка склеивается в единое целое. Пример, есть телефон: 8 (905) 456-74-56, после проверки РВ телефон будет: 89054567456
  public static String cleaned(String phone){
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
