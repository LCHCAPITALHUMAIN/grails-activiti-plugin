# Grails Activiti Commands (Targets) #
## Install Activiti Quick Start Environment ##
Install plugin's index.gsp, logo, favicon and default H2 `DataSource.grooy` into the project with the following command:
```
grails activiti-quickstart
```

## Install and Run Activiti Examples ##
Install the examples of Activiti (which bundled with activiti distribution) into your project's test/unit directory with the following command:
```
grails install-activiti-examples
```

Execute the Activiti examples (JUnit Test Cases) with the following command:
```
grails test-app unit:
```

## Create Activiti Tests ##
This plugin supports creation of both unit tests and integration tests.

Unit tests can be created in test/unit directory with one of the following command:
```
grails create-activiti-test ClassUnderTest
```
or
```
grails create-activiti-test ClassUnderTest unit
```

Integration tests can be created in test/integration directory with the following command:
```
grails create-activiti-test ClassUnderTest integration
```


For example:
```
grails create-activiti-test com.vobject.SimpleProcess
```
An unit test case `SimpleProcessTests.groovy` will be created in test/unit directory like the code below:
```
package com.vobject

import grails.test.*
import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.matchers.JUnitMatchers.*
import org.activiti.engine.test.*

class SimpleProcessTests {
 
    @Rule public ActivitiRule activitiRule = new ActivitiRule()

    @Before void setUp() {
    }

    @After void tearDown() {
    }

    @Test @Deployment void something() {

    }
}
```

Grails-style integration test is supported since version 5.0.alpha4, for example:
```
grails create-integration-test com.vobject.SimpleProcess
```

You can write your integration test like the code below:
```
package com.vobject

import grails.test.*

class SimpleProcessTests extends GrailsUnitTestCase {
    def activitiService
    def identityService
    def managementService
    def processEngine
    def runtimeService
    def repositoryService
    def taskService 
    def historyService
		
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNotNull() {
	assertNotNull identityService
	assertNotNull managementService
	assertNotNull processEngine
	assertNotNull runtimeService
        assertNotNull repositoryService
	assertNotNull taskService
	assertNotNull historyService
    }
}
```

## Deploy Business Archive (BAR) ##
A business archive(.bar) file will be created and deployed to Activiti database with the following command (Make sure Activiti database started with command `ant h2.start` before you run the command below):
```
grails deploy-bar
```

By default, all BPMN(.bpmn20.xml) and PNG(.png) files of grails-app/conf directory and Task Form(.form) files of src/taskforms directory will be packaged into business archive and deploy.