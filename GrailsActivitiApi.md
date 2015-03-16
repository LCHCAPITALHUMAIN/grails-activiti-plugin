# Introduction #
Below APIs are supported by Grails Activiti Plugin:


# Controller Dynamic Methods #
To turn on Activiti dynamic methods for existing controllers, you need to mark the controller class with `static activiti = true` to turn it into Activiti controller, for example:
```
class VacationRequestController {

    static activiti = true
```

## Get total number of assigned tasks of user ##
```
     Long getAssignedTasksCount() 
```

## Get assigned tasks of user ##
```
     List<Task> findAssignedTasks (Map params)
```

## Get total number of unassigned tasks of user ##
```
     Long getUnassignedTasksCount() 
```

## Get unassigned tasks of user ##
```
     List<Task> findUnassignedTasks (Map params)
```

## Get total number of all tasks ##
```
     Long getAllTasksCount() 
```

## Get all tasks ##
```
     List<Task> findAllTasks (Map params)
```

### Sample code ###
```
    def allTaskList = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)		
        [allTasks: findAllTasks(params), 
	 unassignedTasksCount: unassignedTasksCount,
	 myTasksCount: assignedTasksCount,
	 allTasksCount: allTasksCount]
    }		
```

## Start task instantly ##
```
     void startTask(String taskId)
```
Claim the given task and open an appropriate task form of the task by redirect to specific URI.

Sample code:
```
     def startTask = {
	  startTask(params.taskId)
     }		
```

## Claim task ##
```
     void claimTask(String taskId)
```
Claim the given task and put the task into user's assigned task list (My Tasks).

Sample code:
```
     def claimTask = {
          claimTask(params.taskId)
     }  	
```

## Open task form ##
```
     void getForm(String taskId)
```
Open an appropriate task form of given task by redirect to specific URI.

Sample code:
```
     def getForm = {
	 getForm(params.taskId)
     }		
```

## Revoke task ##
```
     void revokeTask(String taskId)
```
Undo the given claimed task and put the task into unassigned task list.

Sample code:
```
     def revokeTask = {
          revokeTask(params.taskId)
     }  	
```

## Delete task ##
```
     String deleteTask(String taskId)
```
Delete the given task and it's associated domain object and return the Id. of the domain object (if there is any). The domain class assumed to be same name and package with the controller class, for example: `vacationRequest.VacationRequest` and `vacationRequest.VacationRequestController`. Otherwise, you will need to pass in domainClassName parameter (e.g.: `com.vobject.VacationRequest`) for method below:
```
     String deleteTask(String taskId, String domainClassName)
```


Sample code:
```
     def deleteTask = {
          deleteTask(params.taskId)
     }  	
```


## Set or unset assignee ##
```
     void setAssignee(String taskId, String username) 
```
Changes the assignee of the given task to the given username

Sample code:
```
     def setAssignee = {
	 setAssignee(params.taskId, params.assignee)
     }		 
```


## Change priority ##
```
     void setPriority(String taskId, int priority) 
```
Changes the priority of the task.

Sample code:
```
     def changePriority = {
    	 setPriority(params.taskId, Integer.parseInt(params.priority))
     }	 
```

