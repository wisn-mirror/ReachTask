package com.wisn.mainmodule.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.MessageContactPresenter;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.HomeView;
import com.wisn.mainmodule.view.MessageContactView;
import com.wisn.mainmodule.view.activity.ChatActivity;
import com.wisn.mainmodule.view.viewholder.MessageItemHolder;
import com.wisn.skinlib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 20:42
 */


public class MessageFragament extends BaseLazyFragment implements MessageContactView{
    private RecyclerView contact_list;
    private MessageContactPresenter contactPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private List<Contact> contacts =new ArrayList<>();
    private HomeView homeView;

    @Override
    public String getTAG() {
        return "MessageFragament";
    }

    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactmessage, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.radiobutton_bg_message)
                .error(R.drawable.radiobutton_bg_message)
                .fallback(R.drawable.radiobutton_bg_message);
        contact_list =(RecyclerView) view.findViewById(R.id.contact_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        contact_list.setLayoutManager(mLinearLayoutManager);
        contact_list.setItemAnimator(new DefaultItemAnimator());
        contact_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mLastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE
                        &&mLastVisibleItem+5>mLinearLayoutManager.getItemCount()){

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>(){
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                final View inflate = View.inflate(getActivity(), R.layout.item_contact, null);
                final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_message,parent,false);
                MessageItemHolder messageItemHolder=new MessageItemHolder(inflate);
                return messageItemHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                if(holder instanceof  MessageItemHolder){
                    MessageItemHolder messageItemHolder= (MessageItemHolder) holder;
                    final Contact contact = contacts.get(position);
                    messageItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            contactPresenter.toSendMessage(contact);
                        }
                    });
                    if(contact.getUnReadMessageNumber()==0){
                        messageItemHolder.contact_imageView.clearTip();
                    }else if(contact.getUnReadMessageNumber()>0){
                        messageItemHolder.contact_imageView.setTipText(String.valueOf(contact.getUnReadMessageNumber()));
                    }else{
                        messageItemHolder.contact_imageView.setTip();
                    }
                    Glide.with(MessageFragament.this)
                            .load(Contants.baseImage+ contact.getIcon())
                            .apply(options).into( messageItemHolder.contact_imageView);

                    messageItemHolder.contact_name.setText(contact.getName());
                    messageItemHolder.contact_message.setText(contact.getLastmessage());
                    messageItemHolder.contact_time.setText(contact.getLastTime());
                }
            }

            @Override
            public int getItemCount() {
                return contacts.size();
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }
        };
        contact_list.setAdapter(adapter);
        dataChange();
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(contactPresenter!=null){
            contactPresenter.getContacts();
        }
    }

    @Override
    public void firstVisible() {
        super.firstVisible();
        contactPresenter=new MessageContactPresenter(this);
        homeView = (HomeView) getActivity();
        contactPresenter.getContacts();
    }


    @Override
    public void setContactData(List<Contact> contactData) {
        contacts =contactData;
        dataChange();
    }

    @Override
    public void updateContactData(List<Contact> contactData) {
        if(contactData!=null){
            contacts.addAll(contactData);
            dataChange();
        }
    }
    private void dataChange(){
        LogUtils.e(getTAG(),"dataChange");
        int count=0;
        for(Contact contact:contacts){
            count=count+contact.getUnReadMessageNumber();
        }
        if(homeView==null){
            homeView = (HomeView) getActivity();
        }
        if(homeView!=null)
        homeView.updateTipMessage(0,count);
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void toSendMessage(Contact contact, User user) {
        Intent intent=   new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(Contants.user_flag, user);
        intent.putExtra(Contants.contact_flag, contact);
        startActivity(intent);
    }

    @Override
    public void update() {
        if(contactPresenter!=null){
            contactPresenter.getContacts();
        }
    }
}
