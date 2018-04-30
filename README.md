# test_simulator

## Usage
Simulator [-h] -c CONFIG -s SEED -l LOAD

Simulator of ONS simulator.

named arguments:  
  **-h, --help** show this help message and exit  
  **-c CONFIG, --config CONFIG**  Specify configuration file  
  **-s SEED, --seed SEED** Specify seed  
  **-l LOAD, --load LOAD** Specify load  

## Config file

Config file has the following properties:

"duration" [number]: duration (in seconds) where the process should sleep  
"error" [boolean]: specifies if process should return error  
"stress" [boolean]: specifies if process should stress the computer resources  
"cpuLoad": [number]: should comprehend values between 0 and 1. How much should stress the CPU, if "stress" property is set  
"memoryLoad" [number]: should comprehend values between 0 and 1. How much should stress the Memory, if "stress" property is set  
