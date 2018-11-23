package com.leo.events.card;


import com.card.internel.CardControl;
import com.card.internel.card.AbstractCard;
import com.card.internel.card.CardManager;
import com.card.internel.model.CardVM;
import com.card.internel.presenter.AbstractPresenter;
import com.card.internel.presenter.ICardPresenter;
import com.card.internel.view.AbstractView;
import com.card.internel.view.ICardView;

/**
 * Created by spf on 2018/11/15.
 */
public abstract class DefaultCard extends AbstractCard {

    private CardControl mControl;
    private AbstractView mCardView;
    private AbstractPresenter mCardPresenter;
    private CardManager mCardManager;

    public DefaultCard(CardControl control) {
        this.mControl = control;
        // view 取 vm中数据
        // presenter给vm设置数据，
        // view事件在presenter中

        //vm
        CardVM cardVM = instanceCardVM();
        //presenter
        mCardPresenter = instancePresenter();
        mCardPresenter.setCardVM(cardVM);
        //view
        mCardView = instanceCardView();
        mCardView.setCardVM(cardVM);
        //card
        mCardPresenter.onBindCard(this);
        //view绑定presenter
        mCardView.onBindPresenter(mCardPresenter);

    }

    /**
     * vm
     * @return
     */
    protected abstract CardVM instanceCardVM();

    /**
     * view
     * @return
     */
    protected abstract AbstractView instanceCardView();

    /**
     * presenter
     * @return
     */
    protected abstract AbstractPresenter instancePresenter();

    @Override
    public void onAttachCardManager(CardManager manager) {
        this.mCardManager = manager;
    }

    @Override
    public ICardPresenter getCardPresenter() {
        return mCardPresenter;
    }

    @Override
    public CardControl getPoseidonControl() {
        return mControl;
    }

    @Override
    public ICardView getCardView() {
        return mCardView;
    }

    @Override
    public CardManager getCardManager() {
        return mCardManager;
    }


}
