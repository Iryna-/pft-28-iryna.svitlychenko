package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions (){
    app.goTo().homePage();
    if (app.contact().list().size()==0){
      app.contact().create(new ContactData()
              .withName("Name")
              .withMiddleName("Middle")
              .withSurname("Surname")
              .withEmail("name.surname@gmail.com")
              .withPhone("07511111111")
              .withAddress("Address line 1")
              .withGroup("group1"));
    }
  }

  @Test
  public void testContactDeletion(){

    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.contact().delete(index);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before, after);

  }


}
