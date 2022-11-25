docker run -it --name mylogstash \
-v /home/hy/foodie/logstash/config:/usr/share/logstash/config \
-v /home/hy/foodie/logstash/data:/usr/share/logstash/data:rw \
-v /home/hy/foodie/logstash/pipeline:/usr/share/logstash/pipeline \
-v /home/hy/foodie/logstash/sync:/usr/local/logstash/sync:rw \
--privileged=true \
-d docker.elastic.co/logstash/logstash:6.4.3
