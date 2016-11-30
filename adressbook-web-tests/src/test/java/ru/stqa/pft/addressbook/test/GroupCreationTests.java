package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

    @Test
    public void testGroupCreation() {
        //Тут каждый шаг теста выделен в отдельный вспомогательный метод
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().initGroupCreation();
        app.getGroupHelper().fillGroupForm(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd")); //Данный метод сделан универсальным
        app.getGroupHelper().submitGroupCreation();
        app.getGroupHelper().returnToGroupPage();
        app.logoutProgram();
    }

}
