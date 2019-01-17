package com.flashdin.fastandroidnetworkingnew.model.dao;

import java.util.List;

public interface BaseDAO<T> {

    List<T> views(T params);
    String insert(T params);
    String update(T params);
    String delete(T params);

}
