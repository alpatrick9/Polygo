package mg.developer.patmi.polygo;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import mg.developer.patmi.polygo.configuration.SqliteHelper;
import mg.developer.patmi.polygo.fragment.DataFragment;
import mg.developer.patmi.polygo.fragment.ResultFragment;
import mg.developer.patmi.polygo.tools.bean_manager.DefaultDataManager;
import mg.developer.patmi.polygo.tools.dialog_manager.DialogDataManager;
import mg.developer.patmi.polygo.tools.KeyboardManager;
import mg.developer.patmi.polygo.tools.Tools;
import mg.developer.patmi.polygo.tools.dialog_manager.DialogDefaultDataManager;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar toolbar;
    protected DialogDataManager dialogDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(!DefaultDataManager.isDefaultSet(this)) {
            DialogDefaultDataManager.dialogChangeDefaultData(this);
        }

        openHelperDatabase();

        setProvider();

        setView();

        setDefaultView();

        setNavigation();
    }

    protected void openHelperDatabase() {
        OpenHelperManager.getHelper(this, SqliteHelper.class);
    }

    protected void setProvider() {
        dialogDataManager = new DialogDataManager(this);
    }

    protected void setView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    protected void setDefaultView() {
        inflateMenuData();
        Fragment fragment = new DataFragment();
        Tools.replaceFragment(this, fragment);
    }

    protected void setNavigation() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                new KeyboardManager(MainActivity.this).hideKeyboard();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.copyright);
        textView.setText(Html.fromHtml(infoApp()));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        toolbar.getMenu().clear();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_data:
                inflateMenuData();
                fragment = new DataFragment();
                break;
            case R.id.nav_result:
                fragment = new ResultFragment();
                break;
            case R.id.nav_init_data:
                DialogDefaultDataManager.dialogChangeDefaultData(this);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Tools.replaceFragment(this, fragment);
        return true;
    }

    protected void inflateMenuData() {
        toolbar.inflateMenu(R.menu.data_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addDataMenu:
                        dialogDataManager.dialogSaveData();
                        break;
                    case R.id.clearAllMenu:
                        dialogDataManager.dialogDataDeleteAll();
                        break;
                }
                return false;
            }
        });
    }

    public String infoApp() {
        String copyright = "&copy; 2016";

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            copyright = copyright+" v"+info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return copyright;
    }
}
