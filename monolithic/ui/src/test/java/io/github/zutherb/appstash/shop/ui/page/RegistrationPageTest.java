package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.ui.AbstractWicketTest;
import io.github.zutherb.appstash.shop.ui.page.user.RegistrationPage;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;
import org.junit.Ignore;

/**
 * @author zutherb
 */
public class RegistrationPageTest extends AbstractWicketTest {

    @Ignore("not ready yet") @Test
    public void testRender() {
        wicketTester.startPage(RegistrationPage.class);
        wicketTester.assertRenderedPage(RegistrationPage.class);
    }

    @Ignore("not ready yet") @Test
    public void testSaveValid() {
        wicketTester.startPage(RegistrationPage.class);
        FormTester formTester = wicketTester.newFormTester("registration");
        formTester.setValue("firstname", "Max");
        formTester.setValue("lastname", "Müller");
        formTester.setValue("username", "Max.M");
        formTester.setValue("email", "max.m@mail.com");
        formTester.setValue("password", "password123");
        formTester.setValue("repeatPassword", "password123");
        formTester.setValue("city", "München");
        formTester.setValue("street", "Lindwurmstr. 33");
        formTester.setValue("zip", "81369");

        wicketTester.hasNoInfoMessage();
    }

    @Ignore("not ready yet") @Test
    public void testSaveInvalid() {
        wicketTester.startPage(RegistrationPage.class);
        FormTester formTester = wicketTester.newFormTester("registration");
        formTester.setValue("firstname", "Max");
        formTester.setValue("lastname", "Müller");
        formTester.setValue("username", "Max.M");
        formTester.setValue("email", "max.m@mail.com");
        formTester.setValue("password", "passsword123");
        formTester.setValue("repeatPassword", "password123");
        formTester.setValue("city", "München");
        formTester.setValue("street", "Lindwurmstr. 33");
        formTester.setValue("zip", "81369");

        formTester.submit();

        //verify(userService, times(0));
        wicketTester.assertErrorMessages("The entered Passwords do not match");
    }


    @Override
    public void initMockData() {

    }
}
