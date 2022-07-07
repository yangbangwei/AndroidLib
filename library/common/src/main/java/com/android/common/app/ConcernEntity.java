package com.android.common.app;

public class ConcernEntity {
    /**
     * l_id : 1
     * l_title : 大乐透
     * l_img :
     * l_concern : 1000
     */
    private boolean isSelect;
    //是否为添加入口
    private boolean isAdd;
    private String l_id;
    private String l_title;
    private String l_img;
    private String l_type;
    private String l_concern;
    private String r_isuse;

    public String getL_concern() {
        return l_concern;
    }

    public void setL_concern(String l_concern) {
        this.l_concern = l_concern;
    }

    public String getR_isuse() {
        return r_isuse;
    }

    public void setR_isuse(String r_isuse) {
        this.r_isuse = r_isuse;
    }

    public ConcernEntity(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    public void setL_title(String l_title) {
        this.l_title = l_title;
    }

    public void setL_img(String l_img) {
        this.l_img = l_img;
    }

    public String getL_id() {
        return l_id;
    }

    public String getL_title() {
        return l_title;
    }

    public String getL_img() {
        return l_img;
    }

    public String getL_type() {
        return l_type;
    }

    public void setL_type(String l_type) {
        this.l_type = l_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConcernEntity concernEntity = (ConcernEntity) o;
        if (!l_id.equals(concernEntity.getL_id())) {
            return false;
        }
        return true;
    }
}