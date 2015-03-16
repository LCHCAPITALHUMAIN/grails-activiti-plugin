# Introduction #

The scaffolding support of the plugin will not generate 100% working application like the Grails scaffolding, given every process has different requirements. However, it will generate 80% repetitive codes and you can focus on coding 20% codes that really matters to your process requirements.


# Scaffolding Commands (Targets) #
## Generate All (Controller and Views) ##
You can generate Activiti controllers and views for all domain classes with the following command:
```
grails generate-activiti-all *
```
**Note:** The command above may not works due to "Domain class not found in grails-app/domain...", the same problem happened to `grails generate-all *`. It should works after the issue of `grails generate-all *` had been fixed.

You can generate Activiti controller and views for specific domain class (e.g.: `vacationRequest.VacationRequest`) with the following command:
```
grails generate-activiti-all vacationRequest.VacationRequest
```

## Generate Controller Only ##
You can generate Activiti controller only for specific domain class (e.g.: `vacationRequest.VacationRequest`) with the following command:
```
grails generate-activiti-controller vacationRequest.VacationRequest
```

## Generate Views Only ##
You can generate Activiti views only for specific domain class (e.g.: `vacationRequest.VacationRequest`) with the following command:
```
grails generate-activiti-views vacationRequest.VacationRequest
```

## Install Activiti Templates ##
You can install Activiti templates for activiti test, controller and views into src/activiti-templates directory with the following command:
```
grails install-activiti-templates
```
Then, you can tailor the installed templates to your project needs. Also, you can create additional gsp template files into src/activiti-templates directory, it will be picked up by the views generation process.

# Multiple Templates Support #
The plugin support multiple templates by adding new configuration item `grails.templates.dir.name` and the directory name of new templates to `grails-app/conf/BuildConfig.groovy`, for example:
```
grails.templates.dir.name="activiti-templates2"
```
By execute `grails install-activiti-templates` after the configuration, the Activiti templates will install into src/activiti-templates2 directory. The `grails.templates.dir.name` specified the current templates used by all scaffolding `generate-activiti-*` commands such as `generate-activiti-all`, `generate-activiti-controller` and `generate-activiti-views`. You can change the configuration to tell the scaffolding `generate-activiti-*` commands which templates to use for generation.