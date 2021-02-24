package ru.aristovo.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.aristovo.framework.managers.*;

import static ru.aristovo.framework.managers.DriverManager.getDriver;
import static ru.aristovo.framework.managers.InitManager.props;
import static ru.aristovo.framework.utils.PropConst.APP_URL;

public class BaseTests {
    /**
     * Менеджер страничек
     * @see PageManager#getPageManager()
     */
    protected PageManager app = PageManager.getPageManager();

    @BeforeClass
    public static void beforeClass() {
        InitManager.initFramework();
    }

    @Before
    public void before() {
        getDriver().get(props.getProperty(APP_URL));
    }

    @AfterClass
    public static void afterClass() {
        InitManager.quitFramework();
    }
}
