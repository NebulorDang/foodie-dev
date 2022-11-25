## ElasticSearch

#### 搜索方式

query string的方式

![image-20221122170535600](image/image-20221122170535600.png)

DLS（Domain specific language）的方式需要使用 json object

![image-20221122173025629](image/image-20221122173025629.png)

![image-20221122173237746](image/image-20221122173237746.png)

#### 集群文档写原理

![image-20221123132552598](image/image-20221123132552598.png)

#### 文档读原理

![image-20221123132653003](image/image-20221123132653003.png)

#### 集群部署

```shell
# 1.将elasticsearch-analysis-ik-6.4.3.zip解压到plugins目录下

# 2.直接启动
docker-compose up -d
```

![image-20221123224334647](image/image-20221123224334647.png)

