package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import io.qameta.allure.Allure;
import redmine.Manager.Context;
import redmine.Manager.Manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Hooks {
    @After
    public void afterAll(Scenario scenario) {
        Context.saveStashToAllure();
        Context.clearStash();
        Manager.driverQuit();
    }
    @After
    public void addScreenshotOnFailed(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = Manager.takeScreenshot();

                Allure.addAttachment("Failed step", new FileInputStream(String.valueOf(screenshot)));
            } catch (FileNotFoundException ignored) {}
        }
    }

}
