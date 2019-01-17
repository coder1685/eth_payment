#/bin/bash
#upload files
aws s3 cp C:/ankita/erc20/Angular4Upload-2/Angular4Upload/dist s3://ethpayment-frontend --recursive --acl public-read