package redmine.ui.page;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;
import redmine.Manager.Manager;

import java.io.ByteArrayInputStream;

public class Page {
    /**
     * Получение экземпляра PageObject с инициализированными элементами
     *
     * @param clazz - класс, представляющий нужную страницу
     * @param <T>   - имя класса страницы
     * @return экземпляр класса PageObject
     */

    @SneakyThrows
    public static <T extends redmine.ui.page.AbstractPage> T getPage(Class<T> clazz) {
        T page = clazz.newInstance();
        PageFactory.initElements(Manager.driver(), page);
        Allure.addAttachment("screenshot", new ByteArrayInputStream(Manager.takeScreenshot()));
        return page;

    }
}
