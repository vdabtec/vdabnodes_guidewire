# VDAB Guidewire Nodes
### Overview
This repository includes the source and libraries needed to build the VDAB nodes that support Guidewire product monitoring and control.
While most of the nodes work with any version of Guidewire products, the GWControlService
only works with Version 8 of the product. 

| | |
|  --- |  :---: |
| Application Page    | [Guidewire Monitoring and Control](https://vdabtec.com/vdab/app-guides/guidewire-monitoring-and-control) |
| Demo Web Link   | [gw-demo.vdabcloud.com:31158/vdab](http://gw-demo.vdabcloud.com:31158/vdab) |
| Documentation   | [Accelerator Guide](https://vdabtec.com/vdab/docs/MonitoringAndControlAccelerator.pdf) |

### Features
<ul>
<li>The <i>GWCheckService</i> node monitors the availability of the GW Server.
<li>The <i>GWMessageService</i> node monitors the health of messaging.
<li>The <i>GWActivityAgent</i> publishes business and technical events to VDAB.
<li>The extensible <i>GWControlService</i> provides direct control of the GW product.
</ul>

### Loading the the Package
The current or standard version can be loaded directly using the VDAB Android Client following the directions
for [Adding Packages](https://vdabtec.com/vdab/docs/VDABGUIDE_AddingPackages.pdf) and selecting the <i>GWNodes</i> package.
 
A custom version can be built using Gradle following the direction below.

* Clone or Download this project from Github.
* Open a command windows from the <i>GWNodes</i> directory.
* Build using Gradle: <pre>      gradle vdabPackage</pre>

This builds a package zip file which contains the components that need to be deployed. These can be deployed by 
manually unzipping these files as detailed in the [Server Updates](https://vdabtec.com/vdab/docs/VDABGUIDE_ServerUpdates.pdf) 
 documentation.

### Known Issues as of 24 Oct  2018

* The <i>GWControlService</i> does not work with version 9 of the Guidewire products and 
requires changes to the Gosu service to support this version. All of the other Guidewire
nodes should work with version 9.


