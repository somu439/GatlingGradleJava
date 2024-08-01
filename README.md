# GatlingGradleJava
Sample performance test using gradle plugin in java

Pre-req: JDK v11

To run the test from UI - click on gatlingRun from gradle menu

To run from terminal - ./gradlew gatlingRun-<PackageName>.<ClassName>
  ex: ./gradlew gatlingRun-gatlingdemostoreapi.DemostoreApiSimulation

Reports are generated in build/reports/


Advantages
Performance and resource efficiency: Gatling uses less memory and disk space than JMeter. It's better at creating and handling more virtual users and thread reuse compared to JMeter.
Code-based approach: Gatling tests are written as code, making them easier to version control, collaborate on, and maintain using tools like Git.
Scala-based DSL: Gatling uses a Domain-Specific Language based on Scala, which allows for more complex and flexible test scenarios while maintaining readability.
Realistic load simulation: Gatling excels at simulating complex user scenarios, including think times and specific request sequences, to mimic real-world user behavior.
Multithreading efficiency: Gatling uses the Netty framework and Akka toolkit, which are based on an actor model that is distributed and fully asynchronous. This allows Gatling to simulate multiple user journeys using a single thread, improving efficiency.
CI/CD integration: Gatling projects can be easily integrated into CI/CD pipelines, as they can be built using Maven or Gradle.
Reporting capabilities: Gatling provides comprehensive reports and integrates well with real-time tracking tools like NewRelic, Grafana, and Taurus.
Assertions API: Gatling offers an integrated assertions API, allowing functional checks to be run alongside performance testing.
Modern technology stack: As a newer tool, Gatling is built on more modern technologies compared to JMeter, which may offer advantages in terms of performance and features
