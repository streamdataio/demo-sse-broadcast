h1. How to start?

This demo is related to the StackOverflow topic: http://stackoverflow.com/questions/31538116/how-to-use-server-sent-event-to-broadcast-to-multiple-users-using-javascript-at
  
Start the SpringBoot server by running the main class `DemoSseBroadcastApplication` through an IDE or through maven
 with the command `mvn spring-boot:run` and test in your browser by opening the `index.html` file.

For Tomcat deployment, run `mvn clean install`, get the war and deploy it in your Tomcat (tested with Tomcat 8.0.14).
To test, you can open a browser `http://localhost:8080/demo-sse-broadcast-0.0.1-SNAPSHOT/sse?type=standard` and see incoming events.