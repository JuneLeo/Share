package com.leo.events.card.tab;


import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;


/**
 * Created by spf on 2018/11/15.
 */
public class TabCard extends DefaultCard {

    public TabCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new TabCardVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new TabCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new TabPresenter();
    }
}
