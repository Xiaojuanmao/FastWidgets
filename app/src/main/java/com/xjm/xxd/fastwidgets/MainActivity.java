package com.xjm.xxd.fastwidgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xjm.xxd.fastwidget.container.WidgetGroupContainer;
import com.xjm.xxd.fastwidget.edit.EditWidgetView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    WidgetGroupContainer mContainer;
    @BindView(R.id.edit_view)
    EditWidgetView mEditView;

    private static final int MENU_ID_MANAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mEditView.setAdapter(new EditWidgetAdapter(LayoutInflater.from(this)));
        mEditView.bindContainerEditor(mContainer.edit());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_ID_MANAGE, Menu.NONE, "管理");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_MANAGE:
                mEditView.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mEditView.getVisibility() == View.VISIBLE) {
            mEditView.hide();
        } else {
            super.onBackPressed();
        }
    }

}
