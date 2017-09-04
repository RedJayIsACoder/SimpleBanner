# How To Use:
  #### 一、在项目的build.gradle文件下添加以下代码:
           allprojects {
               repositories {
                   jcenter()
                   maven { url "https://jitpack.io" }
               }
           } 
  #### 二、在module的build.gradle文件下添加以下代码:
           dependencies {
	      	   compile 'com.github.RedJayIsACoder:SimpleBanner:1.6.1'
           }
          
  #### 三、控件属性:
	  <b>1、interval:</b> 自动播放的时间间隔，默认值为3000
>>>![控件属性](https://github.com/RedJayIsACoder/SimpleBanner/blob/master/image/SimpleBanner01.jpg)


#### 四、API:
  	  1、addImgUrl(List<String> urls,int defaultImgResId)：
	     需要传入一个包含图片地址的string集合以及默认图片的资源id，如不需要展示默认图片，defaultImgResId传0即可。
	     
	  2、addImgTitle(List<String> imgTitles)：
	     调用此方法会在控件的底部展示标题栏，并以数字索引（例：1/3）的方式替换默认的圆点索引。
	     如无需展示标题栏可不调用，将以圆点形式展示索引。
	     
>>>![数字索引](https://github.com/RedJayIsACoder/SimpleBanner/blob/master/image/SimpleBanner02.jpg)
>>>![圆点索引](https://github.com/RedJayIsACoder/SimpleBanner/blob/master/image/SimpleBanner03.jpg)
	     
	  3、setOnItemClickListener(OnItemClickListener listener)：设置item点击事件
           
