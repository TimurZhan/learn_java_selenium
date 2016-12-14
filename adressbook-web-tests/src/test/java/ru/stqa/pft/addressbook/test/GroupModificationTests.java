package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupPage();
    int before = app.getGroupHelper().getGroupCount();//Тут происходит подсчет количества групп ДО создания группы.
    if (! app.getGroupHelper().isThereAGroup()){ //Создана проверка предусловия того, что редактируемая группа существует.
      app.getGroupHelper().createGroup(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
    }
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("Test1", "Test2", "dBd36b"));
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    int after = app.getGroupHelper().getGroupCount();//Тут происходит подсчет количества групп ПОСЛЕ создания группы.\
    Assert.assertEquals(after, before);//Тут реализована проверка количества групп
    app.getSessionHelper().logoutProgram();
  }
}
