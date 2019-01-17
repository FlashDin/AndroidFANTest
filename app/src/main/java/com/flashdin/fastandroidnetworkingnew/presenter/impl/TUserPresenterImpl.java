package com.flashdin.fastandroidnetworkingnew.presenter.impl;

import com.flashdin.fastandroidnetworkingnew.model.dao.TUserDAO;
import com.flashdin.fastandroidnetworkingnew.model.dao.impl.TUserDAOImpl;
import com.flashdin.fastandroidnetworkingnew.model.entity.TUsers;
import com.flashdin.fastandroidnetworkingnew.presenter.BaseView;
import com.flashdin.fastandroidnetworkingnew.presenter.TUserPresenter;

import java.util.List;

public class TUserPresenterImpl implements TUserPresenter<BaseView> {

    private TUserDAO tUserDAO = new TUserDAOImpl();
    private BaseView mBaseView;

    @Override
    public void onAttach(BaseView baseView) {
        mBaseView = baseView;
    }

    @Override
    public void onDetach() {
        mBaseView = null;
    }

    @Override
    public void showFragment() {
        // Set Data
        TUsers data = new TUsers();
        data.setUserName("username");
        // Show Fragment with Data
        mBaseView.onShowFragment(data);
    }

    @Override
    public List<TUsers> views(TUsers params) {
        return tUserDAO.views(params);
    }

    @Override
    public String insert(TUsers params) {
        return tUserDAO.insert(params);
    }

    @Override
    public String update(TUsers params) {
        return tUserDAO.update(params);
    }

    @Override
    public String delete(TUsers params) {
        return tUserDAO.delete(params);
    }
}
