package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void contactCreationTests() {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
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
    List<ContactData> after = app.contact().list();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    Assert.assertEquals(after.size(), before.size() + 1);//Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    before.add(contact);

    /**
     * Тут задействовано лямбда-выражение(анонимная функция), которая помещена в переменную " Comparator<? super ContactData> byId".
     * В данной функции, на вход принимаются 2 объекта, типа ContactData (g1, g2). Затем, при помощи "Integer.compare" они сравниваются, путем сравнения их ID.
     */
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);//Сортируем списко "ДО" по ID объектов, находящихся в нем.
    after.sort(byId);//Сортируем списко "ПОСЛЕ" по ID объектов, находящихся в нем.
    Assert.assertEquals(before, after);//Cравниваем списки, после их упорядочивания.
    //app.getSessionHelper().logoutProgram();
  }



}
