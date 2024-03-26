package cn.eqxiu.mock.common;


import com.alibaba.fastjson.JSONObject;

/**
 * 结果集包装类
 * @author will
 */
public class Result extends JSONObject {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CODE = "code";
    private static final String KEY_MSG = "msg";
    private static final String KEY_OBJ = "obj";

    public Result(boolean success, String code, String msg) {
        this.put(KEY_SUCCESS,success);
        this.put(KEY_CODE,code);
        this.put(KEY_MSG,msg);
    }

    public static Result ofSuccess(){
        return  new Result(true,"200","success");
    }

    public static Result ofSuccess(String msg){
        return  new Result(true,"200",msg);
    }

    public  static Result ofFail(String msg){
        return new Result(false,"500",msg);
    }

    public  static Result ofFail(String code, String msg){
        return new Result(false,code,msg);
    }

    public boolean isSuccess() {
        return this.getBoolean(KEY_SUCCESS);
    }

    public String getCode() {
        return this.getString(KEY_CODE);
    }

    public String getMsg() {
        return this.getString(KEY_MSG);
    }

    public Result setObj(Object obj) {
        this.put(KEY_OBJ,obj);
        return this;
    }
}
