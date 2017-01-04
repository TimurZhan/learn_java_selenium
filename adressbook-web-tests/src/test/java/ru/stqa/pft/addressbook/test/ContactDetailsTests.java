package ru.stqa.pft.addressbook.test;

import org.openqa.selenium.By;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailsTests extends TestBase {

  @Test(enabled = false)
  public void testContactDetails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditedForm = app.contact().InfoFromEditFrom(contact);
    String mergedContactInfoFromEditedForm = mergeData(contactInfoFromEditedForm);
    app.contact().viewById(contact.getId());
    //String allText = cleaned(app.contact().infoDetails());
   // assertThat(allText, equalTo(mergedContactInfoFromEditedForm));
  }

  private String mergeData(ContactData contact) {
    return Arrays.asList(
            contact.getFirstname(),
            contact.getLastname(),
            contact.getAddress1(),
            contact.getHomePhoneNumber(),
            contact.getWorkPhoneNumber(),
            contact.getMobilePhoneNumber(),
            contact.getEmail(),
            contact.getEmail2(),
            contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactDetailsTests::cleaned)
            .collect(Collectors.joining(""));
  }

  public static String cleaned(String cleaned) {
    return cleaned.replaceAll("\\s", "").replaceAll("[-()HMW:]", "").replaceAll("\n", "");
  }

}