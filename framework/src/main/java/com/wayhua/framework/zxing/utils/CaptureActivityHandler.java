package com.wayhua.framework.zxing.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;

import com.wayhua.framework.R;
import com.wayhua.framework.zxing.activity.ICapture;
import com.wayhua.framework.zxing.camera.CameraManager;
import com.wayhua.framework.zxing.decode.DecodeThread;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class CaptureActivityHandler extends Handler {

	private final Context activity;
	private final DecodeThread decodeThread;
	private final CameraManager cameraManager;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(Context activity, CameraManager cameraManager, int decodeMode) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity, decodeMode);
		decodeThread.start();
		state = State.SUCCESS;

		// Start ourselves capturing previews and decoding.
		this.cameraManager = cameraManager;
		cameraManager.startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		if (message.what == R.id.restart_preview) {
		
			restartPreviewAndDecode();
		} else if (message.what == R.id.decode_succeeded)
		{
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			ICapture capture=( ICapture)activity;
			capture.handleDecode((Result) message.obj, bundle);
		}
	
		else if (message.what == R.id.decode_failed)
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
		{
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
		}
		else if (message.what == R.id.return_scan_result)
		{
			Activity act=(Activity)activity;
			act.setResult(Activity.RESULT_OK, (Intent) message.obj);
			act.finish();
		
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		cameraManager.stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
		quit.sendToTarget();
		try {
			// Wait at most half a second; should be enough time, and onPause()
			// will timeout quickly
			decodeThread.join(500L);
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
		}
	}

}
