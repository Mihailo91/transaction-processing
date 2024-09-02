# Batch transaction processing

Batch demo app for processing transactions from csv file. With batch job all transactions are analyzed and inserted into database with suspicious flag in column suspicious_activity

## Technologies used
- [ ] Spring Batch
- [ ] Spring Data flow
- [ ] postgress database

## Setting up environments

### Spring data flow

**Useful resources**
* Database Configuration
	https://docs.spring.io/spring-cloud-dataflow/docs/1.2.3.RELEASE/reference/html/configuration-rdbms.html
	
* Manual instalation
	https://dataflow.spring.io/docs/installation/local/manual/
	
* Download the Spring Cloud Data Flow Server (Shell) and Skiper Server by using the following commands:
```
	wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.11.4/spring-cloud-dataflow-server-2.11.4.jar

	wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-shell/2.11.4/spring-cloud-dataflow-shell-2.11.4.jar
	
	wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-skipper-server/2.11.4/spring-cloud-skipper-server-2.11.4.jar
```

### Run Spring data flow server

Run the following comands inside the folder in which downloaded jars are located.
```
java -jar spring-cloud-skipper-server-2.11.4.jar
```

```
java -jar spring-cloud-dataflow-server-2.11.4.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/task --spring.datasource.username=postgres --spring.datasource.password=twingo --spring.datasource.driver-class-name=org.postgresql.Driver --spring.datasource.initialization-mode=always --spring.jpa.hibernate.ddl-auto=update --spring.jpa.show-sql=true
```

Spring Data Flow dashboard is available on this url:

-  http://localhost:9393/dashboard/index.html#/apps
	
	
### Create a package for batch-procesing-app

```
mvnw clean package
```
Copy jar form target folder to some location. This is importan because this jar will be used by Spring Data Flow, and rebuilding of app won't be possible unless app is unregistered from Spring Data Flow)

### Create the transaction table in the task database

```
CREATE TABLE public.transaction
(
    id bigint,
    card_holder text COLLATE pg_catalog."default",
    card_number text COLLATE pg_catalog."default",
    amount numeric,
    time_of_transaction timestamp(4) with time zone,
    suspicious_activity smallint
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.transaction
    OWNER to postgres;
```

## Running the application from data flow

Thru Data flow dashboard register app as a task.
Add location to the file like this:

* file:///Users/example/final/batch-processing-complete-0.0.1-SNAPSHOT.jar
 

* create a task using this app an launch it.

after execution  you can see result and logs from the server.
