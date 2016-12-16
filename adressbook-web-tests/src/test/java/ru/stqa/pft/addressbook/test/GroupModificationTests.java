package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()){ //Создана проверка предусловия того, что редактируемая группа существует.
      app.getGroupHelper().createGroup(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    app.getGroupHelper().selectGroup(before.size() - 1);//В параметре указывается индекс элемента. "before - 1", это последний элемент в списке
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("Test1", "Test2", "dBd36b"));
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    Assert.assertEquals(after.size(), before.size());//Тут реализована проверка количества групп (размер списка групп)
    app.getSessionHelper().logoutProgram();
  }
}
