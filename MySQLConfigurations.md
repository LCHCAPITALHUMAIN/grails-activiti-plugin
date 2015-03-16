# Introduction #

This document will described step-by-step of how to setup Grails Activiti Plugin to use with MySQL database. We will assume that MySQL database is pre-installed and started in your development workstation.


# 4 Steps to setup Grails Activiti Plugin with MySQL database #
## 1. Create Activiti Database in MySQL ##
  * Login to MySQL using command below:
```
mysql -u root
```
  * Create Activiti database using command below:
```
create database activiti
```

## 2. Download and Setup MySQL Connector/J (JDBC Driver) ##
  * Download latest MySQL Connector/J at http://www.mysql.com/downloads/connector/j/
  * Extract the downloaded compressed file and copy the mysql-connector-java-5.1.13-bin.jar (latest version on 26 August 2010) to lib directory of your grails application.

## 3. Update Config.groovy ##
  * Update databaseName in grails-app/conf/Config.groovy from h2 to mysql, for example:
```
activiti {
    processEngineName = "activiti-engine-default"
    databaseType = "mysql" 
    databaseSchemaUpdate = true // true, false or "create-drop"  
    jobExecutorActivate = false
}
```

## 4. Update Datasource.groovy ##
  * Update database configurations in grails-app/conf/DataSource.groovy from H2 to MySQL, for example:
```
dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "root"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/activiti"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"          
            url = "jdbc:mysql://localhost:3306/activiti"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/activiti"
        }
    }
}
```
To save your typing, you can download the [MySQL's DataSource.groovy here](http://code.google.com/p/grails-activiti-plugin/source/browse/misc/MySqlDataSource.groovy).

Done! You should able run your grails activiti application with MySQL without any problem.