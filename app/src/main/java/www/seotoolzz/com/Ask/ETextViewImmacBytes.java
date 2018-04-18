package www.seotoolzz.com.Ask;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class ETextViewImmacBytes extends android.support.v7.widget.AppCompatEditText {

    public ETextViewImmacBytes(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ETextViewImmacBytes(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ETextViewImmacBytes(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/NexaLight.otf");
            setTypeface(tf);
        }
    }

}
