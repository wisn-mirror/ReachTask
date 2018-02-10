package com.wisn.mainmodule.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.ContactPresenter;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.ContactView;
import com.wisn.mainmodule.view.activity.InfoActivity;
import com.wisn.mainmodule.view.viewholder.ContactsItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 20:42
 */


public class ContactFragament extends BaseLazyFragment implements ContactView {
    private RecyclerView contact_list;
    private SwipeRefreshLayout contact_list_refresh;
    private ContactPresenter contactPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private List<User> users = new ArrayList<>();

    @Override
    public String getTAG() {
        return "ContactFragament";
    }

    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .fallback(R.drawable.photo);

        contact_list_refresh = (SwipeRefreshLayout) view.findViewById(R.id.contact_list_refresh);
        contact_list = (RecyclerView) view.findViewById(R.id.contact_list);

        contact_list_refresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        contact_list_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contactPresenter.refreshContact();
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        contact_list.setLayoutManager(mLinearLayoutManager);
        contact_list.setItemAnimator(new DefaultItemAnimator());
        contact_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mLastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 5 > mLinearLayoutManager.getItemCount()) {
                    contactPresenter.addMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_contact, parent, false);
                ContactsItemHolder contactsItemHolder = new ContactsItemHolder(inflate);
                return contactsItemHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                if (holder instanceof ContactsItemHolder) {
                    final User user = users.get(position);
                    ContactsItemHolder contactsItemHolder = (ContactsItemHolder) holder;
                    contactsItemHolder.contact_name.setText(user.getNameid());
                    contactsItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), InfoActivity.class);
                            intent.putExtra(Contants.user_flag, user);
                            startActivity(intent);
                        }
                    });
                    Glide.with(ContactFragament.this)
                            .load(Contants.baseImage + user.getIconurl())
                            .apply(options).into(contactsItemHolder.contact_imageView);

                }
            }

            @Override
            public int getItemCount() {
                return users.size();
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }
        };
        contact_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        dataChange();
    }

    private void dataChange() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void firstVisible() {
        super.firstVisible();
        contactPresenter = new ContactPresenter(this);
        contactPresenter.getUsers();
    }

    @Override
    public void setUserData(List<User> userData) {
        users = userData;
        dataChange();
    }


    @Override
    public void startRefresh() {

    }

    @Override
    public void finishRefresh() {
        contact_list_refresh.setRefreshing(false);
    }

    @Override
    public void refreshError(String msg) {

    }

}
