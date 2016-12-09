package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void ContactCreationTests() {
        app.getContactHelper().initContact();
        app.getContactHelper().fillContactForm(new ContactData("Test1", "Test2", "Test3", "Test Company", "Test address",
                        "89073451234", "test1test3test2@mail.ru", "Test address 2", "Test1"), true); //true означает, что поле для выбора групп, тут должно быть
        app.getContactHelper().submitContact();
        app.getNavigationHelper().returnToHomePage();
        app.getSessionHelper().logoutProgram();
    }

}
