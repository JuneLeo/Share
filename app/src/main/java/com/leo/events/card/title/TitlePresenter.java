package com.leo.events.card.title;


import com.card.internel.control.Priority;
import com.card.internel.presenter.AbstractPresenter;

/**
 * Created by spf on 2018/11/15.
 */
@Priority
public class TitlePresenter extends AbstractPresenter<TitleCardVM> {

    @Override
    protected void registEvent() {

    }


    public void btnClickClear() {
        mControl.notify("box_click","点击了box");
    }
}
