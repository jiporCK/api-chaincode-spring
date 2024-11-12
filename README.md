```bash
http POST :9090/api/v1/init

http :9090/api/v1/assets/all

http :9090/api/v1/assets/read-by-id/asset4

http POST :9090/api/v1/assets/create color="blue" size:=9 owner="Chipor" appraisedValue=2000

http PUT :9090/api/v1/assets/transfer assetId=="asset1730362779664" owner=="Kimpor"

http DELETE :9090/api/v1/assets/delete/asset4

```