/*
 *	Copyright 2019 Steve White
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *	use this file except in compliance with the License. You may obtain a copy
 *	of the License at:
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *	License for the specific language governing permissions and limitations
 *	under the License.
 *
 *
 */
metadata 
{
	definition(name: "HubConnect Window Shade", namespace: "shackrat", author: "Steve White", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/HubConnect/master/UniversalDrivers/HubConnect-Window-Shade.groovy")
	{
		capability "Window Shade"
		capability "Sensor"
		capability "Switch"
		capability "Switch Level"

		attribute "version", "string"
        
		command "open"
		command "close"
		command "sync"
	}
}


/*
	installed
    
	Doesn't do much other than call initialize().
*/
def installed()
{
	initialize()
}


/*
	updated
    
	Doesn't do much other than call initialize().
*/
def updated()
{
	initialize()
}


/*
	initialize
    
	Doesn't do much other than call refresh().
*/
def initialize()
{
	refresh()
}


/*
	parse
    
	In a virtual world this should never be called.
*/
def parse(String description)
{
	log.trace "Msg: Description is $description"
}


/*
	on
	
	opens the window shade.
*/
def on() { open() }


/*
	off
	
	closes the window shade.
*/
def off() { close() }


/*
	open
    
	Opens the window shade.
*/
def open()
{
	// The server will update open/close status
	parent.sendDeviceEvent(device.deviceNetworkId, "open")
}


/*
	close
    
	Closes the window shade.
*/
def close()
{
	// The server will update open/close status
	parent.sendDeviceEvent(device.deviceNetworkId, "close")
}


/*
      setPosition
      
      sets the shade to a partial position, special case for null (Homebridge)
*/
def setPosition(pos) 
{
	if (pos != null)
	{
	   parent.sendDeviceEvent(device.deviceNetworkId, "setPosition", [pos])
	}
	else
	{
	   parent.sendDeviceEvent(device.deviceNetworkId, "close")
	}
}
// Dashboard uses setLevel as if shades are 'dimmers'
def setLevel(pos) { setPosition(pos) } 


/*
	refresh
    
	Refreshes the device by requesting an update from the client hub.
*/
def refresh()
{
	// The server will update on/off status
	parent.sendDeviceEvent(device.deviceNetworkId, "refresh")
}


/*
	sync
    
	Synchronizes the device details with the parent.
*/
def sync()
{
	// The server will respond with updated status and details
	parent.syncDevice(device.deviceNetworkId, "windowshade")
	sendEvent([name: "version", value: "v${driverVersion.major}.${driverVersion.minor}.${driverVersion.build}"])
}
def getDriverVersion() {[platform: "Universal", major: 1, minor: 2, build: 1]}