**Note:** Please refer to [TaskController.groovy](http://code.google.com/p/grails-activiti-plugin/source/browse/grails-app/controllers/org/grails/activiti/TaskController.groovy) for complete source code.

## Start process, claim task and open task form ##
```
     void start(Map params) 
```
Start a process (assumed that the process key is same with controller, if they are different, you need to specify the process key in `params.controller` prior to calling this method), claim task and open task form.

Assign an ID. to params.businessKey prior to invoke this method to support custom business ID.

**Note:**  Additional `MapEntry` (key value pair) specific to your process can be set to the `params` map prior to calling this method, it's value will be stored in process variable.

Sample code:
```
    def start = {
	start(params)
    }
```

## Save task ##
```
     void saveTask(Map params)
```
Save the given task by specify `params.action` to indicate which task form to open when user resume the task from My Tasks (assumed that `params.taskId` and `params.id` is set and the task form is using the same controller, if they are different, you need to specify the controller in `params.controller` prior to calling this method).

Sample code:
```
     params.action="show"
     saveTask(params)
```

## Complete task ##
```
     void completeTask(Map params)
```
Complete the given task (assumed that `params.taskId` and `params.id` is set).

**Note:**  Additional `MapEntry` (key value pair) specific to your process can be set to the `params` map prior to calling this method, it's value will be stored in process variable.

Sample code:
```
     params.vacationApproved = vacationRequestInstance.approvalStatus == ApprovalStatus.APPROVED
     params.emailTo = vacationRequestInstance.employeeName
     completeTask(params)
```

# Activiti Service #
By marking the controller class with `static activiti = true`, for example:
```
class VacationRequestController {

    static activiti = true
```
An instance variable `activitiService` will be instantiated implicitly for the controller class.

Otherwise, you will need to define `def activitiService` in the class, it will be auto-instantiated by Grails via Dependency Injection mechanism, for example:
```
class VacationRequestController {

    def activitiService
```

As methods below are same with those defined in Controller Dynamic Methods section, it will not repeat in this section.
```
    List<Task> findAssignedTasks (Map params)
    List<Task> findUnassignedTasks (Map params)
    List<Task> findAllTasks (Map params)
    Long getAllTasksCount() 
    String deleteTask(String taskId, String domainClassName)
    void setAssignee(String taskId, String username)
    void setPriority(String taskId, int priority) 
```

The different is you need to calling methods above with `activitiService` variable, not directly, for example:
```
     def setAssignee = {
	 activitiService.setAssignee(params.taskId, params.assignee)
     }		 
```

Please refer to [Controller Dynamic Methods](#Controller_Dynamic_Methods.md) section to find out more.

## Start process by key ##
```
     void startProcess(Map params) 
```
Start a process by specify params.controller as process key prior to calling this method.

Assign an ID. to params.businessKey prior to invoke this method to support custom business ID.

**Note:**  Additional `MapEntry` (key value pair) specific to your process can be set to the `params` map prior to calling this method, it's value will be stored in process variable.

Sample code:
```
    def startProcess = {
        params.controller = "vacationRequest"   
	activitiService.startProcess(params)
    }
```

## Get total number of assigned tasks of user ##
```
     Long getAssignedTasksCount(String username) 
```
Get total number of assigned tasks of user by pass in the username parameter.

Sample code:
```
    def assignedTaskList = {
	def assignedTasksCount = activitiService.getAssignedTasksCount("kermit")
    }
```

## Get total number of unassigned tasks of user ##
```
     Long getUnassignedTasksCount(String username) 
```
Get total number of unassigned tasks of user by pass in the username parameter.

Sample code:
```
    def unassignedTaskList = {
	def unassignedTasksCount = activitiService.getUnassignedTasksCount("kermit")
    }
```


## Get assigned task of user ##
```
    Task getAssignedTask(String username, String processInstanceId)
```
Get an assigned task of user for given username and processInstanceId.

Sample code:
```
    def getTask = {
        ProcessInstance pi = activitiService.startProcess(params)
	def task = activitiService.getAssignedTask("kermit", pi.id)
    }
```

## Get unassigned task of user ##
```
    Task getUnassignedTask(String username, String processInstanceId)
```
Get a unassigned task of user for given username and processInstanceId.

Sample code:
```
    def getTask = {
        ProcessInstance pi = activitiService.startProcess(params)
	def task = activitiService.getUnassignedTask("kermit", pi.id)
    }
```

## Claim task ##
```
     void claimTask(String taskId, String username)
```
Claim the task for given username and put the task into user's assigned task list (My Tasks).

Sample code:
```
     def claimTask = {
          activitiService.claimTask(params.taskId, "kermit")
     }  	
```

## Complete task ##
```
     void completeTask(String taskId, Map params)
```
Complete the given task. `params.id` and `params.domainClassName` must be set (required by deleteTask to delete associated domain object in task list).

**Note:**  Additional `MapEntry` (key value pair) specific to your process can be set to the `params` map prior to calling this method, it's value will be stored in process variable.

Sample code:
```
     params.id = vacationRequestInstance.id
     params.domainClassName = vacationRequestInstance.class.name
     params.vacationApproved = vacationRequestInstance.approvalStatus == ApprovalStatus.APPROVED
     params.emailTo = vacationRequestInstance.employeeName
     activitiService.completeTask(params.taskId, params)
```

## Set task form URI ##
```
     void setTaskFormUri(Map params)
```
As the plugin support save task, this method allow the application to specify the domain class name (required by deleteTask to delete associated domain object in task list), domain object Id. and URI (required by assigned task list to re-open the task form with domain object populated.)

Sample code:
```
     params.id = vacationRequestInstance.id
     params.controller = "vacationHandle" // only when controller is different
     params.action = "show"
     params.domainClassName = vacationRequestInstance.class.name
     activitiService.setTaskFormUri(params)
```

## Get task form URI ##
```
     String getTaskFormUri(String taskId)
```
Get URI of given task.

Sample code:
```
     def getForm = { String taskId ->
	 redirect uri:activitiService.getTaskFormUri(taskId)
     }			
```

## Get candidate user Ids for given task ##
```
     List<String> getCandidateUserIds(String taskId)
```
Get candidate user (potential owner) Ids for given task.

Sample code:
```
     def userIds = activitiService.getCandidateUserIds(taskInstance.id)
     for (id in userIds) {
         println id
     }
```

# Activiti Utils #
`org.grails.activiti.ActivitiUtils` allow application such as view layer access to activiti instance variables without instantiate any class. Below static methods are supported by `ActivitiUtils`:
```
class ActivitiUtils {
     static getActivitiService() 
     static getIdentityService() 
     static getProcessEngine() 
     static getRuntimeService() 
     static getRepositoryService() 
     static getTaskService() 
     static getManagementService() 
     static getHistoryService()
} 
```

Sample code:
```
     def identityService = org.grails.activiti.ActivitiUtils.identityService
     identityService.findUsersByGroupId("user")
```

# Other Grails Activiti Classes #
## Approval Status ##
An `org.grails.activiti.ApprovalStatus` enum class is created for convenient purpose only as most approval process supports PENDING, APPROVED and REJECTED status.
```
enum ApprovalStatus {
     PENDING,
     APPROVED,
     REJECTED
}
```

# Standard Activiti API #
Lastly, if Grails Activiti Plugin API doesn't fit your needs, you can always use the standard Activiti API, for example:
```
package com.vobject

class SimpleProcessEngineService {

    static transactional = true
    def identityService
    def managementService
    def processEngine
    def runtimeService
    def repositoryService
    def taskService 
    def historyService

    def notNull() {
	assert identityService
	assert managementService
	assert processEngine
	assert runtimeService
	assert repositoryService	
	assert taskService
	assert historyService

    }
}
```