package common;

/**
 * 自定义缓存类
 * @author Huangfei
 *
 * 2017年5月4日 下午4:41:36
 */
public class Cache {
    private Object value;//缓存数据   
    private long timeOut;//更新时间   
    public Cache() {   
            super();   
    }   

    public Cache(Object value, long timeOut) {   
        this.value = value;   
        this.timeOut = timeOut;   
    }   

	public long getTimeOut() {   
            return timeOut;   
    }   

    public Object getValue() {   
            return value;   
    }   

    public void setTimeOut(long l) {   
            timeOut = l;   
    }   

    public void setValue(Object object) {   
            value = object;   
    }
    
}
