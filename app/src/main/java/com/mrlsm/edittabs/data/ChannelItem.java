package com.mrlsm.edittabs.data;

import java.io.Serializable;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class ChannelItem implements Serializable {

    private static final long serialVersionUID = -6465237897027410019L;
    /**
     * 栏目对应ID
     */
    public Integer id;
    /**
     * 栏目对应name
     */
    public String name;
    /**
     * 栏目在整体中的排序顺序  rank
     */
    private Integer orderId;
    /**
     * 栏目是否选中
     */
    private boolean selected;

    private boolean canEdit = true;

    public ChannelItem() {
    }

    public ChannelItem(int id, String name, int orderId, boolean selected) {
        this(id, name, orderId, selected, true);
    }

    public ChannelItem(int id, String name, int orderId, boolean selected, boolean canEdit) {
        this.id = Integer.valueOf(id);
        this.name = name;
        this.orderId = Integer.valueOf(orderId);
        this.selected = selected;
        this.canEdit = canEdit;
    }

    public int getId() {
        return this.id.intValue();
    }

    public String getName() {
        return this.name;
    }

    public int getOrderId() {
        return this.orderId.intValue();
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setId(int paramInt) {
        this.id = Integer.valueOf(paramInt);
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setOrderId(int paramInt) {
        this.orderId = Integer.valueOf(paramInt);
    }

    public void setSelected(boolean paramInteger) {
        this.selected = paramInteger;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
}
