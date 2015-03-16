**We moved to `GitHub`! Please access to https://github.com/limcheekin/activiti for latest source codes of the project**

# Overview #
Grails Activiti Plugin is created to integrate [Activiti BPM Suite](http://www.activiti.org/) and workflow system to [Grails Framework](http://www.grails.org/). With the Grails Activiti Plugin, workflow application can be created at your fingertips!

# Installation #
Install the plugin into your project with the following command:
```
grails install-plugin activiti
```

# Configuration #
After the plugin installed into your project, the following configurations will be appended to your project's Config.groovy file:
```
activiti {
    processEngineName = "activiti-engine-default"
	  databaseType = "h2" 
	  deploymentName = appName
	  deploymentResources = ["file:./grails-app/conf/**/*.bpmn*.xml", 
	                         "file:./grails-app/conf/**/*.png", 
	                         "file:./src/taskforms/**/*.form"]
	  jobExecutorActivate = false
	  mailServerHost = "smtp.yourserver.com"
	  mailServerPort = "25"
	  mailServerUsername = ""
	  mailServerPassword = ""
	  mailServerDefaultFrom = "username@yourserver.com"
	  history = "audit" // "none", "activity", "audit" or "full"
	  sessionUsernameKey = "username"
	  useFormKey = true
}

environments {
    development {
        activiti {
	    processEngineName = "activiti-engine-dev"
	    databaseSchemaUpdate = true // true, false or "create-drop"	  
        }
    }
    test {
        activiti {
	    processEngineName = "activiti-engine-test"
	    databaseSchemaUpdate = true
	    mailServerPort = "5025"			  
        }
    }	
    production {
        activiti {
	    processEngineName = "activiti-engine-prod"
	    databaseSchemaUpdate = false
	    jobExecutorActivate = true
        }
    }
}	
```
Please refer to [ProcessEngineConfiguration bean](http://activiti.org/userguide/index.html#configurationRoot) section of the [Activiti User Guide](http://activiti.org/userguide/index.html) to find out more about the configurations above. Additional plugin-specific configuration item is `sessionUsernameKey` and `useFormKey`:
  * `sessionUsernameKey` allow you to specify how your application stored the user identity in session. For example, the application will able to access to current user identity with `session.username` for the default configuration.
  * `useFormKey` allow you to specify whether the process engine using value specified in `activiti:formKey` attribute of userTask element as form URI (when it is set to `true`) or using Convention over Configuration (when it is set the `false`). The convention of the URI is `/${processDefinitionId}/${taskDefinitionId}`. When `useFormKey` is set to `true`, the process engine will pick up the value of `activiti:formKey` attribute as form URI, if `activiti:formKey` attribute is not found, the form URI will acquire using the Convention over Configuration approach.

The plugin using configurations in DataSource.groovy file for Activiti's database-specific configurations since version 5.0.alpha4, so you need to update your project's DataSource.groovy file with the following configurations (To save your typing, you can download the updated [DataSource.groovy here](http://grails-activiti-plugin.googlecode.com/hg/grails-app/conf/DataSource.groovy)):

**Note:** Start from 5.5, you can install the default H2 database `DataSource.groovy` by execute the `grails activiti-quickstart` command.

```
dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
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
            url = "jdbc:h2:tcp://localhost/activiti"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:tcp://localhost/activiti"
        }
    }
}
```
MySQL and PostgreSQL database are supported but it need to configure manually, please refer to [MySQL Configurations](http://code.google.com/p/grails-activiti-plugin/wiki/MySQLConfigurations) and [PostgreSQL Configurations](http://code.google.com/p/grails-activiti-plugin/wiki/PostgreSqlConfigurations) to find out more.

# Run the application #
Run your application with the following command:
```
grails run-app
```

By open your browser at http://localhost:8080/yourApplicationName, you should see the following updated main (index) screen:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/main/main-screen.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/main/main-screen.jpg)


---

You can select an user from the combo box of screen below as current user identity to access the application.

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/main/select-user.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/main/select-user.jpg)

**Note:** There is no pre-defined users after the plugin is installed, the users on the screen is defined by sample application, you may consider install [Vacation Request Sample Application](VacationRequestSampleApplication.md).

If you don't want to install any sample application to your project. you can create users by adding following code to `BootStrap.groovy` file:
```
class BootStrap {
	def identityService
		
     def init = { servletContext ->
 	identityService.with {		
		saveUser(newUser("kermit"))
		saveUser(newUser("fozzie"))
		saveUser(newUser("peter"))
		saveGroup(newGroup("management"))
		saveGroup(newGroup("user"))
		createMembership("kermit", "user")
		createMembership("fozzie", "management")
		createMembership("peter", "management")
	}	    
     }
}
```


---

After an user is selected, you will see the following screen:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/main/user-selected.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/main/user-selected.jpg)

Under Activiti Controllers section, you can access the task lists by click on `TaskListController` or start a process (e.g.: Vacation Request) by click on `[Start]` link next to Activiti controller (e.g.: `VacationRequestController`).

You may access to other Grails Controllers listed under Other Controllers section.

# Automatic Resource Deployment #
As the plugin using `SpringProcessEngineConfiguration` to instantiate and initialize Activiti process engine and services, it supports automatic process resources deployment as documented in [Activiti's User Guide](http://activiti.org/userguide/index.html#N1053E). The plugin further supports Automatic Resource Deployment upon changes of process resources when the application is running in development environment, without restart the application.

# Using Spring Security as Identity Service #
Started from 5.4, you can use Spring Security as custom identity service of the Activiti engine. The Activiti and Spring Security integration is enabled by a separate project, [Activiti Spring Security plugin](http://code.google.com/p/grails-activiti-spring-security-plugin/).

You can install the plugin with the following command:
```
grails install-plugin activiti-spring-security
```

The Vacation Request Sample Application incorporated this feature, please look into it to find out how it works.

# Other Plugin's Features #
  * [3 Task Lists: My Tasks, Unassigned Tasks and All Tasks](TaskLists.md)
  * [Scaffolding: Activiti Controller and CRUD Views Generation](Scaffolding.md)
  * [Grails Activiti Commands (Targets)](GrailsActivitiCommands.md)
  * [Grails Activiti API](GrailsActivitiApi.md)
  * Sample Applications
    * [Vacation Request Sample Application](VacationRequestSampleApplication.md)
    * [Contribute Your Process As Sample Application](ContributeYourProcessAsSampleApplication.md)

# Roadmap #
The Grails Activiti Plugin will practice parallel release by following the roadmap of activiti at http://docs.codehaus.org/display/ACT/Roadmap. New version of the plugin should release shortly after the release of the activiti distribution.
That's the reason the first release of this plugin is 5.0.alpha3 which is the latest version of activiti release dated on 15 July 2010.
The first production version of this plugin should release on early December 2010.

# Version History #
**12-Jul-2012 5.9**
  * Release Spring Security Integration plugin 0.4.8 to support latest version of Spring Security Core plugin 1.2.7.3.
  * Upgrade Activiti's jar files to 5.9. The plugin required Grails 2.x start from this version.
  * Change to Grails dependency management from putting Activiti's jar files in lib directory.
  * Fixed navigation bar to fit Grails 2.x layout.
  * Fixed Issue [4](https://github.com/limcheekin/activiti/issues/4): install-vacation-request-sampleapp script was broken in Grails 2.x
  * Fixed Issue [16](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=16): `ClassNotFoundException` upon domain object deserialization from the context variable (by [Ilya.Drobenya](https://github.com/idrabenia)).
  * Move the code of install script to activiti-quickstart
  * Improve text display on console.

**21-Dec-2011 5.8.1**
  * Fixed Issue [21](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=21) : Activiti 5.8 controller reloading broken in Grails 2.0.0.

**30-Nov-2011 5.8**
  * Upgrade Activiti's jar files to 5.8.

**19-Aug-2011 5.7**
  * Upgrade Activiti's jar files to 5.7.
  * Fixed Issue [15](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=15) : UUIDs in domain objects prevent task deletion.
  * Fixed Issue [18](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=18) : logging.properties causes large number of logs in the log file

**06-Jun-2011 5.6**
  * Upgrade Activiti's jar files and examples to 5.6.
  * Fixed Issue [13](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=13): Unable to run grails war after installing grails-activiti-plugin 5.5.
  * Fixed Issue [14](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=14): Nullpointer exception when the activiti engine is turned off, using activiti spring security plugin.
  * Support turning off Activiti Engine by add `activiti.disabled=true` to Config.groovy.

[Previous Versions](http://code.google.com/p/grails-activiti-plugin/wiki/VersionHistory)


# Known Issues #
  1. `grails generate-activiti-all *` not working due to domainClasses not found.
  1. After edit `*Controller.groovy` file when application running in development environment, access views of the controller will hit HTTP Status 404. The issue will be fixed in Grails 1.3.5 as mentioned in http://grails.1312388.n4.nabble.com/in-place-plugin-controllers-lose-annotations-and-cannot-resolve-plugin-views-after-reload-td1473972.html. Grails 1.3.5 released on 04-Oct-2010.
If issues above really matters to you, please vote for it.

# To Do #
  * Resolved known issues.

# Final Note #
We are welcome your feedback and would like to hear about in what project and how you use the plugin. You are welcome to join the project discussion forum at http://groups.google.com/group/grails-activiti-plugin, see you there!