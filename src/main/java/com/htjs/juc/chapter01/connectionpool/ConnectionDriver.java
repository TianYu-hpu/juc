package com.htjs.juc.chapter01.connectionpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class ConnectionDriver {

    static class ConnectionHandler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equalsIgnoreCase("commit")) {
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            return null;
        }
    }

    /**
     * jdk 动态代理
     * @return
     */
    public static final Connection createConnection() {
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(), new Class<?>[]{Connection.class}, new ConnectionHandler());
    }


}
