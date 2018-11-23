package com.leo.events.tile;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.leo.events.MainApplication;
import com.leo.events.ProductActivity;

/**
 * Created by spf on 2018/11/17.
 */
@RequiresApi(Build.VERSION_CODES.N)
public class ShareTileService extends TileService {

    public static final String TAG = ShareTileService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");

    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Log.e(TAG,"onTileAdded");
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        Log.e(TAG,"onTileRemoved");
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.e(TAG,"onStartListening");
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.e(TAG,"onStopListening");
    }

    @Override
    public void onClick() {
        super.onClick();
        Log.e(TAG,"onClick");

        //获取自定义的Tile.
        Tile tile = getQsTile();
        if (tile == null) {
            return;
        }
        Log.d("MyQSTileService", "Tile state: " + tile.getState());
//        switch (tile.getState()) {
//            case Tile.STATE_ACTIVE:
//                //当前状态是开，设置状态为关闭.
//                tile.setState(Tile.STATE_INACTIVE);
//                //更新快速设置面板上的图块的颜色，状态为关.
//                tile.updateTile();
//                //do close somethings.
//                break;
//            case Tile.STATE_UNAVAILABLE:
//                break;
//            case Tile.STATE_INACTIVE:
//                //当前状态是关，设置状态为开.
//                tile.setState(Tile.STATE_ACTIVE);
//                //更新快速设置面板上的图块的颜色，状态为开.
//                tile.updateTile();
//                //do open somethings.
//                break;
//            default:
//                break;
//        }
        Intent intent = new Intent(MainApplication.getApp(),ProductActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainApplication.getApp(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
