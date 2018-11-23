package com.leo.events.wallpaper;

import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Created by spf on 2018/11/18.
 */
public class TimeWallpaper extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new TimeEngine();
    }
    class TimeEngine extends Engine{
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }
    }

}
