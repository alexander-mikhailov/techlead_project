### How to
Run 2+ instances of import service and check logs to see processing-service routes requests.

### Issues

* Faced the problem of routing a request to a specific host within the bound requests from processing-service to import-service, since acknowledge the request should be sent to the service from which the read request came from.
  I solved this by creating a semblance of conversation within the client, which saves the server instance for the current thread when executing a read request and then uses this server instance to send subsequent requests to it,
  so load balancing is used only for a read request, and acknowledge the request is already going to a specific host stored in memory earlier.

* When adding a new instance of import-service, if we use existing library implementations, it is necessary to change the configuration of the balancer (listOfServers) and redeploy the processing-service if you want to use the added instance of import-service, since changes are not automatically visible to the balancer. I think it would be possible to create a new implementation of configuration reader to automatically pull up configuration changes.



