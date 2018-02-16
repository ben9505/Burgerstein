package theagency.vn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import theagency.vn.burgerstein.R;
import theagency.vn.factory.AppFonts;

/**
 * Created by ben on 16/02/2018.
 */

public class AppButton extends android.support.v7.widget.AppCompatButton {
    public AppButton(Context context) {
        super(context);
        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), AppFonts.FONT_REGULAR);
        setTypeface(myTypeface);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (!isInEditMode()) {
            initFont(attrs);
        }
    }

    private void initFont(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AppFonts);
            if (a.getString(R.styleable.AppFonts_fonts) != null) {
                String fontName = AppFonts.fonts[Integer.valueOf(a.getString(R.styleable.AppFonts_fonts))];

                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
                    setTypeface(myTypeface);
                }
                a.recycle();
            }
        }
    }
}
