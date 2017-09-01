package com.qhj.simplebanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 * 写这段代码的时候，只有上帝和我知道它是干嘛的
 * 现在，只有上帝知道
 * Created by Coder·Qin on 2017/8/18.
 */
public class SimpleBanner extends ViewGroup implements View.OnTouchListener {
    private Context context;
    private ViewFlipper vf;
    private LinearLayout llDot;
    private RelativeLayout rlTitle;
    private TextView tvTitle;
    private TextView tvTitleCurrentNum;
    private TextView tvTitleTotalNum;
    private float x1;
    private float x2;
    private float x3;
    private float y1;
    private float y2;
    private int childWidth;
    private int childHeight;
    private List<ImageView> dots;
    private List<String> titles;
    private int interval;//间隔时间
    private boolean showNext;//是否显示下一个view，此标识符是为了防止用户手势滑动一段距离又返回一段距离的引发的bug
    private OnItemClickListener onItemClickListener;
    private Animation.AnimationListener listener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            for (int i = 0; i <dots.size() ; i++) {
                if (i==vf.getDisplayedChild()){
                    if (titles!=null&&titles.size()>=dots.size()){
                        tvTitle.setText(titles.get(i));
                        tvTitleCurrentNum.setText((i+1)+"");
                    }else {
                        dots.get(i).setBackgroundResource(R.drawable.dot_red);
                        rlTitle.setVisibility(GONE);
                        llDot.setVisibility(VISIBLE);
                    }
                }else {
                    dots.get(i).setBackgroundResource(R.drawable.dot_gray);
                }
            }
            setFromRightToLeftAnim();
            vf.getInAnimation().setAnimationListener(listener);
        }
        @Override
        public void onAnimationEnd(Animation animation) {}
        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    public SimpleBanner(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SimpleBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleBanner);
        interval = typedArray.getInteger(R.styleable.SimpleBanner_interval, 3000);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_simple_banner,this,true);
        dots = new ArrayList<>();
        vf = (ViewFlipper) findViewById(R.id.vf);
        llDot = (LinearLayout) findViewById(R.id.ll_dot);
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitleCurrentNum = (TextView) findViewById(R.id.tv_title_current_num);
        tvTitleTotalNum = (TextView) findViewById(R.id.tv_title_total_num);
        vf.setAutoStart(true);
        vf.setFlipInterval(interval);
        setFromRightToLeftAnim();
        vf.setOnTouchListener(this);
        vf.getInAnimation().setAnimationListener(listener);
    }

    public void addImgTitle(List<String> imgTitles){
        titles = new ArrayList<>();
        if (imgTitles.size()>0){
            titles.addAll(imgTitles);
            tvTitle.setText(titles.get(0));
            tvTitleCurrentNum.setText("1");
            tvTitleTotalNum.setText(titles.size()+"");
            rlTitle.setVisibility(VISIBLE);
            llDot.setVisibility(GONE);
        }
    }

    public void addImgUrl(List<String> urls){
        if (urls!=null&&urls.size()>0){
            if (dots.size()>0){
                dots.clear();
                llDot.removeAllViews();
            }
            for (int i = 0; i < urls.size(); i++) {
                ImageView iv=new ImageView(context);
                ImageView dot=new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(2,0,2,0);
                dot.setLayoutParams(params);
                if (i==0){
                    dot.setBackgroundResource(R.drawable.dot_red);
                }else {
                    dot.setBackgroundResource(R.drawable.dot_gray);
                }
                llDot.addView(dot);
                dots.add(dot);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(urls.get(i)).placeholder(R.drawable.android).centerCrop().into(iv);
                vf.addView(iv);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }

    private void setFromRightToLeftAnim(){
        vf.setInAnimation(context, R.anim.from_right_to_left_anim_in);
        vf.setOutAnimation(context,R.anim.from_right_to_left_anim_out);
    }

    private void setFromLeftToRightAnim(){
        vf.setInAnimation(context,R.anim.from_left_to_right_anim_in);
        vf.setOutAnimation(context,R.anim.from_left_to_right_anim_out);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(0);
        measureChild(child,widthMeasureSpec,heightMeasureSpec);
        childWidth = child.getMeasuredWidth();
        childHeight = child.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getChildAt(0).layout(0,0,childWidth,childHeight);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                x3=0.0f;
                showNext=false;
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                if (x1-x2<-300||x1-x2>300){
                    vf.stopFlipping();
                    showNext=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                x3 = event.getX();
                y2 = event.getY();
                if (x1-x3<-300){
                    setFromLeftToRightAnim();
                    vf.showPrevious();
                    vf.getInAnimation().setAnimationListener(listener);
                }else if(x1-x3>300){
                    vf.showNext();
                }else if (showNext){
                    vf.showNext();
                }
                vf.startFlipping();
                break;
        }
        if (x3!=0.0f){
            if (x1-x3<20.0f&&x1-x3>-20.0f&&y1-y2<20.0f&&y1-y2>-20.0f){
                if (showNext){
                    return false;
                }else {
                    if (onItemClickListener!=null){
                        onItemClickListener.onClick(vf.getDisplayedChild());
                    }
                    return true;
                }
            }else {
                return true;
            }
        }else {
            return true;
        }
    }
}
