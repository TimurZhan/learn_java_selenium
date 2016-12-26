package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod//Метод осуществляющий проверку предусловия.
  public void ensurePrecondition(){
    app.goTo().groupPage();
    if (app.group().all().size() == 0){ //Создана проверка предусловия того, что редактируемая группа существует.
      app.group().create(new GroupData().withName("Test1"));
    }
  }

  @Test//Аннотация к тесту
  public void testGroupModification() {
    Groups before = app.group().all();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("Test1").withFooter("Test2").withHeader("dBd36b");//При модификации группы указываем все новое, кроме id. Его мы берем от домодифицированной группы.
    app.group().modify(group);//Шаги по модификации группы вынесены в отдельный метод
    assertThat(app.group().count(), equalTo(before.size()));//Тут реализована проверка количества элементов (размер списка групп), до и после. Необходимо, чтобы значения совпадали.
    Groups after = app.group().all();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group))); //Сравниваем коллекцию объектов типа GroupData как множество, ДО и ПОСЛЕ модификации группы.
    //app.getSessionHelper().logoutProgram();
  }
}
