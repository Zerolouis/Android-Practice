Overview:

- An `Activity` is an app component that provides a single screen focused on a single user task.
- Each `Activity` has its own user interface layout file.
- You can assign your `Activity` implementations a parent/child relationship to enable Up navigation within your app.
- A `View` can be made visible or invisible with the `android:visibility` attribute.

To implement an `Activity:`

- Choose **File > New > Activity** to start from a template and do the following steps automatically.
- If not starting from a template, create an `Activity` Java class, implement a basic UI for the `Activity` in an associated XML layout file, and declare the new `Activity` in AndroidManifest.xml.

`Intent`:

- An `Intent` lets you request an action from another component in your app, for example, to start one `Activity` from another. An `Intent` can be explicit or implicit.
- With an explicit `Intent` you indicate the specific target component to receive the data.
- With an implicit `Intent` you specify the functionality you want but not the target component.
- An `Intent` can include data on which to perform an action (as a URI) or additional information as `Intent` *extras*.
- `Intent` *extras* are key/value pairs in a `Bundle` that are sent along with the `Intent`.