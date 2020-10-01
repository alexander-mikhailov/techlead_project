# Technical Leadership Project
![Technical Leadership Project schema](https://github.com/alexander-mikhailov/techlead_project/blob/master/techlead_project.png)


Below are implementations of second and third solutions from the schema above which was designed for a practical project during participation at Technical Leadership training.

## solution1-v1 (RestTemplate + Ribbon)
A solution which is implemented using spring RestTemplate http client and Ribbon cline side balancer.

### How to
* Run 2+ instances of "import-service".
* Run 1+ instances of "processing-service".
* Check in a console "processing-service" routes requests.

### Issues
* Faced the problem of routing a request to a specific host within the bound requests from "processing-service" to "import-service", since acknowledgement of the request should be sent to a corresponding "import-service" from which the read request came from.
  The issue was solved by creating a semblance of conversation within the client, which saves the server instance for the current thread when executing a read request and then uses this server instance to send subsequent requests to it, so load balancing is used for a read request only, and acknowledge of request is already going to a specific host stored in memory earlier.
* If you want to add new instances of "import-service" and take them into work then it is necessary to change a configuration of the balancer (listOfServers) and redeploy the processing-service, since new instances of "import-service" are not automatically visible to the balancer with existing implementations of configuration reader. I beleive it would be possible to create a new implementation of configuration reader to automatically pull up configuration changes but that is out of the project scope.

## solution1-v2 (RestTemplate + Eureka)
A solution which is implemented using spring RestTemplate http client and Eureka discovery server/client.

### How to
* Run "discovery-service".
* Apply How to steps from solution1-v1.

### Issues
See solution1-v1 issues.
  
### Advantages
* Dynamic registration of new instances of a service without necessity to change existing configuration and to redeploy the service.

## solution1-v3 (RestTemplate + Eureka + Cloud Config Server)
A solution which is implemented using spring RestTemplate http client, Eureka discovery server/client and Spring Cloud Config server/client.

### How to
* Run "config-service".
* Apply How to steps from solution1-v2.

### Issues
See solution1-v2 issues.
  
### Advantages
* See solution1-v2 advantages.
* Having a central place to manage external properties for applications across all environments and ability to reload properties on the fly using /resfresh spring boot actuator endpoint.

## solution1-v4 (RestTemplate + Eureka + Cloud Config Server + Jaeger)
A solution which is implemented using spring RestTemplate http client, Eureka discovery server/client, Sring Cloud Config server/client and Jaeger implementation for distibuted tracing.

### How to
* You can use Docker Compose to quickly launch a Jaeger server if you have Docker running locally (there is a docker-compose.yml in the root of the module) or you can install it locally and launch.
* Apply How to steps from solution1-v3.
* Open Jaeger UI console (http://localhost:16686/) to monitor the system metrics.

### Issues
See solution1-v3 issues.
  
### Advantages
* See solution1-v3 advantages.
* Having end-to-end distributed tracing (Jaeger in this case) allows us to monitor and troubleshoot transactions in complex distributed systems.

## solution2-v1 (RabbitMQ)
A solution which is implemented using RabbitMQ message broker.

### How to
* You can use Docker Compose to quickly launch a RabbitMQ server if you have Docker running locally (there is a docker-compose.yml in the root of the module) or you can install it locally and launch then.
* Run 1 instance of "import-service" with VM option -Dapp.labResultType=1 and 1 instance of "processing-service" with VM options -Dapp.labResultType=1 -Dapp.labResultProcessingTime=4000. Check RabbitMQ management console. You should not see more than 1 message in queue-lab-result-type-1 at any time.
* In additional run 1 instance of "import-service" with VM option -Dapp.labResultType=2 and 1 instance of "processing-service" with VM options -Dapp.labResultType=2 -Dapp.labResultProcessingTime=15000. Check RabbitMQ management console (http://localhost:15672/ - guest/guest). You should see "queue-lab-result-type-2" queue is gradually filling up with messages.
* To purge the "queue-lab-result-type-2" queue run 3+ instances of "processing-service" with VM options -Dapp.labResultType=2 -Dapp.labResultProcessingTime=15000. Check the RabbitMQ management console. You should see "queue-lab-result-type-2" queue is decreasing gradually.
  
### Advantages
* Absence of http inetegration layer.
* Visual monitoring tool (RabbitMQ management console).

## solution2-v2 (RabbitMQ + Jaeger)
A solution which is implemented using RabbitMQ message broker and Jaeger implementation for distibuted tracing.

### How to
* You can use Docker Compose to quickly launch a RabbitMQ and a Jaeger server if you have Docker running locally (there is a docker-compose.yml in the root of the module) or you can install them locally and launch then.
* Run 1 instance of "import-service" with VM option -Dapp.labResultType=1 and 1 instance of "processing-service" with VM options -Dapp.labResultType=1 -Dapp.labResultProcessingTime=4000.
* Open Jaeger UI console (http://localhost:16686/) to monitor the system metrics.
  
### Advantages
* See solution2-v1 advantages.
* Having end-to-end distributed tracing (Jaeger in this case) allows us to monitor and troubleshoot transactions in complex distributed systems.

