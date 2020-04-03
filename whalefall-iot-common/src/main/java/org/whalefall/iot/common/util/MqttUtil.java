package org.whalefall.iot.common.util;


import lombok.Data;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;

/**
 * Copyright © 2020 Whale Fall All Rights Reserved
 *
 * @author YaoXiang
 * @description
 * @create 2020/3/26 5:23 下午
 */

@Data
public class MqttUtil {
    @Value("${emqx.broker_url}")
    private static String broker;
    @Value("${emqx.client_id}")
    private static String clientid;

    @Getter
    private MqttClient mqttClient;
    private String topic;
    private int qos = 1;
    private String username;
    private String password;
    private String content;

    public MqttUtil() throws MqttException {
        mqttClient = new MqttClient(broker, clientid, new MemoryPersistence());
        connect();
    }

    private void connect() {
        // 创建链接参数
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        mqttConnectOptions.setCleanSession(false);
        // 设置连接的用户名
        mqttConnectOptions.setUserName(username);
        // 设置连接的密码
        mqttConnectOptions.setPassword(password.toCharArray());
        // 设置超时时间 单位为秒
        mqttConnectOptions.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        mqttConnectOptions.setKeepAliveInterval(20);
        try {
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("connectionLost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("topic:" + topic);
                    System.out.println("Qos:" + mqttMessage.getQos());
                    System.out.println("message content:" + new String(mqttMessage.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
                }
            });
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public void publish(String topic, String content) throws MqttException, MqttPersistenceException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        // mqtt服务器保留消息
        mqttMessage.setRetained(true);
        mqttMessage.setPayload(content.getBytes());
        try {
            mqttClient.publish(topic, mqttMessage);

        } catch (MqttException me) {
            me.printStackTrace();
        } finally {
            if (mqttClient != null) {
                mqttClient.disconnect();
                mqttClient.close();
            }
        }
    }

    public void subscribe(String topic, int qos) {
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
