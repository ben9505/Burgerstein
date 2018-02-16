package theagency.vn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import theagency.vn.burgerstein.R;
import theagency.vn.factory.AppFonts;

/**
 * Created by ben on 15/02/2018.
 */

public class AppTextView extends android.support.v7.widget.AppCompatTextView {

    public AppTextView(Context context) {
        super(context);
        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), AppFonts.FONT_REGULAR);
        setTypeface(myTypeface);
    }

    public AppTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
