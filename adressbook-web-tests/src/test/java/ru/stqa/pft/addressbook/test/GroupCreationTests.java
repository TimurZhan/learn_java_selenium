package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

  @Test
  public void testGroupCreation() {
    //Тут каждый шаг теста выделен в отдельный вспомогательный метод
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    GroupData group = new GroupData("Test1", "drgdgdg", "dgfdgdgdgd");
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    Assert.assertEquals(after.size(), before.size() + 1);//Тут реализована проверка количества элементов (размер списка групп), до и после. Необходимо, чтобы значения совпадали.

    /**
     * Тут задействовано лямбда-выражение(анонимная функция) "group.setId". Метод stream превращает список в поток.
     * Функция max (сравниватель) проходит по этому потоку и находит элемент с максимальным ID, в котором
     * сравниваются объекты типа GroupData (o1, o2), путем сравнения их ID. Затем, при помощи ".get()" берется найденный
     * максимальный ID и передается далее, при помощи ".getId()".
     */
    group.setId(after.stream().max(((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))).get().getId());
    before.add(group);//Добавляем в старый список новосозданную группу.
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));//Тут преобразовываем списки в неупорадоченные коллекции(множества) и сравниваем их.
    app.getSessionHelper().logoutProgram();
  }

}
