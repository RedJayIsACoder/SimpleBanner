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
	             compile 'com.github.RedJayIsACoder:SimpleRefreshListView:1.3.0'
           }
           
  #### 三、控件属性:<br/>
           1、interval: 自动播放的时间间隔，默认值为3000
