package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;

@XStreamAlias("contact") //Тут указывает то, как будет называться каждый объект в файле contacts.xml
@Entity //Эта аннотация объявляет класс ContactData привязанным к БД
@Table(name = "addressbook") //Тут подсказываем программе, что класс GroupData равен таблице под названием , в БД
public class ContactData {

  @XStreamOmitField //В данной аннотации указывем то, что id не будет отображаться в файле contacts.xml
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "firstname")
  private String firstname;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "middlename")
  private String middlename;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "lastname")
  private String lastname;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "company")
  private String companyName;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "address")
  @Type(type = "text")
  private String address1;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Transient //Пропускает столбец при отработке запроса к БД
  private String allPhones;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "home")
  @Type(type = "text")
  private String homePhoneNumber;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "work")
  @Type(type = "text")
  private String workPhoneNumber;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilePhoneNumber;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Transient //Пропускает столбец при отработке запроса к БД
  private String allEmail;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "email")
  @Type(type = "text")
  private String email;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "email2")
  @Type(type = "text")
  private String email2;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "email3")
  @Type(type = "text")
  private String email3;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "address2")
  @Type(type = "text")
  private String address2;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Transient //Пропускает столбец при отработке запроса к БД
  private String group;

  @Expose//В данной аннотации указывем то, что id не будет отображаться в файле contacts.json, а будет отображаться данная строчка.
  @Column(name = "photo")
  @Type(type = "text")
  private String photo;

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withMiddlename(String middlename) {
    this.middlename = middlename;
    return this;
  }

  public ContactData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  public ContactData withAddress1(String address1) {
    this.address1 = address1;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withHomePhoneNumber(String homePhoneNumber){
    this.homePhoneNumber = homePhoneNumber;
    return this;
  }

  public ContactData withWorkPhoneNumber(String workPhoneNumber){
    this.workPhoneNumber = workPhoneNumber;
    return this;
  }

  public ContactData withMobilePhoneNumber(String mobilePhoneNumber) {
    this.mobilePhoneNumber = mobilePhoneNumber;
    return this;
  }

  public ContactData withAllEmail(String allEmail) {
    this.allEmail = allEmail;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withAddress2(String address2) {
    this.address2 = address2;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }

  public File getPhoto() {
    return new File(photo);
  }


  public int getId() {
    return id;
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

  public String getAllPhones() {
    return allPhones;
  }

  public String getHomePhoneNumber() {
    return homePhoneNumber;
  }

  public String getWorkPhoneNumber() {
    return workPhoneNumber;
  }

  public String getMobilePhoneNumber() {
    return mobilePhoneNumber;
  }

  public String getAllEmail() {
    return allEmail;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
  }

  public String getAddress2() {
    return address2;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", middlename='" + middlename + '\'' +
            ", lastname='" + lastname + '\'' +
            ", companyName='" + companyName + '\'' +
            ", address1='" + address1 + '\'' +
            ", allPhones='" + allPhones + '\'' +
            ", homePhoneNumber='" + homePhoneNumber + '\'' +
            ", workPhoneNumber='" + workPhoneNumber + '\'' +
            ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
            ", allEmail='" + allEmail + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            ", address2='" + address2 + '\'' +
            ", group='" + group + '\'' +
            ", photo=" + photo +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }
}