package com.card.internel.presenter;

import com.card.internel.card.ICard;
import com.card.internel.model.CardVM;
import com.card.internel.view.ICardView;

public interface ICardPresenter {

    void onBindCard(ICard card);

    ICardView getICardView();
}