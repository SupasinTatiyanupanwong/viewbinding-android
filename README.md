# View Binding

Eliminates boilerplate `findViewById()` calls by using `@BindView` on fields.

## Usage

Annotate fields with `@BindView` and a view ID to the corresponding view in your layout.

```java
public class LoginActivity extends Activity {
    @BindView(R.id.user) EditText user;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.submit) View submit;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewBindingUtils.bind(this);
        // TODO use fields...
    }
}
```

You can perform binding on arbitrary objects by supplying your own view root.

```java
public class LoginFragment extends Fragment {
    @BindView(R.id.user) EditText user;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.submit) View submit;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ViewBindingUtils.bind(this, view);
        // TODO use fields...
    }
}
```

To reset binding, call `unbind` method to set annotated fields to `null`.

```java
public class LoginFragment extends Fragment {
    @BindView(R.id.user) EditText user;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.submit) View submit;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ViewBindingUtils.bind(this, view);
        // TODO use fields...
    }

    @Override public void onDestroyView() {
        ViewBindingUtils.unbind(this);
        super.onDestroyView();
    }
}
```

## Download

```groovy
implementation 'me.tatiyanupanwong.supasin.android.libraries.viewbinding:viewbinding:1.3.0'
```

## License

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
