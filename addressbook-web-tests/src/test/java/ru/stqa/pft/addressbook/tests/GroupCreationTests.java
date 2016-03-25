package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups () throws IOException {

    //Filling out a list of arrays.
    // Each array contains a set of data for one launch of the test method

    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.scv"));
    String line = reader.readLine();
    while (line !=null){
      String[] split = line.split(";");
      list.add(new Object[] {new GroupData()
                  .withName(split[0])
                  .withHeader(split[1])
                  .withFooter(split[2])});
      line = reader.readLine();
    }
    //Test framework uses the iterator to extract each array and use it for the test
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupsPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(),equalTo((before.size() + 1)));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }
}
