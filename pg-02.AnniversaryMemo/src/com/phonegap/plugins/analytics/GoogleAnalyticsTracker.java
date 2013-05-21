/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2006-2011 Worklight, Ltd.
 * Upgraded by Doers' Guild
 */

package com.phonegap.plugins.analytics;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.Tracker;

public class GoogleAnalyticsTracker extends CordovaPlugin {
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String TRACK_PAGE_VIEW = "trackPageView";
    public static final String TRACK_EVENT = "trackEvent";
    public static final String SET_CUSTOM_DIMENSION = "setCustomDimension";
    public static final String TRACK_TIMING = "trackTiming";

    private Tracker tracker;
    private com.google.analytics.tracking.android.EasyTracker instance;

    public GoogleAnalyticsTracker() {
        instance = com.google.analytics.tracking.android.EasyTracker
                .getInstance();
    }

    @Override
//    public PluginResult execute(String action, JSONArray data, String callbackId) {
    public boolean execute(String action, JSONArray data, CallbackContext context) {
        if (START.equals(action)) {
            this.start();
            context.success();
            return true;
        } else if (TRACK_PAGE_VIEW.equals(action)) {
            try {
                this.trackPageView(data.getString(0));
                context.success();
                return true;
            } catch (JSONException e) {
                this.error(context, PluginResult.Status.JSON_EXCEPTION);
                return true;
            }
        } else if (TRACK_EVENT.equals(action)) {
            try {
                this.trackEvent(
                        data.getString(0),
                        data.getString(1),
                        data.getString(2),
                        data.getLong(3));
                context.success();
                return true;
            } catch (JSONException e) {
                this.error(context, PluginResult.Status.JSON_EXCEPTION);
                return true;
            }
        } else if (STOP.equals(action)) {
            this.stop();
            context.success();
            return true;
        } else if (SET_CUSTOM_DIMENSION.equals(action)) {
            try {
                setCustomDimension(data.getInt(0), data.getString(1));
                context.success();
                return true;
            } catch (JSONException e) {
                this.error(context, PluginResult.Status.JSON_EXCEPTION);
                return true;
            }
        } else if (TRACK_TIMING.equals(action)) {
            try {
                trackTiming(
                        data.getString(0),
                        data.getLong(1),
                        data.getString(2),
                        data.getString(3));
                context.success();
                return true;
            } catch (JSONException e) {
                this.error(context, PluginResult.Status.JSON_EXCEPTION);
                return true;
            }
        } else {
            // falseを返すと、this.error(context, Status.INVALID_ACTION)を内部でやってくれてるっぽい。
            return false;
        }
    }

    private void start() {
        instance.activityStart(this.cordova.getActivity());
        tracker = EasyTracker.getTracker();
    }

    private void stop() {
        instance.activityStop(this.cordova.getActivity());
    }

    private void trackPageView(String key) {
        tracker.sendView(key);
        GAServiceManager.getInstance().dispatch();
    }

    private void trackEvent(String category, String action, String label,
            long value) {
        tracker.sendEvent(category, action, label, value);
        GAServiceManager.getInstance().dispatch();
    }

    private void trackTiming(String category, long loadTime, String name,
            String label) {
        tracker.sendTiming(category, loadTime, name, label);
    }

    private void setCustomDimension(int Index, String dimensionValue) {
        tracker.setCustomDimension(Index, dimensionValue);
    }

    // CallbackContext#errorの自前ラッパー
    private void error(CallbackContext context, PluginResult.Status status) {
        context.error(PluginResult.StatusMessages[status.ordinal()]);
    }
}
