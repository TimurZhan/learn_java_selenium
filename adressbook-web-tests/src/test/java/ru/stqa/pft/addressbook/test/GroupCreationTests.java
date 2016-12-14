package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase { //Создан базовый класс "TestBase", в который перенесены вспомогательные методы

    @Test
    public void testGroupCreation() {
        //Тут каждый шаг теста выделен в отдельный вспомогательный метод
        app.getNavigationHelper().gotoGroupPage();
        int before = app.getGroupHelper().getGroupCount();//Тут происходит подсчет количества групп ДО создания группы.
        app.getGroupHelper().createGroup(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
        int after = app.getGroupHelper().getGroupCount();//Тут происходит подсчет количества групп ПОСЛЕ создания группы.\
        Assert.assertEquals(after, before + 1);//Тут реализована проверка количества групп
        app.getSessionHelper().logoutProgram();
    }

}
