Android View Binding Library
============================
Eliminates boilerplate `findViewById()` calls by using `@BindView` on fields.

```java
public class LoginActivity extends Activity {
    @BindView(R.id.user) EditText user;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.submit) View submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewBindingUtils.bind(this);
        // TODO: Use fields...
    }
}
```

```java
public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewHolder views = new ViewHolder(this);
        // TODO: Use fields...
    }

    private static final class ViewHolder {
        @BindView(R.id.user) EditText user;
        @BindView(R.id.pass) EditText pass;
        @BindView(R.id.submit) View submit;

        ViewHolder(Activity activity) {
            ViewBindingUtils.bind(this, activity);
        }
    }
}
```

Download
--------

```groovy
dependencies {
  implementation 'me.tatiyanupanwong.supasin.android:viewbinding:1.2.1'
}
```

License
=======

```
Copyright (C) 2015 Supasin Tatiyanupanwong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
