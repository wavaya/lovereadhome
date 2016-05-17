/*
 * Copyright (c) 2015 PocketHub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wayhua.framework;


import android.content.Context;
import android.util.Log;

import com.wayhua.framework.core.AsyncLoader;


public abstract class ThrowableLoader<D> extends AsyncLoader<D> {

    private static final String TAG = "ThrowableLoader";

    private final D data;

    private Exception exception;

    /**
     * Create loader for context and seeded with initial data
     *
     * @param context
     * @param data
     */
    public ThrowableLoader(Context context, D data) {
        super(context);

        this.data = data;
    }


    protected D getAccountFailureData() {
        return data;
    }

    @Override
    public final D loadInBackground() {
        try {
            return load();
        } finally {

        }

    }

    public D load() {
        exception = null;
        try {
            return loadData();
        } catch (Exception e) {

            try {
                return loadData();
            } catch (Exception e2) {
                e = e2;
            }
            Log.d(TAG, "Exception loading data", e);
            exception = e;
            return data;
        }
    }

    /**
     * @return exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Clear the stored exception and return it
     *
     * @return exception
     */
    public Exception clearException() {
        final Exception throwable = exception;
        exception = null;
        return throwable;
    }

    /**
     * Load data
     *
     * @return data
     * @throws Exception
     */
    public abstract D loadData() throws Exception;
}
