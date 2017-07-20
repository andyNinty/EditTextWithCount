package cn.andyleeblog.edittextwithcount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import cn.andyleeblog.lib.EditTextWithCount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTextWithCount editTextWithCount = (EditTextWithCount) findViewById(R.id.edittext);
        editTextWithCount.setLines(7);
        editTextWithCount.setMaxCount(120);
        editTextWithCount.setViewOrientation(LinearLayout.VERTICAL);
    }
}
