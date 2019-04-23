package com.example.hieuhoang.now.CustomView;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.hieuhoang.now.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class PasswordEditText extends EditText implements TextWatcher {

    private Drawable eye, eyeStrike;
    private Boolean useStrike = false;
    private Boolean visible = false;
    private Boolean validate = false;
    private Drawable drawable;
    private int ALPHA = (int) (235 * 0.55f);
    private String MATCHER_PATTEN = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{6,20})";
    private Pattern pattern;
    private Matcher matcher;


    public PasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.pattern = Pattern.compile(MATCHER_PATTEN);
        if (attrs != null) {
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, 0, 0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike, false);
            this.validate = array.getBoolean(R.styleable.PasswordEditText_useValidate, false);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.baseline_eye_black_24dp).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(), R.drawable.eye_off).mutate();

        install();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (this.validate) {
            if (!focused) {
                TextInputLayout textInputLayout = (TextInputLayout) this.getParent().getParent();
                String string = getText().toString().trim();
                if (string.equals("")) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("");
                    return;
                }
                matcher = pattern.matcher(string);
                if (!matcher.matches()) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Mật khẩu phải ít nhất 6 kí tự, số và 1 kí tự hoa");
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        }
    }

    private void install() {
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        drawable = useStrike && !visible ? eyeStrike : eye;
        drawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && event.getX() >= (getRight() - drawable.getBounds().width())) {
            visible = !visible;
            install();
            invalidate();
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        TextInputLayout textInputLayout;
        if (this.getParent() == null)
            return;
        textInputLayout = (TextInputLayout) this.getParent().getParent();
        if (textInputLayout.getError() == null)
            return;
        matcher = pattern.matcher(s.toString().trim());
        if (matcher.matches()) {
            textInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
