阿里datahub的一个包装的cli程序

### 使用方式:

- 当发送的数据不为null
指定`--data=D:\\file.txt`


```
java -jar ./target/datahub-cli-1.0-SNAPSHOT.jar    --topic=sdk_web_in   --project=test_1   --accessid={accessid}  --accesskey={accesskey}  --endpoint=https://dh-cn-hangzhou.aliyuncs.com  --shareid=0 --data=D:\\file.txt
```
- 当需要发送一个指为`null`的数据
指定`--data=`, 在data后不需要指定任何东西
```
java -jar ./target/datahub-cli-1.0-SNAPSHOT.jar    --topic=sdk_web_in   --project=test_1   --accessid={accessid}  --accesskey={accesskey}  --endpoint=https://dh-cn-hangzhou.aliyuncs.com  --shareid=0 --data=
```

### 参数说明
|参数|值|描述|
|---|---|---|
|topic|sdk_web_in|topic 就叫sdk_web_in或者自己建立的topic|
|accessid| fasdfasd |阿里RAM生成的accessid|
|accesskey|fasdfdfsdf|阿里RAM生成的accesskey|
|endpoint|https://dh-cn-hangzhou.aliyuncs.com|去阿里官网找https://help.aliyun.com/zh/datahub/developer-reference/compatibility-with-kafka/|
|data|需要传输的内容所在文件| window: `D:\\file.txt`   linux:   `/opt/file.txt`|
