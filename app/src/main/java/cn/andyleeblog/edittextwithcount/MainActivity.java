package cn.andyleeblog.edittextwithcount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.andyleeblog.lib.EditTextWithCount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTextWithCount editTextWithCount = (EditTextWithCount) findViewById(R.id.edittext);
//        editTextWithCount.setLines(5/);
//        editTextWithCount.setMaxCount(120);
//        editTextWithCount.setHintText("多行edittext");
    }
}
