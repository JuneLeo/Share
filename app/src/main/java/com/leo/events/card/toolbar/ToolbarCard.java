package com.leo.events.card.toolbar;


import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;

/**
 * Created by spf on 2018/11/15.
 */
public class ToolbarCard extends DefaultCard {

    public ToolbarCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new ToolbarVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new ToolbarCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new ToolbarPresenter();
    }
}
