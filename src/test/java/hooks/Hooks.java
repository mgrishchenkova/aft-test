package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import redmine.Manager.Context;
import redmine.Manager.Manager;

public class Hooks {
    @After
    public void afterAll(Scenario scenario) {
        Context.saveStashToAllure();
        Context.clearStash();
        Manager.driverQuit();
    }

}
