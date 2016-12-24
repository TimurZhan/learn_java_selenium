package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().contactPage();
    if (app.contact().list().size() == 0){//Создана проверка предусловия того, что удаляемая группа существует.
      app.contact().create(new ContactData().withFirstname("Test1").withMiddlename("Fddfgdg").withLastname("ssdfsdf"), true);
    }
  }

  @Test//(enabled = false)
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstname("Test1").withMiddlename("Fddfgdg").withLastname("ssdfsdf");

    app.contact().modify(index, contact);
    app.goTo().contactPage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());//Тут реализована проверка количества контактов (размер списка контактов)
    before.remove(index);//Удаляем модифицированный контакт из списка before. Нужно для дальнейшего сравнения.
    before.add(contact);//Добавляем получившийся, после модификации, контакт в спискок before. Нужно для дальнейшего сравнения.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция), которая помещена в переменную " Comparator<? super GroupData> byId".
     * В данной функции, на вход принимаются 2 объекта, типа ContactData (g1, g2). Затем, при помощи "Integer.compare" они сравниваются, путем сравнения их ID.
     */
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);//Сортируем список "ДО" по ID объектов, находящихся в нем.
    after.sort(byId);//Сортируем список "ПОСЛЕ" по ID объектов, находящихся в нем.
    Assert.assertEquals(before, after);//Cравниваем списки после их упорядочивания.
    //app.getSessionHelper().logoutProgram();
  }
}