### How to
Run 2+ instances of import-service and check logs to see processing-service routes requests.

### Issues

* Faced the problem of routing a request to a specific host within the bound requests from processing-service to import-service, since acknowledge the request should be sent to the service from which the read request came from.
  I solved this by creating a semblance of conversation within the client, which saves the server instance for the current thread when executing a read request and then uses this server instance to send subsequent requests to it,
  so load balancing is used only for a read request, and acknowledge the request is already going to a specific host stored in memory earlier.
  
### Adv
* Dynamic registration of new instances of a service without necessity to change existing configuration.  



