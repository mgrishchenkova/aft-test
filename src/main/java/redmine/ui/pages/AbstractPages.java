package redmine.ui.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;
import redmine.Manager.Manager;

/**
 * Класс для получения страницы с инициализированными элементами
 */
public class AbstractPages {
    @SneakyThrows
    public <T extends AbstractPages> T getPages(Class<T> clazz){
        T page=clazz.newInstance();
        PageFactory.initElements(Manager.driver(),page);
        return page;
    }
}
