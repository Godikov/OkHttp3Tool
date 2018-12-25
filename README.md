# OkHttp3Tool
OkHttp3请求工具类。

v1.1

个人开发中为了方便写的OkHttp3请求工具，目前支持post/get请求。

接入方式：

allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
  
  并在dependencies中添加以下代码：
  
    implementation 'com.github.Godikov:OkHttp3Tool:1.1'
    
    
  使用方式，举例：
    
    //接口测试路径，为聚合数据新闻头条免费路径。
    private static final String TEST_URL = "http://v.juhe.cn/toutiao/index";

    //post请求测试
    public static void postTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        params.put("key",appKey);
        if(!TextUtils.isEmpty(type)){
            params.put("type",type);
        }
        OkHttpUtil.getInstance().post(params,callback);
    }

    //get请求测试
    public static void getTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        params.addParam("key",appKey);
        if(!TextUtils.isEmpty(type)){
            params.addParam("type",type);
        }
        OkHttpUtil.getInstance().get(params,callback);
    }
    
    之后在MainActivity中：
    
        OkHttpInterface.postTest("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                //接口回调成功
                //bodyStr: 返回的Json格式数据
                //actModel: json转换成的对象
            }

            @Override
            public void finish() {
                super.finish();
                //失败回调
            }
        });
        
        
OkHttpParams是设置请求参数的对象:    OkHttpParams params = new OkHttpParams(); //新建一个OkHttpParams对象

params.setUrl(url); //设置请求路径

post请求设置参数： params.put(key,value);

get请求设置参数： params.addParam(key,value);

post发送请求： OkHttpUtil.getInstance().post(params,callback);

get发送请求： OkHttpUtil.getInstance().get(params,callback);

OkHttpResponseCallback<BaseActModel> callback： 请求结果回调对象,BaseActModel为json数据转换为对象之后的对象类。
  

