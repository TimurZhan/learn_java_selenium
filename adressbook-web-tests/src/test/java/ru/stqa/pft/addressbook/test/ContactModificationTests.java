package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()){//Создана проверка предусловия того, что удаляемая группа существует.
      app.getContactHelper().createContact(new ContactData("Test1", "Test2", "Test3", "Test Company", "Test address",
              "89073451234", "test1test3test2@mail.ru", "Test address 2", "Test1"), true);
    }
    app.goTo().returnToHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().initContactModification(before.size() - 1);
    ContactData contact = new ContactData(
            before.get(before.size() - 1).getId(),
            "8",
            "8",
            "Test3",
            "Test Company",
            "Test address",
            "8",
            "test1test3test2@mail.ru",
            "Test address 2",
            "Тест 2222");
    app.getContactHelper().fillContactForm(contact, false); //false означает, что поле для выбора групп, тут НЕ должно быть
    app.getContactHelper().submitContactModification();
    app.goTo().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());//Тут реализована проверка количества контактов (размер списка контактов)
    before.remove(before.size() - 1);//Удаляем модифицированный контакт из списка before. Нужно для дальнейшего сравнения.
    before.add(contact);//Добавляем получившийся, после модификации, контакт в спискок before. Нужно для дальнейшего сравнения.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция), которая помещена в переменную " Comparator<? super GroupData> byId".
     * В данной функции, на вход принимаются 2 объекта, типа ContactData (g1, g2). Затем, при помощи "Integer.compare" они сравниваются, путем сравнения их ID.
     */
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);//Сортируем список "ДО" по ID объектов, находящихся в нем.
    after.sort(byId);//Сортируем список "ПОСЛЕ" по ID объектов, находящихся в нем.
    Assert.assertEquals(after.size(), before.size());///Cравниваем списки после их упорядочивания.
    app.getSessionHelper().logoutProgram();
  }
}