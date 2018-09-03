/**    
 * @Title: ClientVerticle.java  
 * @Package com.bicon.botu.server  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author yanhewei@vv.cc.com    
 * @date 2018年9月3日 下午9:34:15  
 * @version V1.0    
*/
package com.bicon.botu.server;


import java.util.Properties;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.SendContext;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**  
 * @ClassName: ClientVerticle  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author yanhewei@vv.cc.com  
 * @date 2018年9月3日 下午9:34:15  
 *    
*/
public class ClientVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {
		System.out.println("MainVerticle startting in thread: " + Thread.currentThread().getName());
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
				HttpServer server = vertx.createHttpServer();
				Router route = Router.router(vertx);
				EventBus eb = vertx.eventBus();
				
				route.get("/hello").handler(
						
						new Handler<RoutingContext>() {
					
					@Override
					public void handle(RoutingContext event) {
						
						eb.send("Test", "Hello",new Handler<AsyncResult<Message<String>>>() {

							@Override
							public void handle(AsyncResult<Message<String>> result) {
								if(result.succeeded()){
									event.response().end(Buffer.buffer(result.result().body()));
								}else{
									event.response().end(Buffer.buffer("failed"));
								}
							}
						});
					}
				}
						
						);
				server.requestHandler(route::accept);
				server.listen(8090);
			} else {
				// failed!
			}
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	public static void main(String[] args) {
		ClientVerticle mainVerticle = new ClientVerticle();
		try {
			mainVerticle.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
