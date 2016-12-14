package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletionTest() {
    app.getNavigationHelper().gotoGroupPage();
    int before = app.getGroupHelper().getGroupCount();//Тут происходит подсчет количества групп ДО создания группы.
    if (! app.getGroupHelper().isThereAGroup()){//Создана проверка предусловия того, что удаляемая группа существует.
      app.getGroupHelper().createGroup(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
    }
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    int after = app.getGroupHelper().getGroupCount();//Тут происходит подсчет количества групп ПОСЛЕ создания группы.\
    Assert.assertEquals(after, before - 1);//Тут реализована проверка количества групп
    app.getSessionHelper().logoutProgram();
  }

}
