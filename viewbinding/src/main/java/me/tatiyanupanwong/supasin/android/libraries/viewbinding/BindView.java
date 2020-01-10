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

package me.tatiyanupanwong.supasin.android.libraries.viewbinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates a view ID to which the annotated field will be bound. The view
 * will automatically be cast to the field type.
 *
 * <pre><code>
 * {@literal @}BindView(R.id.title) TextView title;
 * </code></pre>
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface BindView {
    /**
     * Returns a view ID to which the field will be bound.
     *
     * @return a view ID to which the field will be bound
     */
    @IdRes
    int value();
}
