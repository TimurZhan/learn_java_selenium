package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletionTest() {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()){//Создана проверка предусловия того, что удаляемая группа существует.
      app.getGroupHelper().createGroup(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    app.getGroupHelper().selectGroup(before.size() - 1);//В параметре указывается индекс элемента. "before - 1", это последний элемент в списке
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    Assert.assertEquals(after.size(), before.size() - 1);//Тут реализована проверка количества элементов (групп), до и после. Необходимо, чтобы значение before было меньше значения after.
    app.getSessionHelper().logoutProgram();
  }

}
