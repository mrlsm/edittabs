package com.mrlsm.edittabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mrlsm.edittabs.R;
import com.mrlsm.edittabs.data.ChannelItem;

import java.util.List;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class OtherAdapter extends BaseAdapter {

    private Context context;
    private List<ChannelItem> channelList;
    /**
     * 是否可见
     */
    private boolean isVisible = true;
    /**
     * 要删除的position
     */
    private int remove_position = -1;

    public OtherAdapter(Context context, List<ChannelItem> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public ChannelItem getItem(int position) {
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.channel_add_item, null);
        TextView itemText = (TextView) view.findViewById(R.id.text_item);
        ChannelItem channel = getItem(position);
        itemText.setText(channel.getName());
        if (!isVisible && (position == channelList.size() - 1)) {
            itemText.setText("");
        }
        if (remove_position == position) {
            itemText.setText("");
        }
        return view;
    }

    /**
     * 获取频道列表
     */
    public List<ChannelItem> getChannelList() {
        return channelList;
    }

    /**
     * 添加频道列表
     */
    public void addItem(ChannelItem channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    /**
     * 设置删除的position
     */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
    }

    /**
     * 删除频道列表
     */
    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        notifyDataSetChanged();
    }

    /**
     * 设置频道列表
     */
    public void setListDate(List<ChannelItem> list) {
        channelList = list;
    }

    /**
     * 获取是否可见
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 设置是否可见
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}