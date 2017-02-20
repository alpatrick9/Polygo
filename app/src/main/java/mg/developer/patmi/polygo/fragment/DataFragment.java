package mg.developer.patmi.polygo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.developer.patmi.polygo.R;
import mg.developer.patmi.polygo.dao.DataDao;
import mg.developer.patmi.polygo.models.entity.Data;
import mg.developer.patmi.polygo.tools.DialogManager;
import mg.developer.patmi.polygo.tools.TableTools;

/**
 * Created by patmi on 30/01/2017.
 */
public class DataFragment extends Fragment {

    protected View rootView;
    protected TableLayout dataTable;

    protected DataDao dataDao;

    protected List<Data> datas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.data_fragment, container, false);

        initProvider();

        setData();

        initView();

        bindDataToView();

        return rootView;
    }

    private void initProvider() {
        dataDao = new DataDao(getActivity());
    }

    private void setData() {
        try {
            datas = dataDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        dataTable = (TableLayout) rootView.findViewById(R.id.data);
        dataTable.setBackground(getResources().getDrawable(R.drawable.table_border));

        TableRow row = TableTools.addHeadRow(getActivity(), dataTable);

        TextView stationsTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.station_title), stationsTitle, row);

        TextView pvTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.pv_title), pvTitle, row);

        TextView hzTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.hz_title), hzTitle, row);

        TextView vTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.v_title), vTitle, row);

        TextView diTitle = new TextView(getActivity());
        TableTools.addLabelViewRow(getActivity(), getResources().getString(R.string.di_title), diTitle, row);
    }

    private void bindDataToView() {
        for (int i = 0; i < datas.size(); i++) {
            final DialogManager dialogManager = new DialogManager(getActivity());
            final Data data = datas.get(i);

            TableRow row = TableTools.addDefaultRow(getActivity(), dataTable);
            TextView stationsTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), data.getStations(), stationsTitle, row);

            TextView pvTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), data.getpV(), pvTitle, row);

            TextView hzTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), data.gethZ().toString(), hzTitle, row);

            TextView vTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), data.getV().toString(), vTitle, row);

            TextView diTitle = new TextView(getActivity());
            TableTools.addDefaultViewRow(getActivity(), data.getDi().toString(), diTitle, row);

            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogManager.dialogUpdateData(data.getId());
                }
            });

            row.setLongClickable(true);
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dialogManager.dialogDataDelete(data);
                    return false;
                }
            });
        }
    }
}
