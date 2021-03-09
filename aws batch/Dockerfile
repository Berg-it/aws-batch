#Base Image is ubuntu
FROM ubuntu:16.04

# Install Open-JDK (JAVA)
RUN apt-get update
RUN apt-get install -yq software-properties-common
RUN add-apt-repository -y ppa:openjdk-r/ppa
RUN apt update -qq
RUN apt install -yq openjdk-11-jdk

#copy from local to container and rename jar
COPY aws-batch-1.0-SNAPSHOT.jar /batch.jar
RUN chmod 777 batch.jar
# Run the  batch job( You can start a job like this as well .
# In our case we will be overriding the CMD command when we submit the batch job)
# will turn to : java -jar batch.jar AWS_BATCH_IP_SOURCE_BUCKET X AWS_BATCH_IP_SOURCE_BUCKET_KEY A AWS_BATCH_IP_DESTINATION_BUCKET Y AWS_BATCH_IP_DESTINATION_KEY B
# where X, Y: means aws s3 bucket name and A,B s3 object keys.
CMD ["java","-jar", "batch.jar"]