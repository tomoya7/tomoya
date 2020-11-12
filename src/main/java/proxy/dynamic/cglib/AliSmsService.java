//package github.javaguide.proxy.dynamicProxy.cglibDynamicProxy;
package proxy.dynamic.cglib;

public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
