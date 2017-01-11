package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod//Метод осуществляющий проверку предусловия.
  public void ensurePrecondition(){
    app.goTo().groupPage();
    if (app.db().groups().size() == 0){ //Создана проверка предусловия того, что редактируемая группа существует. Проверка идет через прямоне обращение к БД
      app.group().create(new GroupData().withName("Test1"));
    }
  }

  @Test
  public void testGroupDeletionTest() {
    Groups before = app.db().groups();//Тут происходит подсчет количества групп(элементов в множестве) ДО создания группы.
    /** Тут создаем экземпляр объекта (Группа), которая будет в дальнейшем удалена.
     * before.iterator() - данный метод последовательно перебирает коллекцию объектов в before, как множество.
     * next() - выбирает какой-либо объект и извлекает данный элемент из множества.
     */
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));//Тут реализована проверка количества элементов (размер списка групп), до и после. Необходимо, чтобы значения совпадали.
    Groups after = app.db().groups();//Тут происходит подсчет количества групп (элементов в множестве) ПОСЛЕ создания группы.
    assertThat(after, equalTo(before.without(deletedGroup))); //Проверка того, что группа удалилась.
    //app.getSessionHelper().logoutProgram();
  }

}
