package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Класс для получения страницы с инициализированными элементами
 */
public class AbstractPage {

    @FindBy(xpath = "//div")
    private WebElement field;
}
