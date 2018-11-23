package com.leo.events.card.vip;


import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;

/**
 * Created by spf on 2018/11/15.
 */
public class VipCard extends DefaultCard {

    public VipCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new VipCardVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new VipCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new VipPresenter();
    }
}
