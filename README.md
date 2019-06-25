# 封装netty 4.x版本
该jar工程封装了netty 4.x版本，实现即插即用功能。

## 包类名说明
`com.jf.crawl.connect` 服务端及客户端引导连接
`com.jf.crawl.codec` 封装实体对象编解码，继承`DefaultMessage`即可自定义具体对象
`com.jf.crawl.handler` 自定义handler，继承`DefaultObjectHandler`即可自定义实现具体业务
`com.jf.crawl.thread.ChannelReconnectThread` channel重连


# 使用示例
1. `src/test/java/com.jf.crawl.demo.PeopleMessage` 传递的对象定义
   `src/test/java/com.jf.crawl.demo.PeopleMessageHandler` 实现的业务

2. `src/test/java/com.jf.crawl.TestServer` 服务端使用示例
   `src/test/java/com.jf.crawl.TestClient` 客户端使用示例
