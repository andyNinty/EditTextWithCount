package cn.andyleeblog.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * EditTextWithCount
 * ---------------------------------------
 * |please input your word            6/10|
 * ---------------------------------------
 */

public class EditTextWithCount extends LinearLayout {
    private int maxCount = 10;
    private int textColor = Color.BLACK;
    private int leftDrawable;
    private EditText editText;
    private String hintText;
    private TextView textView;
    private int lines;

    public EditTextWithCount(Context context) {
        this(context, null);
    }

    public EditTextWithCount(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextWithCount(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithCount);
        maxCount = typedArray.getInteger(R.styleable.EditTextWithCount_maxCount, 10);
        lines = typedArray.getInteger(R.styleable.EditTextWithCount_lines, 1);
        textColor = typedArray.getColor(R.styleable.EditTextWithCount_textColor, Color.BLACK);
        leftDrawable = typedArray.getResourceId(R.styleable.EditTextWithCount_leftDrawable, 0);
        hintText = typedArray.getString(R.styleable.EditTextWithCount_hintText);
        int pixelOffset = getResources().getDimensionPixelOffset(R.dimen.text_normal);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.EditTextWithCount_textSize, pixelOffset);
        typedArray.recycle();
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        editText = new EditText(context);
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.view_space_small);
        int margin = context.getResources().getDimensionPixelOffset(R.dimen.view_space_normal);
        editText.setPadding(offset, offset, offset, offset);
        editText.setBackgroundColor(Color.WHITE);
        editText.setTextColor(textColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.setHint(hintText);
        editText.setHintTextColor(context.getResources().getColor(R.color.edit_hint_textcolor));
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        layoutParams.setMargins(offset, offset, offset, offset);
        addView(editText, layoutParams);
        if (leftDrawable != 0) {
            editText.setCompoundDrawablePadding(margin);
            editText.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), leftDrawable), null, null, null);
        }
        textView = new TextView(context);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(margin, 0, margin, 0);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));
        addView(textView, lp);
        textView.setText(getCountText(0));
        editText.setLines(lines);
        if (lines == 1) {
            editText.setGravity(Gravity.CENTER_VERTICAL);
        } else {
            editText.setGravity(Gravity.TOP);//EditText多行
            setViewOrientation(LinearLayout.VERTICAL);
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(getCountText(s.length()));
            }
        });

    }

    public String getInputText() {
        return editText.getText().toString();
    }

    public void clearText() {
        editText.setText("");
    }

    private CharSequence getCountText(int length) {
        String text = length + "/" + maxCount;
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        int indexOf = text.indexOf("/");
        stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#11ae7b")), 0, indexOf, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }

    /**
     * 设置最大字符数
     *
     * @param maxCount 最大字符数
     */

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});//不然setMaxCount无效
        textView.setText(getCountText(getInputText().length()));
    }

    /**
     * 设置行数
     *
     * @param lines 行数
     */
    public void setLines(int lines) {
        editText.setLines(lines);
        if (lines == 1) {
            editText.setSingleLine();
            editText.setGravity(Gravity.CENTER_VERTICAL);//EditText多行
        } else {
            editText.setGravity(Gravity.TOP);
            setViewOrientation(LinearLayout.VERTICAL);
        }
    }

    /**
     * 设置最大行数
     *
     * @param lines 最大行数
     */
    public void setMaxLines(int lines) {
        editText.setMaxLines(lines);
    }


    /**
     * 设置字体颜色
     *
     * @param textColor 字体颜色
     */
    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        editText.setTextColor(textColor);
    }

    /**
     * 设置hint颜色
     *
     * @param hintTextColor hint 颜色
     */
    public void setHintTextColor(@ColorInt int hintTextColor) {
        editText.setHintTextColor(hintTextColor);
    }

    public String getHintText() {
        return hintText;
    }

    /**
     * 设置hint text
     *
     * @param hintText hint text
     */
    public void setHintText(String hintText) {
        this.hintText = hintText;
        editText.setHint(hintText);
    }

    public void setText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            editText.setText("");
            return;
        }
        editText.setText(charSequence.length() > maxCount ? charSequence.subSequence(0, maxCount) : charSequence);
        editText.setSelection(editText.getText().length());
    }

    /**
     * editText有多行的时候,垂直方向布局
     *
     * @param orientation 方向
     */
    public void setViewOrientation(int orientation) {
        setOrientation(orientation);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int margin = this.getResources().getDimensionPixelOffset(R.dimen.view_space_small);
        lp.setMargins(0, 0, margin, margin);
        lp.gravity = Gravity.END;
        textView.setLayoutParams(lp);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        editText.setOnFocusChangeListener(l);
    }

}
