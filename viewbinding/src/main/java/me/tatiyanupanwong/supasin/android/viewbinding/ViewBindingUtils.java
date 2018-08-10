package me.tatiyanupanwong.supasin.android.viewbinding;

import android.app.Activity;
import android.app.Dialog;
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
    public static <T extends View> T bind(@NonNull View source, @IdRes int viewId) {
        return (T) source.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T bind(@NonNull Activity source, @IdRes int viewId) {
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
