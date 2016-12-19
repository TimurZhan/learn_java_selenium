package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void ContactCreationTests() {
    List<ContactData> before = app.getContactHelper().getContactList();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
    app.getContactHelper().initContact();
    ContactData contact = new ContactData("sdfTest", "Tsdfsdfesу2", "Test3", "Test Company", "Test address",
            "8900045001", "test1test3test2@mail.ru", "Test address 2", "Test1");
    app.getContactHelper().fillContactForm(contact, true);//true означает, что поле для выбора контактов, тут должно быть
    app.getContactHelper().submitContact();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    Assert.assertEquals(after.size(), before.size() + 1);//Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция) "contact.setId". Метод stream превращает список в поток.
     * Функция max (сравниватель) проходит по этому потоку и находит элемент с максимальным ID, в котором
     * сравниваются объекты типа ContactData (o1, o2), путем сравнения их ID. Затем, при помощи ".get()" берется найденный
     * максимальный ID и передается далее, при помощи ".getId()".
     */
    contact.setId(after.stream().max(((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))).get().getId());
    before.add(contact);//Добавляем в старый список новосозданный контакт.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция), которая помещена в переменную " Comparator<? super ContactData> byId".
     * В данной функции, на вход принимаются 2 объекта, типа ContactData (g1, g2). Затем, при помощи "Integer.compare" они сравниваются, путем сравнения их ID.
     */
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);//Сортируем списко "ДО" по ID объектов, находящихся в нем.
    after.sort(byId);//Сортируем списко "ПОСЛЕ" по ID объектов, находящихся в нем.
    Assert.assertEquals(before, after);//Cравниваем списки, после их упорядочивания.
    app.getSessionHelper().logoutProgram();
  }

}
