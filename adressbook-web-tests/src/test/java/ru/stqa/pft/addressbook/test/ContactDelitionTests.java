package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDelitionTests extends TestBase {

  @BeforeMethod//Проверка предусловия
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().list().size() == 0){//Создана проверка предусловия того, что удаляемая группа существует.
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
    List<ContactData> before = app.contact().list();//Тут происходит подсчет количества контактов(элементов в списке) ДО удаления контакта.
    int index = before.size() - 1;
    app.contact().delete(index);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ удаления контакта.
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(index);
    /**
     * Тут задействовано лямбда-выражение(анонимная функция), которая помещена в переменную " Comparator<? super GroupData> byId".
     * В данной функции, на вход принимаются 2 объекта, типа ContactData (g1, g2). Затем, при помощи "Integer.compare" они сравниваются, путем сравнения их ID.
     */
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);//Сортируем список "ДО" по ID объектов, находящихся в нем.
    after.sort(byId);//Сортируем список "ПОСЛЕ" по ID объектов, находящихся в нем.
    Assert.assertEquals(after, before);
    app.getSessionHelper().logoutProgram();
  }


}