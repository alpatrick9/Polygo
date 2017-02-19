package mg.developer.patmi.polygo.tools;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by patmi on 10/02/2017.
 */
public class KeyboardManager {

    private Context context;
    private InputMethodManager keyManager;

    public KeyboardManager(Context context) {
        this.context = context;
        this.keyManager = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
    }

    public void hideKeyboard() {
        this.keyManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
    }
}
