/**    
 * @Title: ServiceVerticle.java  
 * @Package com.bicon.botu.server  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author yanhewei@vv.cc.com    
 * @date 2018年9月3日 下午9:28:27  
 * @version V1.0    
*/
package com.bicon.botu.server;

import java.util.Properties;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**  
 * @ClassName: ServiceVerticle  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author yanhewei@vv.cc.com  
 * @date 2018年9月3日 下午9:28:27  
 *    
*/
public class ServiceVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {
		//这里为了做示范就直接写死在代码中了，实际运用可以从配置文件获取
		Properties zkConfig = new Properties();
		zkConfig.setProperty("hosts.zookeeper", "127.0.0.1");
		zkConfig.setProperty("path.root", "io.vertx");
		zkConfig.setProperty("retry.initialSleepTime", "1000");
		zkConfig.setProperty("retry.intervalTimes", "3");
		JsonObject json = JsonObject.mapFrom(zkConfig);
		ClusterManager mgr = new ZookeeperClusterManager(json);
		VertxOptions options = new VertxOptions().setClusterManager(mgr);
		Vertx.clusteredVertx(options, res -> {
			if (res.succeeded()) {
				vertx = res.result();
				
				EventBus eb = vertx.eventBus();
				eb.consumer("Test", new Handler<Message<String>>() {
					
					@Override
					public void handle(Message<String> event) {
						event.reply(event.body() +" . Hi");
					}
				});
			} else {
				// failed!
			}
		});
		
	}

	public static void main(String[] args) {
		ServiceVerticle mainVerticle = new ServiceVerticle();
		try {
			mainVerticle.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
