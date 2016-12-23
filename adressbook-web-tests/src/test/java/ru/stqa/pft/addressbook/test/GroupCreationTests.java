package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

  @Test
  public void testGroupCreation() {
    //Тут каждый шаг теста выделен в отдельный вспомогательный метод
    app.goTo().groupPage();
    List<GroupData> before = app.group().list();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    GroupData group = new GroupData().withName("Тест 2222");
    app.group().create(group);
    List<GroupData> after = app.group().list();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    Assert.assertEquals(after.size(), before.size() + 1);//Тут реализована проверка количества элементов (размер списка групп), до и после. Необходимо, чтобы значения совпадали.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция) "group.setId". Метод stream превращает список в поток.
     * Функция max (сравниватель) проходит по этому потоку и находит элемент с максимальным ID, в котором
     * сравниваются объекты типа GroupData (o1, o2), путем сравнения их ID. Затем, при помощи ".get()" берется найденный
     * максимальный ID и передается далее, при помощи ".getId()".
     group.setId(after.stream().max(((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))).get().getId());
     */

    before.add(group);//Добавляем в старый список новосозданную группу.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция), которая помещена в переменную " Comparator<? super GroupData> byId".
     * В данной функции, на вход принимаются 2 объекта, типа GroupData (g1, g2). Затем, при помощи "Integer.compare" они сравниваются, путем сравнения их ID.
     */
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);//Сортируем списко "ДО" по ID объектов, находящихся в нем.
    after.sort(byId);//Сортируем списко "ПОСЛЕ" по ID объектов, находящихся в нем.
    Assert.assertEquals(before, after);//Cравниваем списки после их упорядочивания.
    //app.getSessionHelper().logoutProgram();
  }

}
