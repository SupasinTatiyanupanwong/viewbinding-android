/*
 * Copyright 2015 Supasin Tatiyanupanwong
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

package me.tatiyanupanwong.supasin.libraries.android.viewbinding;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import androidx.annotation.CheckResult;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Binds {@link BindView} annotated fields in the specified target to its
 * corresponding {@link View} objects with matching {@link View#getId()}.
 */
@SuppressWarnings({ "unused", "WeakerAccess" }) // Public APIs
public final class ViewBindingUtils {
    private ViewBindingUtils() {}

    /**
     * Syntactic sugar for {@link Activity#findViewById(int)} which infers the target type.
     *
     * @param source the Activity to search the View in
     * @param id the ID to search for
     *
     * @return a view with given ID if found, or {@code null} otherwise
     *
     * @deprecated Compile against API 26 and use {@link Activity#findViewById(int)}.
     */
    @Nullable
    @CheckResult
    @Deprecated
    public static <T extends View> T bind(@NonNull Activity source, @IdRes int id) {
        return source.findViewById(id);
    }

    /**
     * Syntactic sugar for {@link Dialog#findViewById(int)} which infers the target type.
     *
     * @param source the Activity to search the View in
     * @param id the ID to search for
     *
     * @return a view with given ID if found, or {@code null} otherwise
     *
     * @deprecated Compile against API 26 and use {@link Dialog#findViewById(int)}.
     */
    @Nullable
    @CheckResult
    @Deprecated
    public static <T extends View> T bind(@NonNull Dialog source, @IdRes int id) {
        return source.findViewById(id);
    }

    /**
     * Syntactic sugar for {@link View#findViewById(int)} which infers the target type.
     *
     * @param source the Activity to search the View in
     * @param id the ID to search for
     *
     * @return a view with given ID if found, or {@code null} otherwise
     *
     * @deprecated Compile against API 26 and use {@link View#findViewById(int)}.
     */
    @Nullable
    @CheckResult
    @Deprecated
    public static <T extends View> T bind(@NonNull View source, @IdRes int id) {
        return source.findViewById(id);
    }

    /**
     * Binds {@link BindView} annotated field in the specified {@link Activity}.
     * The current content view is used as the view root.
     *
     * @param target target {@code Activity} for view binding
     */
    @UiThread
    public static void bind(@NonNull Activity target) {
        bind(target, target.getWindow().getDecorView());
    }

    /**
     * Binds {@link BindView} annotated field in the specified {@link Dialog}.
     * The current content view is used as the view root.
     *
     * @param target target {@code Dialog} for view binding
     */
    @UiThread
    public static void bind(@NonNull Dialog target) {
        //noinspection ConstantConditions
        bind(target, target.getWindow().getDecorView());
    }

    /**
     * Binds {@link BindView} annotated field in the specified {@link View}.
     * The current content view is used as the view root.
     *
     * @param target target {@code View} for view binding
     */
    @UiThread
    public static void bind(@NonNull View target) {
        bind(target, target);
    }

    /**
     * Binds {@link BindView} annotated field in the specified {@code target}
     * using the {@code source} {@link Activity} as the view root.
     *
     * @param target target {@code Object} for view binding
     * @param source source {@code Activity} on which IDs will be looked up
     */
    @UiThread
    public static void bind(@NonNull Object target, @NonNull Activity source) {
        bind(target, source.getWindow().getDecorView());
    }

    /**
     * Binds {@link BindView} annotated field in the specified {@code target}
     * using the {@code source} {@link Dialog} as the view root.
     *
     * @param target target {@code Object} for view binding
     * @param source source {@code Dialog} on which IDs will be looked up
     */
    @UiThread
    public static void bind(@NonNull Object target, @NonNull Dialog source) {
        //noinspection ConstantConditions
        bind(target, source.getWindow().getDecorView());
    }

    /**
     * Binds {@link BindView} annotated field in the specified {@code target}
     * using the {@code source} {@link View} as the view root.
     *
     * @param target target {@code Object} for view binding
     * @param source source {@code View} on which IDs will be looked up
     */
    @UiThread
    public static void bind(@NonNull Object target, @NonNull View source) {
        final Map<Field, Integer> map = new HashMap<>();

        Class<?> clazz = target.getClass();
        while (clazz != null) {
            if (isFrameworkClass(clazz.getName())) {
                break; // Reached framework class. Abandoning search.
            }

            for (Field field : clazz.getDeclaredFields()) {
                BindView annotation = field.getAnnotation(BindView.class);
                if (annotation != null) {
                    map.put(field, annotation.value());
                }
            }

            clazz = clazz.getSuperclass();
        }

        try {
            for (Field field : map.keySet()) {
                field.setAccessible(true);
                //noinspection ConstantConditions
                field.set(target, source.findViewById(map.get(field)));
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Unbinds {@link BindView} annotated field in the specified {@code target}.
     *
     * @param target target {@code Object} for view unbinding
     */
    @UiThread
    public static void unbind(@NonNull Object target) {
        final List<Field> fields = new ArrayList<>();

        Class<?> clazz = target.getClass();
        while (clazz != null) {
            if (isFrameworkClass(clazz.getName())) {
                break; // Reached framework class. Abandoning search.
            }

            for (Field field : clazz.getDeclaredFields()) {
                BindView annotation = field.getAnnotation(BindView.class);
                if (annotation != null) {
                    fields.add(field);
                }
            }

            clazz = clazz.getSuperclass();
        }

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(target, null);
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }


    private static boolean isFrameworkClass(String className) {
        return className.startsWith("android.")
                || className.startsWith("androidx.")
                || className.startsWith("java.");
    }
}
