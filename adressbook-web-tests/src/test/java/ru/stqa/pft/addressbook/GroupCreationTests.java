package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

    @Test
    public void testGroupCreation() {
        //Тут каждый шаг теста выделен в отдельный вспомогательный метод
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd")); //Данный метод сделан универсальным
        submitGroupCreation();
        returnToGroupPage();
        logoutProgram();
    }

}
