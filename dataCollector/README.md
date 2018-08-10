# Code for DataCollector SmartTest8

Build and Run
-------------

Prerequisites
-------------
Java 8
Maven > 3.0


Go on the project's root folder, then type:
------------------------------------------
$ mvn package
$ mvn spring-boot:run

From Eclipse (Spring Tool Suite)
---------------------------------
Import as Existing Maven Project and run it as Spring Boot App.

To Add the Jar in the local Maven Repo
-------------------------------------

mvn install:install-file -Dfile=target/dataCollector-1.0.0-jar-with-dependencies.jar -DgroupId=com.nashtech -DartifactId=dataCollector -Dversion=1.0.0 -Dpackaging=jar





