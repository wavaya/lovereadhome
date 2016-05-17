package com.wayhua.framework.zxing.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.google.zxing.Result;
import com.wayhua.framework.zxing.camera.CameraManager;

/**
 * Created by Administrator on 2015/12/3.
 */
public interface ICapture {
    public Handler getHandler();

    Rect getCropRect();

    void handleDecode(Result rawResult, Bundle bundle);

    CameraManager getCameraManager();
}
