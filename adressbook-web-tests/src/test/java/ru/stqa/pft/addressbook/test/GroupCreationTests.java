package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

//Статический импорт
import static org.hamcrest.CoreMatchers.equalTo;//Импорт отдельного метода (equalTo) из класса CoreMatchers билбиотеки hamcrest.
import static org.hamcrest.MatcherAssert.assertThat;//Импорт отдельного метода (assertThat) из класса MatcherAssert билбиотеки hamcrest.

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

  @Test
  public void testGroupCreation() {
    //Тут каждый шаг теста выделен в отдельный вспомогательный метод
    app.goTo().groupPage();
    Groups before = app.group().all();//Тут происходит подсчет количества групп(элементов в множестве) ДО создания группы.
    GroupData group = new GroupData().withName("Тест 2222");
    app.group().create(group);
    Groups after = app.group().all();//Тут происходит подсчет количества групп (элементов в множестве) ПОСЛЕ создания группы.
    assertThat(after.size(), equalTo(before.size() + 1));//Тут реализована проверка количества элементов (размер списка групп), до и после. Необходимо, чтобы значения совпадали.

    /**MatcherAssert, это проверка используемая из бибилиотеки hamcrest.
     * Где метод assertThat проверяет множества after и before между собой. В нем присутствует класс CoreMatchers, в котором,
     * в свою очередь, присутствует метод equalTo(), вот он и используется для проверки.
     * В строке "group.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt())" присваиваем новой группе правильный ID.
     * Где выражение after.stream() превращает коллекцию ID в поток, из множества after.
     * Метод mapToInt превращает поток объектов GroupData в поток ID (т.е. чисел).
     * В которм работает анонимная функция (g)-> g.getId(), где (g) объект типа GroupData, а g.getId() это Id взятой группы.
     * Метод max() сравнивает ID групп из множества after, а затем берет самый максимальный ID из них. Метод getAsInt() преобразует результат в целое число.
     */
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));

    //app.getSessionHelper().logoutProgram();
  }

}
