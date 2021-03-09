# AWS BATCH  EXAMPLE
Sample code to get started with AWS Batch.
## Prerequisites:
Install [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html)
Install [Docker](https://docs.docker.com/install/)
Install [maven](https://maven.apache.org/download.cgi)
Setup either [Docker hub](https://hub.docker.com/signup)/[AWS ECR](https://docs.aws.amazon.com/AmazonECR/latest/userguide/get-set-up-for-amazon-ecr.html)
## Run the code

### Step 1: 
Download Code -> Open command Line -> go to the folder where you downloaded the code
Run command
```
 mvn clean package
 mkdir temp
 cp target/aws-batch-1.0-SNAPSHOT.jar temp/
 cp Dockerfile temp/
 cd temp
 ```

`docker run -d image-id`
`docker container ls -a`
`docker logs container-id`

### Step 2:
    if your don't have a ECR repository to store the Docker image so you need to do
        In the ECR console, choose Get Started or Create repository.
        Enter a name for the repository, for example: awsbatch/first_batch.

        log in to an Amazon ECR registry
            aws ecr get-login-password --region <Region> | docker login -u AWS --password-stdin <IdUser>.dkr.ecr.eu-central-1.amazonaws.com

### Step 3
        + Create an IAM role for an ECS service task with bellow policies (X_batch_role_X)
             ---> AWS Service - Elastic Container Service
                    - CloudWatchFullAccess
                    - CloudWatchLogsFullAccess
                    - EC2InstanceProfileForImageBuilderECRContainerBuilds
### Step 4
    now we configure our batch environement
        - Create a Compute Environment: each jobs will run on a compute environment
        - Create a Job Queue: where you’ll send the jobs to get executed. This queue will be attached to a compute environment so the AWS Batch service will create the
                    resources needed based on the load of the queue. It will use the min, max, and desired vCPUs configuration to know how many instances to create
        - Create a Job Definition: which image docker will use, and wich parameters will send to this image docker

### Step 5
    Now we create a Lambda function wich will trigger our job
        - Create a new IAM role that will affected to the new lambda function with policy: AWSBatchServiceEventTargetRole & AWSLambdaBasicExecutionRole
            - aws iam create-role --role-name lambda-trigger-Batch --assume-role-policy-document file://trust-policy.json
            - aws iam attach-role-policy --role-name lambda-trigger-Batch --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
            - aws iam attach-role-policy --role-name lambda-trigger-Batch --policy-arn arn:aws:iam::aws:policy/service-role/AWSBatchServiceEventTargetRole
        - Create your function lambda_function.py
        - Deploy your fucntion
            - zip my-deployment-package.zip lambda_function.py
            - aws lambda create-function --function-name trigger-batch-1-function \
                         --zip-file fileb://my-deployment-package.zip --handler \
                         lambda_function.lambda_handler --runtime python3.8 \
                         --role arn:aws:iam::<IAM-ACCOUNT-ID>:role/lambda-trigger-Batch \
                         --region eu-central-1
        - add env variable for our lambda function
            - aws lambda update-function-configuration --function-name trigger-batch-1-function \
                  --environment "Variables={batch_1_JobDefinition=job-definition-batch-read-file-34:1,batch_1_JobQueue=queue-read-file-batch}" \
                  --region eu-central-1

### Step 6
    We configure a Rule Event from EventBridge aws service to call lambda each day at 1AM
        -Create our Event rule
            - aws events put-rule --name "DailyLambdaFunction" --schedule-expression "cron(25 18 ? * * *)"
            - aws events put-targets --rule DailyLambdaFunction --targets "Id"="1","Arn"="arn:aws:lambda:eu-central-1:<IAM-ACCOUNT-ID>:function:trigger-batch-1-function"


### Step 7
    Now we need to create S3 bucket where we will upload our files
        - Create bucket
            - aws s3api create-bucket --bucket my-batch1-bucket --create-bucket-configuration LocationConstraint=eu-central-1



#### Ref
    https://docs.aws.amazon.com/cli/latest/reference/lambda/create-function.html
    https://docs.aws.amazon.com/lambda/latest/dg/gettingstarted-awscli.html
    https://docs.aws.amazon.com/cli/latest/reference/events/put-rule.html
    https://docs.aws.amazon.com/cli/latest/reference/events/put-targets.html
    https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/ScheduledEvents.html


