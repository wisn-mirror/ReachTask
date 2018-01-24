package com.wisn.mainmodule.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.ContactPresenter;
import com.wisn.mainmodule.view.ContactView;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 20:42
 */


public class ContactFragament extends BaseLazyFragment implements ContactView{
    private RecyclerView contact_list;
    private SwipeRefreshLayout contact_list_refresh;
    private ContactPresenter contactPresenter;
    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(view);
        contactPresenter=new ContactPresenter(this);
        return view;
    }

    private void initView(View view) {
        contact_list_refresh = view.findViewById(R.id.contact_list_refresh);
        contact_list = view.findViewById(R.id.contact_list);
    }

    @Override
    public void firstVisible() {
        super.firstVisible();
        contactPresenter.refreshContact();
    }

    @Override
    public void updateContactList(List<User> userlist) {

    }

    @Override
    public void startRefresh() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void refreshError(String msg) {

    }

    @Override
    public void setUserData(List<User> userData) {

    }
}
