package com.leo.events.card.func;


import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;

/**
 * Created by spf on 2018/11/15.
 */
public class FuncCard extends DefaultCard {

    public FuncCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new FuncCardVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new FuncCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new FuncPresenter();
    }
}
