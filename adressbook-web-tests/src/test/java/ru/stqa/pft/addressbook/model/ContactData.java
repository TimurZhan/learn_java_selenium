package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id;
  private final String firstname;
  private final String middlename;
  private final String lastname;
  private final String companyName;
  private final String address1;
  private final String mobilePhoneNumber;
  private final String email;
  private final String address2;
  private static String group;

  public ContactData(String firstname, String middlename, String lastname, String companyName,
                     String address1, String mobilePhoneNumber, String email, String address2, String group) {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.companyName = companyName;
    this.address1 = address1;
    this.mobilePhoneNumber = mobilePhoneNumber;
    this.email = email;
    this.address2 = address2;
    this.group = group;
  }

  public ContactData(int id, String firstname, String middlename, String lastname, String companyName,
                     String address1, String mobilePhoneNumber, String email, String address2, String group) {
    this.id = id;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.companyName = companyName;
    this.address1 = address1;
    this.mobilePhoneNumber = mobilePhoneNumber;
    this.email = email;
    this.address2 = address2;
    this.group = group;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getAddress1() {
    return address1;
  }

  public String getMobilePhoneNumber() {
    return mobilePhoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress2() {
    return address2;
  }

  public static String getGroup() {
    return group;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }
}
