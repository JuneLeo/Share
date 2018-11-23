package com.leo.events.card.box;

import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;

/**
 * Created by spf on 2018/11/15.
 */
public class BoxCard extends DefaultCard {

    public BoxCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new BoxCardVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new BoxCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new BoxPresenter();
    }
}
