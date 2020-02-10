package ru.reimu.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.MessageFormat;

public class Demo {

    public static void main(String[] args) {
        String broker = "tcp://47.102.121.60:1883";
        String clientId = "JavaSample";

        //use the memory persistence
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            System.out.println("connecting to broker");

            client.connect(connectOptions);

            System.out.println("connected");

            /**
             * subscribe
             */
            String topic = "demo/topics";
            System.out.println("subscribe to topic: " + topic);

            client.subscribe(topic);

            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable throwable) {

                }

                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    String msg = MessageFormat.format("msg: {0}, topic: {1}", mqttMessage.toString(), topic);
                    System.out.println(msg);
                }

                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

            /**
             * publish
             */
            String pubMsg = "message from MqttPublish - java";
            int qos = 2;
            System.out.println("[Publish] publish message: " + pubMsg);
            MqttMessage pubMsgByte = new MqttMessage(pubMsg.getBytes());
            pubMsgByte.setQos(qos);
            client.publish(topic, pubMsgByte);
            System.out.println("[Publish] Message published");
        } catch (MqttException e) {
            System.out.println("reason:" + e.getReasonCode());
            System.out.println("msg:" + e.getMessage());
            System.out.println("loc:" + e.getLocalizedMessage());
            System.out.println("cause:" + e.getCause());
            System.out.println("excep:" + e);
        }
    }
}
