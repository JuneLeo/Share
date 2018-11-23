package com.card.internel.card;

import android.os.Bundle;

import com.card.internel.CardControl;
import com.card.internel.control.IPrority;
import com.card.internel.control.Priority;
import com.card.internel.presenter.ICardPresenter;
import com.card.internel.view.ICardView;


/**
 * Created by spf on 2018/11/15.
 */
public abstract class AbstractCard implements ICard {

    @Override
    public @Priority
    int getPriority() {
        return ICard.DEFAULT;
    }

    @Override
    public void onCreate(Bundle bundle) {
        getCardView().onCreate(bundle);
    }

    @Override
    public void onStart() {
        getCardView().onStart();
    }

    @Override
    public void onResume() {
        getCardView().onResume();
    }

    @Override
    public void onPause() {
        getCardView().onPause();
    }

    @Override
    public void onStop() {
        getCardView().onStop();
    }

    @Override
    public void onDestroy() {
        getCardView().onDestroy();
    }

    @Override
    public ICardView getCardView() {
        return null;
    }

    @Override
    public ICardPresenter getCardPresenter() {
        return null;
    }

    @Override
    public CardControl getPoseidonControl() {
        return null;
    }

    @Override
    public void onAttachCardManager(CardManager manager) {

    }

    @Override
    public CardManager getCardManager() {
        return null;
    }

    @Override
    public int compareTo(IPrority iCard) {
        return iCard.getPriority() - getPriority();
    }
}
