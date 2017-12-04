# TenderCare-backend
* **Description:**

A java backend for https://github.com/ImenGharsalli/TenderCare as a Spring boot project written in java8.
This is a hypermedia-driven rest API that accepts and responds in json and offers services to handle users, jobs and carServices.
We will have an overview of the different features of this API including the provided services, metrics and API documentation.

### Prerequisites:

* **Java 8 installation:**
$sudo add-apt-repository ppa:webupd8team/java
$sudo apt-get update
$sudo apt-get install oracle-java8-installer

### Calling the services:
In order to be able to handle jobs user and careServices, the following services are provided: JobRestController, CareServiceRestControlle, UserRestController.Example:
 * **UserRestController**
	* **Creating a new user**
		http://localhost:8080/api/users

![alt text](https://github.com/ImenGharsalli/TenderCare-backend/blob/master/demo/get.png)

	* **Updating an existing user**
		http://localhost:8080/api/users/1
	* **Returning an existing user**
		http://localhost:8080/api/users/1
	* **Returning the list of all users**
		http://localhost:8080/api/users

### HATEOAS
This API is HATEOAS based hypermedia-driven. HATEOAS stands for Hypermedia as the Engine of Application State.The default media type served by Spring HATEOAS is HAL. HAL (Hypertext Application Language) is one of the most popular hypermedia formats to emerge. It’s lightweight, adding little more to JSON encodings than a _links attribute to contain a list of hyperlinks and their rels.

### Metrics:
Collecting metrics provides the necessary information to handle an API efficiently by giving an insight of how of how it is perfoming, how it is being user and even whether each service is working properly or not.
This API provides data about the number of calls of each HTTP Request along with the corresponding status as well as data about threads, processors, uptime, memory, heap...

### API documentation
This API comes with structured description and documentation following the OpenAPi specification by taking advantage of SpringFox Swagger2 implementation.
