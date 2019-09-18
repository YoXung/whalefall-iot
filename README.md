# Whale Fall IoT

### 安装依赖软件
* *MongoDB*

    MongoDB是一个基于分布式文件存储的数据库，我们会把MongoDB作为物联网平台主要的数据存储工具。[传送门](https://www.mongodb.com/download-center/community)

* *Redis*

    Redis是一个高效的内存数据库，物联网平台会使用Redis来实现缓存和简单的队列功能。[传送门](https://redis.io/download)

* *RabbitMQ*

    RabbitMQ是使用Erlang编写的AMQP Broker，物联网平台使用RabbitMQ作为队列系统实现物联网平台内部以及物联网平台到业务系统的异步通信。[传送门](https://www.rabbitmq.com/#getstarted)
    
* *EMQ X*

    EMQ X是一个使用Erlang编写的MQTT Broker，物联网平台使用EMQ X来实现MQTT/CoAP协议接入，并使用EMQ X的一些高级功能来简化和加速开发。[传送门](https://docs.emqx.io/broker/v3/cn/)
