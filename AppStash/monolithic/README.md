MongoDB Online Shop
===================

The MongoDB Online Shop is a small Sample Application that demonstrates the usage of MongoDB in a Java project
using Spring Data.

System Requirements
-------------------

The following requirements have to be met for setting up and compiling the project:

* [Gradle](http://www.gradle.org/)
* [Groovy](http://groovy.codehaus.org/)
* [Git](http://git-scm.com/)
* [Java](http://www.java.com/de/download/)
* [Mongo DB](http://www.mongodb.org/)

If you're on Mac OS, the easiest way to meet all requirements is to use the package manager Homebrew:

    1. Install XCode via Apple's App Store.
    2. In XCode, install 'Command Line Tools'.
    3. Install Homebrew with
        $ ruby -e "$(curl -fsSkL raw.github.com/mxcl/homebrew/go)"
        (see http://mxcl.github.com/homebrew/ for checking if the command is still valid)
    4. Install all required packages directly in the shell, e.g. via
        $ brew install git

Alternatively, you can set the following system variables in '.bash_profile' after manually installing Gradle, Groovy,
Git, Java, and Mongo DB:

    $ export GRADLE_HOME<Your Gradle Home>/gradle-VERSION
    $ export MONGO_HOME=<Your Mongo Home>/mongodb-VERSION
    $ export GROOVY_HOME=<Your Groovy Home>/groovy-VERSION
    $ export JAVA_HOME=<Your Java Home>
    $ export CLASSPATH=<Your Java Home>/lib/

    $ export PATH=$PATH:$GRADLE_HOME/bin
    $ export PATH=$PATH:$MONGO_HOME/bin
    $ export PATH=$PATH:$JAVA_HOME/bin
    $ export PATH=$PATH:$GROOVY_HOME/bin


Setting up the Project
----------------------

In the console, select any directory on your computer and clone this repository from github. That's it! The project is ready to be build for your favorite IDE:

    $ gradle eclipse
    $ gradle idea

Starting the Application
------------------------

There are two options to start the application:

    1. Command "$ gradle jettyRun" in the console for running on port 8080.
    2. Run JettyStart.main from the IDE for running on the port specified as a program argument.

For the latter option, make sure to set the working directory to ./shop/ui.
In both cases, the application will be available at http://localhost:port/shop.

Data Generator
---------------

On startup, the application automatically checks if the database "shop" exists and generates users, products, and
random orders for the last 3 years. Hence, the easiest way to re-initialize the data is to delete the entire database
"shop" and restart the application.

General Hints
-------------

- The Login "admin" / "admin123" can be used for accessing a list of all orders.
- For convenience, a random customer is automatically logged in as soon as the user proceeds to the checkout.
- Any particular customer can be logged in with the password "customer123"

MongoDB Collections
-------------------

    order
        stores all submitted orders
    product
        the product catalog
    recommendation.mapping
        mapping table used and maintained by mahout
    recommendation.preference
        user-item-preference (counts the number of orders for each user-item-pair)
    sequence.order
        incremental counter for providing order IDs
    user
        stores all registered users