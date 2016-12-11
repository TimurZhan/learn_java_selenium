package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

    @Test
    public void testGroupCreation() {
        //Тут каждый шаг теста выделен в отдельный вспомогательный метод
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().createGroup(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
        app.getSessionHelper().logoutProgram();
    }

}
