#!/usr/bin/env python3
#Requires 4 params
#First param - colon (:) seperated room names
#Second param - room which is on fire/in which sprinkler should be on; colon (:) seperated
#Third param - rooms which have server which is switched on; colon (:) seperated
#script for generating pddl dynamically..... on running this script we get test.pddl....
import sys

rooms = sys.argv[1].split(":")
sprinkler_on_rooms = sys.argv[2].split(":")

if len(sys.argv) == 3:
	on_server_rooms = []
else:
	on_server_rooms = sys.argv[3].split(":")


problem = "(problem sprinkler_problem)"
domain = "(:domain sprinkler_usecase-strips)"
init = "(:init"
objects = "(:objects"

for item in rooms:
	objects = objects + " " + item
	init = init + " " + "(has_off_sprinkler " + item + ")"
	if item in on_server_rooms:
		init = init + " " + "(has_on_server " + item + ")"
	else:
		init = init + " " + "(has_off_server " + item + ")"		
objects = objects + ")"
init = init +" )"

goal = "(:goal (and"
for item in sprinkler_on_rooms:
	goal = goal + " " + "(has_on_sprinkler " + item + ")"
goal = goal+" ))"

fileContent = "(define "+problem+domain+objects+init+goal+")"
fileOp = open("problemfile.pddl","w")
fileOp.write(fileContent)
fileOp.close()
