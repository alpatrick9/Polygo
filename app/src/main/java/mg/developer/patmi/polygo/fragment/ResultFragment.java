package mg.developer.patmi.polygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mg.developer.patmi.polygo.R;

/**
 * Created by patmi on 30/01/2017.
 */
public class ResultFragment extends Fragment {

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_fragment, container, false);
        return rootView;
    }
}
