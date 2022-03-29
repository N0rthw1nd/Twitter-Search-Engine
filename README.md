# IR PROJECT SEARCH ENGINE

This project is divided in two parts: a backend, written in Java and a frontend written in Python. 
Once the backend and the frontend are started, the Web App is accessible at the addrees [Web App home page](http://0.0.0.0:5000).

# Directories Structure

"IRProjectSearchEngine" is the main directory of the backend, it containts an Intellij project, with all the configuration files, the already built index and the crawled data.

"WebApp" contains the FrontEnd.



# Libraries

**BACKEND - JAVA**
The backend is developed with IDE Intellij using maven as package manager, so the projects libraries are listed in the file pom.xml. 
To view and load them is sufficient to open the project with a JAVA IDE that supports Maven projects.
Also is required support for Spring Boot framework.

**FRONTEND - PYTHON**
The project is written in python3.8 so is preferable to install that version. Other libraries used are request and flask. They are listed with their versions in the file: (./WebApp/requirements.txt)

# Compiling The Project

**BACKEND - JAVA**
To compile the project, import the directory "IRprojectSearchEngine" inside an IDE like Intellij or Eclipse and start the main class RestappApplication. To run the main class is essential to have Spring Boot installed.

**FRONTEND - PYTHON**
To start the frontend run the script (in the main directory)./frontend.sh that will install the required libraries and start the frontend. 

```
$ sh ./frontend.sh
```

Alternatively run the following commands:

```
$ cd WebApp
$ pip3.8 -r install requirements.txt
$ python3.8 app.py

```

