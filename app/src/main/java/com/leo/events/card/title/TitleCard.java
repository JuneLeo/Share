package com.leo.events.card.title;


import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;

/**
 * Created by spf on 2018/11/15.
 */
public class TitleCard extends DefaultCard {

    public TitleCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new TitleCardVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new TitleCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new TitlePresenter();
    }
}
