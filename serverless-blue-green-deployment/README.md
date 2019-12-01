# Serverless Blue-Green Deployment for Test-Isolation

A very simple example of a CD pipeline (AWS CodePipeline) implementing the blue-green deployment strategy for test-isolation.

More info: http://blog.ttulka.com/serverless-blue-green-deployment

## Project Structure

`pipeline`
AWS CloudFormation template for the CD pipeline.

`stack`
AWS CloudFormation template for the service stack.

`sample-service`
AWS Lambda Node.js source code of a simple function.
