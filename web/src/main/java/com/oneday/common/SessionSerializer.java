package com.oneday.common;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Created by chendeer on 2017-12-05.
 */
public class SessionSerializer implements RedisSerializer<Object> {

    static private Logger logger = LoggerFactory.getLogger(SessionSerializer.class);

    private byte[] EMPTY_BYTES = new byte[0];

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null){
            return EMPTY_BYTES;
        }
        Class clz = o.getClass();
        String name = clz.getCanonicalName();

        String value = JSON.toJSONString(o);
        return (name + "|" + value).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null){
            return null;
        }

        String str = new String(bytes);
        if (str.equals("")){
            return null;
        }

        String[] parts = str.split("\\|", 2);
        String clzName = parts[0];
        String value = parts[1];

        try {
            Class<?> clz = Class.forName(clzName);
            return JSON.parseObject(value,clz);
        } catch (ClassNotFoundException e) {//由于是会话共享，所以会话里面会有其他系统的对象，直接忽略
            return null;
        }
    }
}
