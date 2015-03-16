# Version History #
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

**13-May-2011 5.5**
  * Upgrade Activiti's jar files and examples to 5.5. Ported the Activiti's examples from JUnit3 to JUnit4.
  * Fixed Issue [10](http://code.google.com/p/grails-activiti-plugin/issues/detail?id=10): deleteTask with custom domain class.
  * Add a new command `activiti-quickstart` to install plugin's index.gsp, logo, favicon and default H2 `DataSource.grooy` into the project. The index.gsp no longer install by default.
  * Add businessKey support when start a process instance by assign a value to `params.businessKey`.
  * Must use activiti-spring-security plugin 0.4.2 or above for Spring Security Integration.

**05-Apr-2011 5.4**
  * Upgrade Activiti's jar files to 5.4.
  * Support email sending without authentication.
  * Incorporate Spring Security Integration (enabled by [Activiti Spring Security plugin](http://code.google.com/p/grails-activiti-spring-security-plugin/)) to the plugin and Vacation Request Sample Application.

**02-Mar-2011 5.3**
  * Upgrade Activiti's jar files and examples to 5.3. Ported the Activiti's examples from JUnit3 to JUnit4.

**02-Feb-2011 5.2**
  * Upgrade Activiti's jar files to 5.2.

**21-Jan-2011 5.1.2**
  * Support disable activiti engine by specify system property `-DdisabledActiviti=true`. Eg. `grails -DdisabledActiviti=true run-app`. Refer to this [discussion topic](http://groups.google.com/group/grails-activiti-plugin/t/4f571c52a45a7adf) for origin of this feature.

**14-Jan-2011 5.1.1**
  * Fixed `java.lang.NoClassDefFoundError` on unit test execution (`grails test-app -unit`) after the activiti plugin installed. The issue reported [here](http://groups.google.com/group/grails-activiti-plugin/browse_thread/thread/f87913a1bd84d757).

**05-Jan-2011 5.1**
  * Upgrade Activiti's jar files to 5.1.

**03-Dec-2010 5.0**
  * Update Activiti's jar files and examples to 5.0. Ported the Activiti's examples from JUnit3 to JUnit4.
  * Sortable support for "Create Time" column of 3 Task Lists: My Tasks, Unassigned Tasks and All Tasks.
  * Update Vacation Request Sample Application to support Email Notification using E-mail Service Task.
  * Fixed Issue [ACT-303](http://jira.codehaus.org/browse/ACT-303): Database schema not created properly. The workaround is to create the Activiti's database schema manually using `ant db.create` and set the `dbSchemaStrategy` in `Config.groovy` to `check-version` and `dbCreate` in `DataSource.groovy` to `update`.
  * Fixed 13 tests failed for activiti examples as mentioned in http://forums.activiti.org/en/viewtopic.php?f=3&t=406.
  * Fixed Issue [ACT-207](http://jira.codehaus.org/browse/ACT-207): Task lists sort by id column is not working properly as reported in http://forums.activiti.org/en/viewtopic.php?f=3&t=323. Solution is order "Create Time" column by descending.

**04-Nov-2010 5.0.rc1**
  * Update Activiti's jar files and examples to 5.0.rc1. The Activiti's examples change to JUnit4 style from JUnit3.
  * Added new column "Create Time" to 3 Task Lists: My Tasks, Unassigned Tasks and All Tasks
  * Fixed issue [ACT-206](http://jira.codehaus.org/browse/ACT-206): Revoke button and `revokeTask` method still not working yet.
  * Fixed issue [ACT-145](http://jira.codehaus.org/browse/ACT-145): support useFormKey configuration, if enabled return formKey as Task Form URI, otherwise return task.taskDefinitionId

**06-Oct-2010 5.0.beta2**
  * Update Activiti's jar files and examples to 5.0.beta2. The Activiti's examples change to JUnit4 style from JUnit3.
  * 3 Task Lists: My Tasks, Unassigned Tasks and All Tasks
  * Scaffolding: Activiti Controller and CRUD Views Generation
  * Grails Activiti API
  * Vacation Request Sample Application
  * Support Activiti's Process Engine to make use of Grails's dataSource and transactionManager
  * Support Auto Resource Deployment for updated resources upon process engine initialization and when the application is running.
  * Major updates and expansions of project documentation

**09-Sep-2010 5.0.beta1**
  * Update Activiti's jar files and examples to 5.0.beta1. But majority of the test cases FAILED.
  * Introduced Dependency Injection for RuntimeService, RepositoryService and HistoryService (API Update).
**03-Aug-2010 5.0.alpha4**
  * Update Activiti's jar files and examples to 5.0.alpha4.
  * Introduced Dependency Injection for HistoricDataService.
  * Use DataSource.groovy configurations for Activiti's database-specific configurations.
  * Grails-style integration test supported for Activiti.
**15-Jul-2010 5.0.alpha3** - First alpha release.
  * Dependency injection for key type including IdentityService, ManagementService, ProcessEngine, ProcessService and TaskService.
  * Introduced new grails targets: install-activiti-examples, create-activiti-test and deploy-bar.