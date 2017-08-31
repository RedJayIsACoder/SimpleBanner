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
	      	   compile 'com.github.RedJayIsACoder:SimpleBanner:1.3.1'
           }
          
  #### 三、控件属性:
	  1、interval: 自动播放的时间间隔，默认值为3000
 #### 四、API:
  	   1、addImgUrl(urls)：需要传入一个包含图片地址的string集合
	   2、setOnItemClickListener：设置item点击事件
           
