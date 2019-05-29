package com.mrlsm.edittabs.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class ChannelManage {

    public static ChannelManage channelManage;
    /**
     * 默认的用户选择频道列表
     */
    private static List<ChannelItem> defaultUserChannels;
    /**
     * 默认的其他频道列表
     */
    private static List<ChannelItem> defaultOtherChannels;

    static {
        defaultUserChannels = new ArrayList<>();
        defaultOtherChannels = new ArrayList<>();
        defaultUserChannels.add(new ChannelItem(1, "推荐", 1, true, false));
        defaultUserChannels.add(new ChannelItem(2, "热点", 2, true));
        defaultUserChannels.add(new ChannelItem(3, "杭州", 3, true));
        defaultUserChannels.add(new ChannelItem(4, "时尚", 4, true));
        defaultUserChannels.add(new ChannelItem(5, "科技", 5, true));
        defaultUserChannels.add(new ChannelItem(6, "体育", 6, true));
        defaultUserChannels.add(new ChannelItem(7, "军事", 7, true));
        defaultOtherChannels.add(new ChannelItem(8, "财经", 1, false));
        defaultOtherChannels.add(new ChannelItem(9, "汽车", 2, false));
        defaultOtherChannels.add(new ChannelItem(10, "房产", 3, false));
        defaultOtherChannels.add(new ChannelItem(11, "社会", 4, false));
        defaultOtherChannels.add(new ChannelItem(12, "情感", 5, false));
        defaultOtherChannels.add(new ChannelItem(13, "女人", 6, false));
        defaultOtherChannels.add(new ChannelItem(14, "旅游", 7, false));
        defaultOtherChannels.add(new ChannelItem(15, "健康", 8, false));
        defaultOtherChannels.add(new ChannelItem(16, "美女", 9, false));
        defaultOtherChannels.add(new ChannelItem(17, "游戏", 10, false));
        defaultOtherChannels.add(new ChannelItem(18, "数码", 11, false));
        defaultUserChannels.add(new ChannelItem(19, "娱乐", 12, false));
    }

    /**
     * 初始化频道管理类
     */
    public static ChannelManage getManage() {
        if (channelManage == null)
            channelManage = new ChannelManage();
        return channelManage;
    }

    /**
     * 获取其他的频道
     *
     * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
     */
    public List<ChannelItem> getUserChannel() {
        return defaultUserChannels;
    }

    /**
     * 获取其他的频道
     *
     * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
     */
    public List<ChannelItem> getOtherChannel() {
        return defaultOtherChannels;
    }

    /**
     * 保存用户频道到数据库
     */
    public void saveUserChannel(List<ChannelItem> userList) {
        defaultUserChannels = userList;
    }

    /**
     * 保存其他频道到数据库
     */
    public void saveOtherChannel(List<ChannelItem> otherList) {
        defaultOtherChannels = otherList;
    }
}
