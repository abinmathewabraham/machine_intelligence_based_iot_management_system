#!/bin/bash
python3 pddlgen.py $1 $2 $3 > /dev/null
python3 callPanda.py sprinkler_usecase_domainfile.pddl problemfile.pddl
#/home/abin/optic1/debug/optic/optic-clp sprinkler_usecase_domainfile.pddl problemfile.pddl > /dev/null
#cat planoutput.txt
