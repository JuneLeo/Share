package com.leo.events.card.box;


import com.card.internel.control.Priority;
import com.card.internel.obsever.Subscriber;
import com.card.internel.presenter.AbstractPresenter;

/**
 * Created by spf on 2018/11/15.
 */
@Priority
public class BoxPresenter extends AbstractPresenter<BoxCardVM> {

    @Override
    protected void registEvent() {
        mControl.getObservable("aaaa",BoxCard.class).subscribe(new Subscriber<BoxCard>() {
            @Override
            public void onNext(BoxCard appleCard) {

            }
        });
    }

    public void btnClick() {
        mControl.notify("key_person","你好");
    }

    public void btnClickClear() {

    }
}
