package com.leo.events.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leo.events.shareutil.ShareUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by wangchao on 2017/4/21.
 */

public abstract class AbstractWXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    protected String TAG = AbstractWXEntryActivity.class.getSimpleName();

    abstract protected String getAppId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, getAppId());
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        handleIntent(getIntent());
    }

    protected void handleIntent(Intent intent) {
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean success = api.handleIntent(intent, this);
            if (!success) {
                finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {}

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i("",baseResp.toString());

        if (baseResp instanceof SendMessageToWX.Resp){

            SendMessageToWX.Resp resp = (SendMessageToWX.Resp) baseResp;

            switch (resp.errCode){
                case SendMessageToWX.Resp.ErrCode.ERR_OK:

                    ShareUtil.mShareListener.shareSuccess();
                    break;
                case SendMessageToWX.Resp.ErrCode.ERR_AUTH_DENIED:
                    ShareUtil.mShareListener.shareFailure(new Exception("分享失败"));
                    break;
                case SendMessageToWX.Resp.ErrCode.ERR_USER_CANCEL:
                    ShareUtil.mShareListener.shareCancel();
                    break;
            }
//            LocalBroadcastManager.getInstance(this).sendBroadcast(data);
        }

        finish();
    }
}
