package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;

public class ContactDelitionTests extends TestBase {
    @Test
    public void testContactModification() {
      app.getContactHelper().selectToDeleteContact();
      app.getContactHelper().deleteContact();
      app.getSessionHelper().logoutProgram();
    }
}
