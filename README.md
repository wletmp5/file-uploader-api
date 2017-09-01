# file-uploader-api
this is a file upload demo api with the following features.

• API to upload a file with a few meta-data fields. Persist meta-data in in-memory persistence store and store the file in the file system.
• API to get file meta-data
• API to download content stream 
• API to search for file IDs with a search criterion 
Write a scheduler in the same app to poll for new items in the last hour and send an email 

#How to run
1. clone this project
1.1 configure polling job in src/main/resources/application.properties file
   a. job.directory.path=[PATH]   
   b. job.file.lastModifiedDate=[TIMEINMILLIS] --> it will only download files in the last hour(defaulted)
2. in terminal, run "mvn package" command in the application directory
3. go to application/target directory then run "java -jar file-uploader-api-0.0.1-SNAPSHOT.jar"
4. test the application (you can use any utility to test the APIs)
note* polling of files is configured to run every 10 seconds

a. upload a file: 
    url: localhost:8080/documents/
    method: POST
    params:
        file(multipart): [selected file]
    
b. get files meta-data
    url: localhost:8080/documents/ 
    method: GET
    path params:
        name (optional)
        size (optional)
        
c. get file meta-data
    url: localhost:8080/documents/{id}
    method: GET
    
d. get file content
    url: localhost:8080/documents/{id}/content
    method: GET
    
#TODO
1. Logging
2. Unit Testing
3. API Docs
4. Mail Notifications
5. Refactoring and etc.

   
    

