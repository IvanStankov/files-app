# files-app
## Run app
- ### Through grails CLI
1. Go to the root application folder
2. Open command line and execute "grails run-app"
3. Open app in a browser using url http://localhost:8080/files-app
- ### Through Docker
1. docker run -d -p <host_port>:8080 --name files-app ivanstankov/files-app
2. Open app in a browser using url http://<docker_host>:<host_port>/files-app
