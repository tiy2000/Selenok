package ru.ozon;

import core.BasePage;
import core.annotations.BaseUrl;

public class OzonBasePage<T extends OzonBasePage<T>> extends BasePage<T> {

    @BaseUrl
    public static final String BASE_URL = "http://ozon.ru";

}
