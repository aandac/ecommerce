# -- > Create S3 Bucket
echo $(awslocal s3 mb s3://ecommerce-s3-bucket)
echo $(awslocal s3 mb s3://ecommerce-temp-s3-bucket)
# --> List S3 Buckets
echo $(awslocal s3 ls)
