# Internet-shop
- Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Authors](#authors)
###<a name="purpose"></a>Project purpose

This is a template for creating an Internet-shop.
<hr>
It implements typical functions for an online store. 
It has login and registration forms.

Available functions for all users: 
* view menu of the store
* view items of the store
* registration
* log in
* log out
  
Available functions for users with a USER role only: 
* add to user's bucket
* delete from user's bucket
* view all user's orders
* complete order
* view a lists of selected items in user`s bucket

Available functions for users with an ADMIN role only:
* add items to the store
* delete items from the store
* view a list of all users
* delete users from the store

<hr>

### <a name="structure"></a>Project Structure
* Java 11
* Maven 4.0.0
* javax.servlet-api 3.1.0
* jstl 1.2
* log4j 1.2.17
* maven-checkstyle-plugin 3.1.0
* mysql-connector-java 8.0.18
<hr>

### <a name="developer-start"></a>For developer
Open the project in your IDE.

Add it as maven project.

Configure Tomcat:
* add artifact
* add sdk 11.0.3

Add sdk 11.0.3 in project structure.

Use file /jv-internet-shop/src/main/resources/init_db.sql to create schema, all the tables and add few items for testing required by this app in MySQL database.

At /Oasis/src/main/java/mate/academy/internetshop/Factory class use username and password for your DB to create a Connection.

Change a path in /Oasis/src/main/resources/log4j.properties. It has to reach your logFile.

Run the project.

If you first time launch this project you can add user with admin role on login page 
 
<hr>

### <a name="authors"></a>Authors
[Mykhailo Kramar](https://github.com/Mykhaylo12?tab=repositories)

