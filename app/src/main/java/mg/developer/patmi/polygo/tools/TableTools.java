package mg.developer.patmi.polygo.tools;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import mg.developer.patmi.polygo.R;

/**
 * Created by patmi on 10/02/2017.
 */
public class TableTools {

    public static TableRow addHeadRow(Context context, TableLayout tableLayout) {
        return TableTools.createTableRow(context, tableLayout, R.drawable.table_head_border);
    }

    public static TableRow addDefaultRow(Context context, TableLayout tableLayout) {
        return TableTools.createTableRow(context, tableLayout, R.drawable.table_row_border);
    }

    protected static TableRow createTableRow(Context context, TableLayout tableLayout, int borderRef) {
        TableRow row = new TableRow(context);
        tableLayout.addView(row);

        row.setBackground(context.getResources().getDrawable(borderRef));

        ViewGroup.MarginLayoutParams rowParamsMargin = (ViewGroup.MarginLayoutParams) row.getLayoutParams();
        row.setLayoutParams(rowParamsMargin);
        return row;
    }

    public static void addLabelViewRow(Context context, String title, TextView textView, TableRow row) {
        TableTools.addViewRow(context, title, textView, row, R.color.title_color);
    }

    public static void addDefaultViewRow(Context context, String title, TextView textView, TableRow row) {

        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        TableTools.addViewRow(context, title, textView, row, R.color.default_color);
    }

    protected static void addViewRow(Context context, String title, TextView textView, TableRow row, int refColor) {
        row.addView(textView);

        ViewGroup.MarginLayoutParams paramsMargin = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        paramsMargin.setMargins(1, 1, 1, 1);
        paramsMargin.width = Tools.getWidth(context, 5) - 10;
        paramsMargin.height = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(paramsMargin);

        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(refColor));
    }
}
