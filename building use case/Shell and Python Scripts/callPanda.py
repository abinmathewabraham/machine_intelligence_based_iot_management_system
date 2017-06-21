#!/usr/bin/env python3
import sys
import requests
import json

urlp = 'http://127.0.0.1:5000/pddlplanner/planapi/v1.0/plan'
domainFile = sys.argv[1]
problemFile = sys.argv[2]
json_data={'plannerType':'optic'}
files = {'domainFile': open(domainFile, 'r'), 'problemFile':open(problemFile, 'r')}
response = requests.post(urlp,data=json_data,files=files)
planJson = json.loads(response.text)
if len(planJson['plan']) == 0:
	print('null')
else:
	planStr = '\n'.join(planJson['plan'])
	print(planStr)
