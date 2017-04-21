本项目是鉴于目前开源项目对于6.0及其以上动态权限没有支持非Activity模块申请权限的支持，为便于开发而写的，
欢迎大家拍砖，扔花！
目前第一版还有很多不足，以后有时间在慢慢优化，增加新功能！
本项目类似于代码生成器，由于仓促，很多地方没有考虑好，希望大家指正，欢迎开启issue来讨论
     
先上图（虽然也没有必要图，但大家喜欢就好）
![image](https://github.com/YoneHsiung/permissiondispatcher/blob/master/anim.gif)

**`重要的事情说三次
支持任意地方检测权限，任意地方检测权限，检测权限`**
**使用项目非常简单，步骤如下:**


1.打开项目根目录的build.gradle, 加入如下代码

	    dependencies {
	    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
	    }
2.打开项目目录的build.gradle 

	    dependencies {
	        compile 'com.xy.open.opensource:permission:latest.integration'
	    }
 3.rebuild 项目
    
     开始写自己的权限相关的配置 -->
    
4.编写BaseActivity基类，所有的activity都继承BaseActivity

	     public abstract  class BaseActivity extends Activity {
    	    private Dispatch dispatcher;
    	
    	    @Override
    	    protected void onCreate(@Nullable Bundle savedInstanceState) {
    	        super.onCreate(savedInstanceState);
    	        dispatcher = Dispatcher.instants.getDispatcher();
    	        dispatcher.register(this);
    	        
    	    }
    	
    	    @Override
    	    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    	        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    	        dispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    	    }
    	}
5.在BaseActivity或者Application类里面初始化权限申请总线类

	BaseActivity
   	    @Override
    	    protected void onCreate(@Nullable Bundle savedInstanceState) {
    	        super.onCreate(savedInstanceState);
    	        dispatcher = Dispatcher.instants.getDispatcher();
    	        dispatcher.register(this);
    	        new RequestPermissionBus();
    	    }
	Application   
	
	   @Override
	    public void onCreate() {
	        super.onCreate();
	        new RequestPermissionBus();
	    }
	    
6.编写权限请求方法	RequestPermissionBus.class

	public class RequestPermissionBus {
	
	    @RequestPermissions(permissions = {Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_FINE_LOCATION},
	            grantCode = 20000,denyMessage = "拒绝Test的权限应用将无法运行，很有可能死机哦",invoke = true)
	    public final int test(int i, String m, Activity activity, GrantListener listener) throws Exception {
	        Log.d("TAG_TEST", "test M = " + m + " i = " + i + "object = " + activity);
	        return i;
	    }
	
	    @RequestPermissions(permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, grantCode = 20001,
	            denyMessage = "拒绝Test1的CALL_PHONE权限应用将无法运行，很有可能爆炸哦",invoke = false
	    )
	    public void test1(int i1, String m1, Object object1) throws RuntimeException, Exception {
	        Log.d("TAG_TEST", "test1 M = " + m1 + " i = " + i1 + "object = " + object1);
	    }
	
	
	}
	

7.rebuild project

8.最后一步请求权限（备注：在需要请求权限的函数前请求权限，暂时不支持在请求权限的函数内直接处理逻辑，需要在回调中处理）

	   public void request(View view) throws Exception {
	        PermissionCheck.getInstants().checkTestWithPermissions(45, "test", this, new GrantListener<Integer>() {
	            @Override
	            public void onPromise(boolean promise, Integer result) {
	            //这里是回调，promise是否授权，result如果选择回调权限申请方法，result是返回值，否则result为空
	                System.out.print(promise + " :::::  "+result);
	                tv.setText("promise = " + promise + " , result =  "+result);
	            }
	        });
	    }
	
	    public void requests(View view) throws Exception {
	        PermissionCheck.getInstants().checkTest1WithPermissions(2, "test1", new Object());
	
	        PermissionCheck.getInstants().checkTest3WithPermissions(22, "test3", 111);
	    }
 
是不是很简单，so easy！ 


**关于项目**

>关于权限请求方法说明：
>
  	 	当你写好权限请求方法后，rebuild项目，会自动帮你生成真正的请求方法，方法名为 check<yourmethod>Permissions
>关于权限请求函数参数:

		     boolean invoke default true; //是否回调权限请求的方法 比如,checkTest1WithPermissions ---> Test1
		     
		     String[] permissions default "";//需要申请的权限，可变参数，数组类型
		    
		     int grantCode default -1；//请求权限是的requestCode
		      
		     String denyMessage;//权限被拒绝弹出的dialog的内容 
	     

>关于回调方法
 
		     new GrantListener<T>() {
			            @Override
			            public void onPromise(boolean promise, T result) {
			            //这里是回调，promise是否授权，result如果选择回调权限申请方法，result是返回值，否则result为空
			                System.out.print(promise + " :::::  "+result);
			                tv.setText("promise = " + promise + " , result =  "+result);
			            }
			        }
			        
			T 泛型，为权限检测方法的返回值类型，    
			boolean promise  是否授权			

>extra -> Permissions

    这里有一个已经写好的Permission，如果需要，直接拷贝到自己的项目中，rebuild就可以不用再配置多余的权限了

**License**

    Copyright 2017 Yone Hsiung
    
   
       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at
    
           http://www.apache.org/licenses/LICENSE-2.0
    
       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.
