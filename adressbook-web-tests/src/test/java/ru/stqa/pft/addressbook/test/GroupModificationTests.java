package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

  @BeforeMethod//Метод осуществляющий проверку предусловия.
  public void ensurePrecondition(){
    app.goTo().groupPage();
    if (app.group().list().size() == 0){ //Создана проверка предусловия того, что редактируемая группа существует.
      app.group().create(new GroupData().withName("Test1"));
    }
  }

  @Test//Аннотация к тесту
  public void testGroupModification() {
    List<GroupData> before = app.group().list();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    int index = before.size() - 1;
    GroupData group = new GroupData()
            .withId(before.get(index).getId()).withName("Test1").withFooter("Test2").withHeader("dBd36b");//При модификации группы указываем все новое, кроме id. Его мы берем от домодифицированной группы.
    app.group().modify(index, group);//Шаги по модификации группы вынесены в отдельный метод
    List<GroupData> after = app.group().list();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    Assert.assertEquals(after.size(), before.size());//Тут реализована проверка количества групп (размер списка групп)
    before.remove(index);//Удаляем модифицированную группу из списка before. Нужно для дальнейшего сравнения.
    before.add(group);//Добавляем получившуюся, после модификации, группу в спискок before. Нужно для дальнейшего сравнения.

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
