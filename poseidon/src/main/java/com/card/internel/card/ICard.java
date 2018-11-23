package com.card.internel.card;


import com.card.internel.CardControl;
import com.card.internel.control.ILifecycle;
import com.card.internel.control.IPrority;
import com.card.internel.presenter.ICardPresenter;
import com.card.internel.view.ICardView;

/**
 * Created by spf on 2018/11/15.
 */
public interface ICard extends ILifecycle, IPrority {

    ICardView getCardView();

    ICardPresenter getCardPresenter();

    CardControl getPoseidonControl();

    void onAttachCardManager(CardManager manager);

    CardManager getCardManager();

}
