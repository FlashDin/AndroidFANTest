package com.flashdin.fastandroidnetworkingnew.presenter;

import com.flashdin.fastandroidnetworkingnew.model.entity.TUsers;

public interface BaseView extends BasePresenter {

    void onShowFragment(TUsers data);

}