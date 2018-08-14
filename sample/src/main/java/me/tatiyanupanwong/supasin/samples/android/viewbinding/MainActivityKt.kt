/*
 * Copyright (C) 2015-2018 Supasin Tatiyanupanwong
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

package me.tatiyanupanwong.supasin.samples.android.viewbinding

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import me.tatiyanupanwong.supasin.android.viewbinding.BindView
import me.tatiyanupanwong.supasin.android.viewbinding.ViewBindingUtils

class MainActivityKt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val views = ViewHolder(this)
        views.textView.setText(R.string.app_name)
    }

    private class ViewHolder internal constructor(activity: Activity) {
        @BindView(R.id.textView)
        lateinit var textView: TextView

        init {
            ViewBindingUtils.bind(this, activity)
        }
    }
}
