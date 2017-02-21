package mg.developer.patmi.polygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import mg.developer.patmi.polygo.R;
import mg.developer.patmi.polygo.models.bean.Result;
import mg.developer.patmi.polygo.tools.TableTools;
import mg.developer.patmi.polygo.tools.bean_manager.ResultManager;

/**
 * Created by patmi on 30/01/2017.
 */
public class ResultFragment extends Fragment {

    protected View rootView;
    protected TableLayout resultTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_fragment, container, false);

        initView();

        bindDataToView();

        return rootView;
    }

    private void initView() {
        resultTable = (TableLayout) rootView.findViewById(R.id.result);
        resultTable.setBackground(getResources().getDrawable(R.drawable.table_border));

        TableRow row = TableTools.addHeadRow(getActivity(), resultTable);

        TextView pvTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.pv_title), pvTitle, row);

        TextView dhTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.dh_title), dhTitle, row);

        TextView gisementTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.gisement_title), gisementTitle, row);

        TextView xmTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.xm_title), xmTitle, row);

        TextView ymTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.ym_title), ymTitle, row);
    }

    private void bindDataToView() {
        List<Result> results = ResultManager.getResults(getActivity());
        for (int i = 0; i < results.size(); i++) {
            final Result result = results.get(i);

            TableRow row = TableTools.addDefaultRow(getActivity(), resultTable);

            TextView pvTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), result.getData().getpV(), pvTitle, row);

            TextView dhTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), result.getdH().toString(), dhTitle, row);

            TextView gisementTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), result.getGisement().toString(), gisementTitle, row);

            TextView xmTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), result.getxM().toString(), xmTitle, row);

            TextView ymTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), result.getyM().toString(), ymTitle, row);

        }
    }

}
