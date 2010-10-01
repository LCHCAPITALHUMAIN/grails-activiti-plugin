/* Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
import org.activiti.engine.runtime.ProcessInstance
import org.activiti.engine.task.Task
import grails.util.GrailsNameUtils
import grails.util.Environment
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ControllerArtefactHandler
import org.springframework.core.io.Resource

/**
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 5.0.alpha3
 */
class ActivitiGrailsPlugin {
    // the plugin version
    def version = "5.0.beta2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.3 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Lim Chee Kin"
    def authorEmail = "limcheekin@vobject.com"
    def title = "Grails Activiti Plugin - Enabled Activiti BPM Suite support for Grails"
    def description = '''
 Grails Activiti Plugin is created to integrate Activiti BPM Suite and workflow system to Grails Framework. 
 With the Grails Activiti Plugin, workflow application can be created at your fingertips! 

 Project Site and Documentation: http://code.google.com/p/grails-activiti-plugin/
 Support: http://code.google.com/p/grails-activiti-plugin/issues/list 
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/activiti"
	
    def watchedResources = "file:./grails-app/conf/**/*.bpmn*.xml"
  	
    def observe = ["controllers"]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
    	println "Activiti Process Engine Initialization..."	
    	processEngine(org.activiti.spring.ProcessEngineFactoryBean) {
            processEngineName = CH.config.activiti.processEngineName?:"grails-activiti-noconfig"
            dataBaseName = CH.config.activiti.dataBaseName?:"h2-noconfig"
            dbSchemaStrategy = CH.config.activiti.dbSchemaStrategy?
                               CH.config.activiti.dbSchemaStrategy.toUpperCase().replace("-", "_"):
			       "create-drop".toUpperCase().replace("-", "_")
				    deploymentName = CH.config.activiti.deploymentName?:"deploymentName not defined."
            deploymentResources = CH.config.activiti.deploymentResources?:["file:grails-app/conf/**/*.bpmn*.xml", "file:src/taskforms/**/*.form"]
			      jobExecutorAutoActivate = CH.config.activiti.jobExecutorAutoActivate?:false
				    mailServerHost = CH.config.activiti.mailServerHost?:"mailServerHost not defined."
					  mailServerPort = CH.config.activiti.mailServerPort?:"mailServerPort not defined."
					  mailServerUserName = CH.config.activiti.mailServerUserName?:"mailServerUserName not defined."
					  mailServerPassword = CH.config.activiti.mailServerPassword?:"mailServerPassword not defined."
					  mailServerDefaultFromAddress = CH.config.activiti.mailServerDefaultFromAddress?:"mailServerDefaultFromAddress not defined."
            dataSource = ref("dataSource")
            transactionManager = ref("transactionManager")
        }
    	runtimeService(processEngine:"getRuntimeService") 
        repositoryService(processEngine:"getRepositoryService")
    	taskService(processEngine:"getTaskService") 
    	managementService(processEngine:"getManagementService") 
    	identityService(processEngine:"getIdentityService")
    	historyService(processEngine:"getHistoryService")
		
        activitiService(org.grails.activiti.ActivitiService) {
            runtimeService = ref("runtimeService")
            taskService = ref("taskService")
        }
    }

    def doWithDynamicMethods = { ctx ->
        application.controllerClasses.each { controllerClass ->
            if (controllerClass.hasProperty("activiti") && controllerClass.clazz.activiti) {
                controllerClass.metaClass.getActivitiService = {-> return ctx.activitiService}
                // addActivitiActions(controllerClass) Not possible, find out more at URL below:
                // http://archive.jrcs.codehaus.org/lists/org.codehaus.grails.dev/msg/25487189.post@talk.nabble.com
                addActivitiMethods(controllerClass)
            }
        }
    }

    def addActivitiMethods(controllerClass) {
        controllerClass.metaClass.start = { Map params ->
            activitiService.with {
                params.username = session.username
                ProcessInstance pi = startProcess(params)
                Task task = getUnassignedTask(session.username, pi.id)
                claimTask(task.id, session.username)
                redirect uri:getTaskFormUri(task.id)
            }
        }
				
        controllerClass.metaClass.startTask = { String taskId ->
            activitiService.with {
                claimTask(taskId, session.username)
                redirect uri:getTaskFormUri(taskId)
            }
        }
							
        controllerClass.metaClass.getForm = { String taskId ->
            redirect uri:activitiService.getTaskFormUri(taskId)
        }
				
        controllerClass.metaClass.saveTask = { Map params ->
            params.domainClassName = getDomainClassName(delegate)
            activitiService.setTaskFormUri(params)
        }
				
        controllerClass.metaClass.completeTask = { Map params ->
            params.domainClassName = getDomainClassName(delegate)
            activitiService.completeTask(params.taskId, params)
        }
						
        controllerClass.metaClass.claimTask = { String taskId ->
            activitiService.claimTask(taskId, session.username)
        }
				
        controllerClass.metaClass.revokeTask = { String taskId ->
            activitiService.claimTask(taskId, null)
        }
				
        controllerClass.metaClass.deleteTask = { String taskId, String domainClassName = null ->
            if (delegate.class != org.grails.activiti.TaskController) {
                domainClassName = getDomainClassName(delegate)
            }
            activitiService.deleteTask(taskId, domainClassName)
        }
		
        controllerClass.metaClass.setAssignee = { String taskId, String username ->
            if (username) {
                activitiService.setAssignee(taskId, username)
            } else {
                revokeTask(taskId)
            }
        }
				
        controllerClass.metaClass.setPriority = { String taskId, int priority ->
            activitiService.setPriority(taskId, priority)
        }
						
		
        controllerClass.metaClass.getUnassignedTasksCount = {->
            activitiService.getUnassignedTasksCount(session.username)
        }
				
        controllerClass.metaClass.getAssignedTasksCount = {->
            activitiService.getAssignedTasksCount(session.username)
        }
				
        controllerClass.metaClass.getAllTasksCount = {->
            activitiService.getAllTasksCount()
        }
				
        controllerClass.metaClass.findUnassignedTasks = { Map params ->
            params.username=session.username
            if (!params.sort) {
                params.sort = "id"
                params.order = "desc"
            }
            activitiService.findUnassignedTasks(params)
        }
				
        controllerClass.metaClass.findAssignedTasks = { Map params ->
            params.username=session.username
            if (!params.sort) {
                params.sort = "id"
                params.order = "desc"
            }
            activitiService.findAssignedTasks(params)
        }
				
        controllerClass.metaClass.findAllTasks = { Map params ->
            if (!params.sort) {
                params.sort = "id"
                params.order = "desc"
            }
            activitiService.findAllTasks(params)
        }
    }

    private getDomainClassName(delegate) {
        return "${delegate.class.package.name}.${GrailsNameUtils.getLogicalName(delegate.class.name, 'Controller')}"
    }
		
    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        println "event.source = $event.source"
        if (!(event.source instanceof Resource)) {  		  
            if(application.isControllerClass(event.source)) {
                def controllerClass = application.addArtefact(ControllerArtefactHandler.TYPE, event.source)			
                if (controllerClass.hasProperty("activiti") && controllerClass.clazz.activiti) {
                    controllerClass.metaClass.getActivitiService = {-> return event.ctx.activitiService}
                    addActivitiMethods(controllerClass)
                }
            } else { // it is org.springframework.core.io.Resource
			
            }
        } 		
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
