package com.flashdin.fastandroidnetworkingnew.presenter;

import com.flashdin.fastandroidnetworkingnew.model.dao.TUserDAO;

public interface TUserPresenter<T extends BasePresenter> extends TUserDAO {

    void onAttach(T view);

    void onDetach();

    void showFragment();
}
