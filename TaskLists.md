# Introduction #

There are 3 common task lists: My Tasks, Unassigned Tasks and All Tasks come with the plugin which shared by all processes of the application.

# My Tasks #
My Tasks is display tasks that claimed by user and still pending.

After click on TaskController, you will see the screen below:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/tasklist/MyTasks.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/tasklist/MyTasks.jpg)

There are four options in the navigation bar: Home, My Tasks, Unassigned Tasks and All Tasks. The default screen opened is My Tasks. At the end of each Task option, you will see a bracket with number, it is summary (number of Tasks) of each type of Tasks. By click on Home option, the application will go back to main (index) page.

All task lists support the following features:
  * All columns are sortable in ascending or descending order except the Action column. Default sort by Id. column in descending order.
  * Pagination support. Default 10 records per page.

User can access details of task by click on Id. of the task.

Also, user can undo the claimed task by click Revoke button of the task **(Not working yet in 5.0.beta2-PREVIEW)**.

# Unassigned Tasks #
Unassigned Tasks is display tasks that eligible for the user and can claim by the user.

After click on Unassigned Tasks option, you will see the screen below:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/tasklist/UnassignedTasks.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/tasklist/UnassignedTasks.jpg)

The screen above is similar to My Tasks except user can't view the detail of task and there are two buttons in Action column: Claim and Start.

By click Claim button, the task is claimed by user and put into his My Tasks. By click Start button, the claimed task is put into My Tasks and the user can start working on the task instantly instead of retrieve the task from My Tasks.


# All Tasks #
All Tasks display all assigned tasks and unassigned tasks.

After click on All Tasks option, you will see the screen below:

![http://grails-activiti-plugin.googlecode.com/hg/docs/images/tasklist/AllTasks.jpg](http://grails-activiti-plugin.googlecode.com/hg/docs/images/tasklist/AllTasks.jpg)

The screen above is similar to Unassigned Tasks except user is able to set the priority and assignee of the task and click Delete button to delete the task.