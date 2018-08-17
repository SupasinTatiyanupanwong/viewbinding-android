/*
 * Copyright (C) 2015 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.viewbinding;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused") // Public APIs
public final class ViewBindingUtils {
    private ViewBindingUtils() {}

    @SuppressWarnings("unchecked")
    @CheckResult
    public static <T extends View> T bind(@NonNull Activity source, @IdRes int viewId) {
        return (T) source.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    @CheckResult
    public static <T extends View> T bind(@NonNull Dialog source, @IdRes int viewId) {
        return (T) source.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    @CheckResult
    public static <T extends View> T bind(@NonNull View source, @IdRes int viewId) {
        return (T) source.findViewById(viewId);
    }

    public static void bind(@NonNull Activity target) {
        bindInternal(target, target.getWindow().getDecorView());
    }

    public static void bind(@NonNull Dialog target) {
        //noinspection ConstantConditions - Nothing we can do anyway
        bindInternal(target, target.getWindow().getDecorView());
    }

    public static void bind(@NonNull View target) {
        bindInternal(target, target);
    }

    public static void bind(@NonNull Object target, @NonNull Activity source) {
        bindInternal(target, source.getWindow().getDecorView());
    }

    public static void bind(@NonNull Object target, @NonNull Dialog source) {
        //noinspection ConstantConditions - Nothing we can do anyway
        bindInternal(target, source.getWindow().getDecorView());
    }

    public static void bind(@NonNull Object target, @NonNull View source) {
        bindInternal(target, source);
    }


    private static void bindInternal(@NonNull Object target, @NonNull View source) {
        final Map<Field, Integer> map = new HashMap<>();

        Class<?> clazz = target.getClass();
        while (clazz != null) {
            String clazzName = clazz.getName();
            if (clazzName.startsWith("android.") || clazzName.startsWith("java.")) {
                break; // Reached framework class. Abandoning search.
            }

            for (Field field : clazz.getDeclaredFields()) {
                BindView annotation = field.getAnnotation(BindView.class);
                if (annotation != null) {
                    field.setAccessible(true);
                    map.put(field, annotation.value());
                }
            }

            clazz = clazz.getSuperclass();
        }

        try {
            for (Field field : map.keySet()) {
                field.set(target, source.findViewById(map.get(field)));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
