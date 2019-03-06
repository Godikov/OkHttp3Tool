# OkHttp3Tool
OkHttp3请求工具类。

个人封装的一个方便工作使用的OkHttp3轮子，目前可以使用post/get请求，包括文件/多文件上传。

集成方式：
    Step 1. Add the JitPack repository to your build file
    Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
  
    Step 2. Add the dependency:
    
    dependencies {
        ...
        implementation 'com.github.Godikov:OkHttp3Tool:v1.1.0'
    }
    
    
请求示例：
创建一个OkHttpInterface类，并在其中新增方法：
    
        public static void postTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
            OkHttpParams params = new OkHttpParams();
            //params.setGetRequest(); 如果是get请求，加上这句话
            params.setUrl(TEST_URL);
            params.add("key",appKey);
            params.add("type",type);
            OkHttpUtil.getInstance().request(params,callback);
        }
        
获取回调结果示例:

    
        OkHttpInterface.postTest("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                //接口回调成功
                //bodyStr: 返回的Json格式数据
                //actModel: json转换成的对象
                Log.d(TAG,bodyStr);
            }

            @Override
            public void onTimeOut() {
                super.onTimeOut();
                //连接超时
            }

            @Override
            public void onConnectFail() {
                super.onConnectFail();
                //连接失败
            }

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                //接口请求失败
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //接口请求结束回调
            }
        });



    
用法介绍：

    定义请求参数对象：
    
        OkHttpParams params = new OkHttpParams();
        
    设置请求路径:
    
        params.setUrl(TEST_URL);
        
    设置请求方式：
   
        get请求：params.setGetRequest();
        post请求：params.setPostRequest(); （默认使用post请求）
        
    添加请求参数：
    
        params.add("key",appKey);
        
    添加文件参数（File）:
    
        params.putFile(tag,file);
        
    添加文件列表（List<MultiFile>) MultiFile: 自定义的File,用于请求key和文件对应）：
    
        params.putMultiFiles(files);
            
    发送请求：
    
        OkHttpUtil.getInstance().request(params,callback);
  
    请求回调（即上述发送请求时传的第二个参数 callback ）：
    
        OkHttpResponseCallback<BaseActModel> callback  BaseActModel为json格式String转对象之后的结果类  
        
        
  
  
