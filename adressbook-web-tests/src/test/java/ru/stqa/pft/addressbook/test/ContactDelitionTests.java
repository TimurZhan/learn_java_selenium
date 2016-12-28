package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDelitionTests extends TestBase {
  @Test
  public void testContactDelition() {
    if (! app.getContactHelper().isThereAContact()){//Создана проверка предусловия того, что удаляемая группа существует.
      app.getContactHelper().createContact(new ContactData("Test1", "Test2", "Test3", "Test Company", "Test address",
              "89073451234", "test1test3test2@mail.ru", "Test address 2", "Тест 2222"), true);
    }
    app.goTo().returnToHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();//Тут происходит подсчет количества контактов(элементов в списке) ДО удаления контакта.
    app.getContactHelper().selectToDeleteContact(before.size() - 1);
    app.getContactHelper().deleteContact();
    app.goTo().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ удаления контакта.
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(before.size() - 1);
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