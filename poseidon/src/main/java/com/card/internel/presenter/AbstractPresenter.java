package com.card.internel.presenter;

import com.card.internel.CardControl;
import com.card.internel.card.ICard;
import com.card.internel.model.CardVM;
import com.card.internel.view.ICardView;

/**
 * Created by spf on 2018/11/15.
 */
public abstract class AbstractPresenter<VM extends CardVM> implements ICardPresenter {
    ICardView iCardView;
    protected CardControl mControl;
    protected VM iCardVM;

    public AbstractPresenter() {

    }

    @Override
    public void onBindCard(ICard card) {
        iCardView = card.getCardView();
        mControl = card.getPoseidonControl();
        registEvent();
    }

    protected abstract void registEvent();

    @Override
    public ICardView getICardView() {
        return iCardView;
    }

    public void setCardVM(VM iCardVM) {
        this.iCardVM = iCardVM;
    }
}
