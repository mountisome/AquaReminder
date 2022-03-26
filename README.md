## Aqua Reminder

### 简要介绍

这是我们人机交互课程的大作业，任务是设计一个简单的app，要求符合人机交互目标。我们设计了一个喝水提醒app—Aqua Reminder，该app是模仿柠檬喝水app实现的。功能有**每日喝水量显示**，**喝水量添加**，**喝水提醒**，**种植树木**，**用户排名**等。我花了较长的时间学习Android方面的一些知识，在下面会介绍app中用到的一些知识。



### 功能介绍

- 每日喝水量显示：
    - 喝水的水杯中会显示今日的喝水量
    - 水杯中的水量不会超过1500ml
- 喝水量添加：
    - 喝完水后可以添加本次喝水量
    - 一天过去将会更新喝水量
- 喝水提醒：
    - 定时提醒：闹钟将会在规定的时间提醒
    - 间隔提醒：闹钟将在设置后每隔一小时提醒
- 种植树木：
    - 用户可以在种植界面兑换树木种子
    - 兑换后树木可以在已种植中查看
- 用户排名：
    - app将会按照能量多少将所有用户排名



### 主要页面展示

登录界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262206350.png"></div>

喝水界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208730.png"></div>

添加喝水量界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208649.png"></div>

提醒设置界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208697.png"></div>

种植树木界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208744.png"></div>

选择树木界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208973.png"></div>

已种植界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208072.png"></div>

用户界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208432.png"></div>

用户排名界面：

<div align=center><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262208053.png"></div>



### 技术简介

- ListView顶部和底部的分割线：

    ```java
    ListView lv;
    lv.addHeaderView(new View(this)); // 添加顶部分割线
    lv.addFooterView(new View(this)); // 添加底部分割线
    ```

- 返回上一级：

    ```java
    Activity.this.finish();
    ```

- 对话框：

    ```java
    AlertDialog alert = null;
    AlertDialog.Builder builder = null;
    
    alert = null;
    builder = new AlertDialog.Builder(Activity.this);
    // 标题+消息+按钮
    alert = builder.setTitle("...").setMessage("...").setNegativeButton("...", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
    
        }
    }).create();
    alert.show();
    ```

- 闹钟提醒：

    [Android官方文档](https://developer.android.google.cn/training/scheduling/alarms)

- 底部导航栏：

    [菜鸟教程](https://www.runoob.com/w3cnote/android-tutorial-fragment-demo1.html)

- 水杯水量动态变化：

    Histogram类：

    ```java
    public class Histogram extends View {
    
        int MAX = 1800; // 矩形显示的最大值
        int corner = 0; // 矩形的角度，设置为0则没有角度。
        double data = 0; // 显示的数
        double tempData = 0; // 初始数据
        int textPadding = 0; // 字体与矩形图的距离
        Paint mPaint;
        int mColor;
        Context mContext;
    
        //构造函数
        public Histogram(Context context) {
            super(context);
            mContext = context;
        }
    
        public Histogram(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            mContext = context;
            initPaint();
        }
    
        public Histogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mContext = context;
            initPaint();
        }
    
        // 画笔方法
        private void initPaint() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mColor = mContext.getResources().getColor(R.color.water);
            mPaint.setColor(mColor);
        }
    
        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (data == 0.0) {
                mPaint.setTextSize(getWidth() / 2);
                RectF oval3 = new RectF(0, getHeight() - DensityUtils.pxTodip(mContext,
                        20), getWidth(), getHeight()); // 设置个新的长方形
                canvas.drawRoundRect(oval3, DensityUtils.pxTodip(mContext, corner),
                        DensityUtils.pxTodip(mContext, corner), mPaint);
                canvas.drawText("",
                        getWidth() * 0.5f - mPaint.measureText("0") * 0.5f,
                        getHeight() - DensityUtils.pxTodip(mContext, 20) -
                                2 * DensityUtils.pxTodip(mContext, textPadding),
                        mPaint);
                return;
            }
    
            // 防止数值很大的的时候，动画时间过长
            int step = (int) (data / 100 + 1.0);
            if (tempData < data - step) {
                tempData = tempData + step;
            } else {
                tempData = data;
            }
            //画圆角矩形
            String S = tempData + ""; // 如果数字后面需要加% 则在""中添加%
            // 设置显示的字体
            // Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"digital-7.ttf");
            // mPaint.setTypeface(typeface);
            // 一个字和两,三个字的字号相同
            if (S.length() < 4) {
                mPaint.setTextSize(getWidth() / 2);
            } else {
                mPaint.setTextSize(50); // 可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
            }
    
            float textH = mPaint.ascent() + mPaint.descent();
            float MaxH = getHeight() - textH - 2 * DensityUtils.pxTodip(mContext, textPadding);
    
            // 圆角矩形的实际高度
            float realH = (float) (MaxH / MAX * tempData);
            RectF oval3 = new RectF(0, getHeight() - realH, getWidth(), getHeight()); // 设置个新的长方形
            canvas.drawRoundRect(oval3, DensityUtils.pxTodip(mContext, corner), DensityUtils.pxTodip(mContext, corner), mPaint);
            // 写数字
            canvas.drawText(S,
                    getWidth() * 0.5f - mPaint.measureText(S) * 0.5f,
                    getHeight() - realH - 2 * DensityUtils.pxTodip(mContext, textPadding),
                    mPaint);
    
            if (tempData != data) {
                postInvalidate();
            }
        }
    
        public void setData(double tempData, double data, int MAX) {
            this.tempData = tempData;
            this.data = data;
            this.MAX = MAX;
            postInvalidate();
        }
    
    }
    ```

    某个Activity或Fragment：

    ```java
    private Histogram histogram;
    
    histogram.setData(tempWater, water, MAX_WATER);
    ```

    **别忘了在页面中定义一个Histogram控件**

- SwitchCompat开关：

    类名：`androidx.appcompat.widget.SwitchCompat`

    <div align="center"><img src="https://mountisomeb1.oss-cn-shanghai.aliyuncs.com/img/202203262211257.jpg"></div>



### 存在的问题

app中使用了`BroadcastReceiver`来监听日期发生变化，但是一直出现了`onReceive`方法多次调用导致数据库中的数据更新有错误的问题，该问题一直没有解决，如果有解决的方法欢迎评论或者私信。



代码如下：

```java
dateChangeReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_DATE_CHANGED) && broadcastFlag) {
        broadcastFlag = false;
        updateUserInfo();
        flag = true;
    	}
    	else {
        broadcastFlag = true;
    	}
	}
};

IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
registerReceiver(dateChangeReceiver, intentFilter);
```



### 项目地址

[gitee地址](https://gitee.com/mountisome/AquaReminder)

[github地址](https://github.com/mountisome/AquaReminder)