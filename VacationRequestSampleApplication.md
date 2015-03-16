

# Acknowledgment #
May thanks to works done and supports given by Activiti community, the vacation request sample application is created based on the works of Activiti community at http://activiti.org/userguide/index.html#taskForms with minor changes in [VacationRequest.bpmn20.xml](http://code.google.com/p/grails-activiti-plugin/source/browse/src/sample-app/vacation-request/grails-app/conf/standard/VacationRequest.bpmn20.xml) file.

# Introduction #
## Process overview ##
The following two users of corresponding group involved in vacation request process:
  * kermit, normal user
  * fozzie, management

kermit initiate the vacation request and fozzie approve or reject the request.

**Note: The sample application running live at http://grails-activiti.aws.af.cm/ (27-July-2012)**

## Installation ##
(Please execute `grails activiti-quickstart` command prior to installation if you haven't done so, please see description [here](http://code.google.com/p/grails-activiti-plugin/wiki/GrailsActivitiCommands#Install_Activiti_Quick_Start_Environment) for more information)

Since 5.9, if you want the Vacation Request Sample Application run with Spring Security, add the `compile ':activiti-spring-security:0.4.8'` to the `BuildConfig.groovy` and execute `grails refresh-dependencies` to install [Activiti Spring Security Integration plugin](http://code.google.com/p/grails-activiti-spring-security-plugin/) and it's dependent  [Spring Security Core plugin](http://grails.org/plugin/spring-security-core) .

You should see the following message in your console:
```
*******************************************************
* You've installed the Spring Security Core plugin.   *
*                                                     *
* Next run the "s2-quickstart" script to initialize   *
* Spring Security and create your domain classes.     *
*                                                     *
*******************************************************
```
If you don't, execute `grails refresh-dependencies` again.

Install the sample application into your project with the following command:
```
grails install-vacation-request-sampleapp
```

The installation script will run the following steps:
  1. Prompt "Enter package name for User and Role domain classes:", for example you can enter `org.grails.activiti.identity`.
  1. Copy login/logout views and controllers from Spring Security Core plugin to your application.
  1. Create Activiti specific implementation of User.groovy, Role.groovy and `UserRole.groovy` to your application's domain classes directory according to package name entered.
  1. Update Config.groovy for settings related to Spring Security Core plugin.

**Before 5.9, you will be prompted "Do you want to use Spring Security for identity service? (y, n)". Type 'y' to install Spring Security Integration.**

## Run the application ##
After deployed the process definition file successfully and run the application with the following command:
```
grails run-app
```
Then, open your browser at http://localhost:8080/yourApplicationName.

## Uninstallation ##
If you want to remove the sample application from your project, you can do so with the following command:
```
grails uninstall-vacation-request-sampleapp
```

# Task List of both users #
Before we start the process, let's look at My Tasks list for kermit:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-taskbar.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-taskbar.jpg)

(My Tasks: 15 pending)

and fozzie:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-taskbar.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-taskbar.jpg)

(My Tasks: 0 pending, Unassigned Tasks: 3 pending)

# Initiate vacation request #
kermit initiates vacation request by click on `[Start]` link next to `VacationRequestController`, the following creation screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-create.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-create.jpg)

(My Tasks: 16 pending)

kermit want to submit the request later, so he save the task first by click Create button. By click Complete button, the task is submit to management immediately. After click Create button, the screen below display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-created.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-created.jpg)

(My Tasks: 16 pending)

A moment later, kermit ready to submit the task, he retrieve the task from My Tasks list and the following update screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-update.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-update.jpg)

(My Tasks: 16 pending)

He confirm nothing need to be updated and click Complete button, the following screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-updated-complete.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-updated-complete.jpg)

(My Tasks: 15 pending)

# Vacation request approval #
fozzie access to his My Tasks list:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-taskbar2.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-taskbar2.jpg)

(My Tasks: 0 pending, Unassigned Tasks: 4 pending)

and access to his Unassigned Tasks list:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-unassignedtasks.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-unassignedtasks.jpg)

(My Tasks: 0 pending, Unassigned Tasks: 4 pending)

and click Claim button for first pending unassigned task, the claimed task put into My Tasks list:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-unassignedtasks-claimed.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-unassignedtasks-claimed.jpg)

(My Tasks: 1 pending, Unassigned Tasks: 3 pending)

fozzie access to his My Tasks list:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-mytasks.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-mytasks.jpg)

(My Tasks: 1 pending, Unassigned Tasks: 3 pending)

and open the task by click on the Id., the following approval screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-approval.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-approval.jpg)

(My Tasks: 1 pending, Unassigned Tasks: 3 pending)

fozzie reject the request by select REJECT for Approval Status and click Complete button, the following screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-rejected.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-rejected.jpg)

(My Tasks: 0 pending, Unassigned Tasks: 3 pending)

# Resend vacation request #
kermit noticed his request was rejected from his My Tasks list:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-mytasks-rejected.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-mytasks-rejected.jpg)

(My Tasks: 16 pending)

kermit update his vacation request and resend his request (checked Resend Request field) by click Complete button:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-rejected-edit-with-resend.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-rejected-edit-with-resend.jpg)

(My Tasks: 16 pending)

then, the following result screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-rejected-edit-with-resend-result.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/kermit-rejected-edit-with-resend-result.jpg)

(My Tasks: 15 pending)

# Vacation request second approval #
fozzie claims the task from his Unassigned Tasks list and start working on the task, the following approval screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-reapprove.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-reapprove.jpg)

(My Tasks: 1 pending, Unassigned Tasks: 3 pending)

He approved the request this time and click Complete button, the following result screen display:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-reapproved-complete.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/fozzie-reapproved-complete.jpg)

(My Tasks: 0 pending, Unassigned Tasks: 3 pending)

# Email notification #
When you check your mail box of `activiti.mailServerDefaultFrom`, you will see a message with subject "Your Vacation Request has been approved." like the screen below:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/email-sent.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/vacation-request-sampleapp/email-sent.jpg)

**Note:** If you are sending email using gmail account, you need to uncomment the following code block in `VacationRequestBootStrap.groovy` file:
```
["mail.smtp.auth":"true",
 "mail.smtp.socketFactory.port":"465",
 "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
 "mail.smtp.socketFactory.fallback":"false",
 "mail.smtp.starttls.required": "true"].each { k, v ->
     System.setProperty k, v
}
```

Sample gmail account configuration in Config.groovy file:
```
activiti {
    processEngineName = "activiti-engine-default"
	  databaseType = "h2" 
	  deploymentName = appName
	  deploymentResources = ["file:./grails-app/conf/**/*.bpmn*.xml", 
	                         "file:./grails-app/conf/**/*.png", 
	                         "file:./src/taskforms/**/*.form"]
	  jobExecutorActivate = false
	  mailServerHost = "smtp.gmail.com"
	  mailServerPort = "465"
	  mailServerUsername = "email@gmail.com"
	  mailServerPassword = "mypassword"
	  mailServerDefaultFrom = "email@gmail.com"
	  history = "audit" // "none", "activity", "audit" or "full"
	  sessionUsernameKey = "username"
	  useFormKey = true
}
```