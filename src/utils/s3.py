# import boto3

'''
Inserting objects into s3 bucket
'''
# Connect to the S3 service
# s3 = boto3.client("s3")

# Choose a bucket by name
# bucket_name = "your-bucket-name"

# Write a string to a file in S3
# s3.put_object(Bucket=bucket_name, Key="example.txt", Body="Example data")

# Write data from a file to S3
# with open("example_data.txt", "rb") as data:
#    s3.put_object(Bucket=bucket_name, Key="example_data.txt", Body=data)

'''
Listing objects in s3 bucket
'''
# s3 = boto3.resource("s3")

# Choose a bucket by name
# bucket_name = "your-bucket-name"
# bucket = s3.Bucket(bucket_name)

# List the contents of the bucket
# for obj in bucket.objects.all():
#    print(obj.key)

'''
Environment variables:
1) AWS_ACCESS_KEY_ID
2) AWS_SECRET_ACCESS_KEY
'''
