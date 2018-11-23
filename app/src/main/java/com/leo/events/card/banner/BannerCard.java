package com.leo.events.card.banner;

import com.card.internel.CardControl;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.view.AbstractView;
import com.leo.events.card.DefaultCard;

/**
 * Created by spf on 2018/11/15.
 */
public class BannerCard extends DefaultCard {

    public BannerCard(CardControl control) {
        super(control);
    }

    @Override
    protected CardVM instanceCardVM() {
        return new BannerCardVM();
    }

    @Override
    protected AbstractView instanceCardView() {
        return new BannerCardView();
    }

    @Override
    protected AbstractPresenter instancePresenter() {
        return new BannerPresenter();
    }
}
