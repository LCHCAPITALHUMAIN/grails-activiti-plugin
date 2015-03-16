# Introduction #
This document will described step-by-step of how to setup Grails Activiti Plugin to use with PostgreSQL database. We will assume that PostgreSQL database is pre-installed and started in your development workstation (Thanks to Mauro contributed this document as mentioned in this [discussion topic](http://groups.google.com/group/grails-activiti-plugin/t/6fba6396d2972388)).

# 5 Steps to setup Grails Activiti Plugin with PostgreSQL database #
## 1. Create Activiti Database in PostgreSQL ##
  * Create a user for postgresql using command below:
```
psql -U ${user}
```
  * Create Activiti database using command below:
```
create database activiti
```

## 2. Download and Setup PostgreSQL JDBC Driver ##
  * Download latest PostgreSQL JDBC Driver at http://jdbc.postgresql.org/download.html
  * Copy the jar file to lib directory of your grails application.

## 3. Create the schema manually ##
  * This step is needed due to known issues #1 documented at http://code.google.com/p/grails-activiti-plugin/#Known_Issues.
  * Download the Activiti5.0.rc1 release from http://activiti.org/downloads/activiti-5.0.rc1.zip
  * Extract the file and enter in the setup directory
  * Edit build.postgres.properties setting your username and your password
  * Edit build.properties changing db parameter from h2 to postgres
  * Run ant db.create.

## 4. Update Config.groovy ##
  * Update databaseName in grails-app/conf/Config.groovy from h2 to postgres, for example:
```
     activiti {
            processEngineName = "activiti-engine-default"
            databaseType = "postgres" 
            databaseSchemaUpdate = true // true, false or "create-drop" 
          }
```

## 5. Update Datasource.groovy ##
  * Update database configurations in `grails-app/conf/DataSource.groovy` from H2 to PostgreSQL, for example:
```
dataSource {
	pooled = true
	driverClassName = "org.postgresql.Driver"
	username = "${user}"
	password = "${password}"
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
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql:activiti"
		}
	}
	
	test {
		dataSource {
			dbCreate = "create-drop"          
			url = "jdbc:postgresql:activiti"
		}
	}
	
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:postgresql:activiti"
		}
	}
}
```

To save your typing, you can download the [PostgreSQL's DataSource.groovy here](http://grails-activiti-plugin.googlecode.com/hg/misc/PostgreSqlDataSource.groovy).

Done! You should able run your grails activiti application with PostgreSQL without any problem.