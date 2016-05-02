package com.jinpalhawang.jambudvipa;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountMiddleApplication {

  private static final Logger log = LoggerFactory.getLogger(AccountMiddleApplication.class);

  public static void main(String[] args) {
    final SpringApplication app = new SpringApplication(AccountMiddleApplication.class);
    app.setLogStartupInfo(false);
    app.run(args);
    log.info(AccountMiddleApplication.class.getSimpleName() + " started.");
  }

}

@RestController
@RefreshScope
class AccountMiddleRestController {

  private static final Logger log = LoggerFactory.getLogger(AccountMiddleRestController.class);

  @Autowired
  private DiscoveryClient discoveryClient;

  @Value("${helloWorld}")
  private String helloWorld;

  @RequestMapping("/")
  public String helloWorld() {
    log.info(helloWorld);
    return helloWorld;
  }

  @RequestMapping("/service-instances/{applicationName}")
  public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
    return this.discoveryClient.getInstances(applicationName);
  }

}
