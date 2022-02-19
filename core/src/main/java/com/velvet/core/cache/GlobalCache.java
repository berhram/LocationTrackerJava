package com.velvet.core.cache;

import java.util.List;

public interface GlobalCache<T> {
    void addItems(List<T> inputItems);

    void addItem(T inputItem);

    List<T> getItems();

}
